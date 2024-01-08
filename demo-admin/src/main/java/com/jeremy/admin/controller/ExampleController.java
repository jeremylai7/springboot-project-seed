package com.jeremy.admin.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author: laizc
 * @date: created in 2022/5/16
 * @desc:
 **/
@Api(tags = "示例")
@RestController
@RequestMapping("/example")
public class ExampleController {

    @ApiOperation(value = "index")
    @GetMapping("/index")
    public Object index(HttpServletRequest request){
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setAa("3535ddd");
        return demo;
    }

    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("test.xlsx");
        Workbook workbook = reader.getWorkbook();
        List<? extends PictureData> pictures = workbook.getAllPictures();
        for (PictureData picture : pictures) {
            byte[] data = picture.getData();
            System.out.println(data);
        }


    }

    @ApiOperation(value = "import")
    @PostMapping("/importImage")
    public void importExcelImage(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        //List<? extends PictureData> pictures = workbook.getAllPictures();
        Map<String, PictureData> map = getPictures2(sheet);
        System.out.println(map);
        printImg(map);



    }

    public static Map<String, PictureData> getPictures1 (HSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                map.put(key, pdata);
            }
        }
        return map;
    }

    public static Map<String, PictureData> getPictures2 (XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    PictureData xssfPictureData = picture.getPictureData();

                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    //图片写出
    public static void printImg(Map<String, PictureData> sheetList) throws IOException {

        Object key[] = sheetList.keySet().toArray();
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();

            //图片保存路径
            FileOutputStream out = new FileOutputStream("D:\\img\\pic" + picName + "." + ext);
            out.write(data);
            out.close();
        }
    }






    @ApiOperation(value = "import")
    @PostMapping("/import")
    public void importExcel(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        InputStream inputStream = multipartFile.getInputStream();

            Workbook workbook = WorkbookFactory.create(inputStream);
            // sheet 数量
            int sheetsNumber = workbook.getNumberOfSheets();

            // 默认读取第一页
            Sheet sheet = workbook.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            int first = sheet.getFirstRowNum();
            int phy = sheet.getPhysicalNumberOfRows();
            // 首行
            for (int j = sheet.getRow(0).getFirstCellNum(); j < sheet.getRow(0).getLastCellNum(); j++) {
                Object cellValue = getCellValue(sheet.getRow(0).getCell(j));
                System.out.println(cellValue);
            }

            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    Object cellValue = getCellValue(cell);
                    System.out.println(cellValue);

                }
            }




    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串

    private static final DecimalFormat sc_number = new DecimalFormat("0.00E000"); //格式化科学计数器

    private static final Pattern points_ptrn = Pattern.compile("0.0+_*[^/s]+");

    private static final DecimalFormat df = new DecimalFormat("0");// 格式化 number为整

    private static final DecimalFormat df_per = new DecimalFormat("##.00%");//格式化分比格式，后面不足2位的用0补齐


    private static Object getCellValue(Cell cell) {

        Object value = null;
        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) { //日期
                    value = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                } else {
                    final String dataFormatString = cell.getCellStyle().getDataFormatString();
                    if ("@".equals(dataFormatString)
                            || "General".equals(dataFormatString)
                            || "0_ ".equals(dataFormatString)) {
                        //文本  or 常规 or 整型数值
                        value = new BigDecimal(df.format(cell.getNumericCellValue()));
                    } else if (points_ptrn.matcher(dataFormatString).matches()) { //正则匹配小数类型
                        value = new BigDecimal(cell.getNumericCellValue());  //直接显示
                    } else if ("0.00E+00".equals(dataFormatString)) {//科学计数
                        value = cell.getNumericCellValue(); //待完善
                        value = sc_number.format(value);
                    } else if ("0.00%".equals(dataFormatString)) {//百分比
                        value = cell.getNumericCellValue(); //待完善
                        value = df_per.format(value);
                    } else if ("# ?/?".equals(dataFormatString)) {//分数
                        value = cell.getNumericCellValue(); ////待完善
                    } else { //数值
                        value = new BigDecimal(cell.getNumericCellValue());

                    }
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                //value = ",";
                break;
            default:
                value = cell.toString();
        }
        return value;
    }


}
