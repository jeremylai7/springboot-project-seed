package com.jeremy.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @ApiOperation(value = "import")
    @PostMapping("/import")
    public void importExcel(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            // sheet 数量
            int sheetsNumber = workbook.getNumberOfSheets();
            System.out.println(sheetsNumber);
            // 默认读取第一页
            Sheet sheet = workbook.getSheetAt(1);
            Row row = null;
            Cell cell = null;
            int first = sheet.getFirstRowNum();
            int phy = sheet.getPhysicalNumberOfRows();

            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    Object cellValue = getCellValue(cell);
                    System.out.println(cellValue);

                }
            }





        } catch (InvalidFormatException e) {
            e.printStackTrace();
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
