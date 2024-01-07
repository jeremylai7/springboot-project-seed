package com.jeremy.common.util.excel;


import com.jeremy.common.util.excel.annotation.ExcelColumn;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Excel导出器
 *
 * @author
 * @create 2019-07-29 9:27
 **/
public class ExcelExporter {


    public static XSSFWorkbook export(List<?> datas, Class<?> cls) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Field[] fields = getAllFields(cls);
        List<String> titles = getTitle(fields);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }
        writeTitle(titles, sheet);
        XSSFCellStyle style = crateCellStyle(workbook);
        int currentRowNum = 1;
        for (int r = 0; r < datas.size(); r++) {
            XSSFRow row = sheet.createRow(currentRowNum);
            Field[] columns = getAllFields(datas.get(r).getClass());
            int columnNum = 0;
            for (Field field : columns) {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                if (annotation == null) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                        cls);
                Method method = pd.getReadMethod();
                Object value = method.invoke(datas.get(r));
                XSSFCell cell = row.createCell(columnNum);
                setCellValue(cell, field.getType(), value);
                cell.setCellStyle(style);
                columnNum++;
            }
            currentRowNum++;
        }
        return workbook;
    }

    /**
     * 获取所有的字段 包含父类字段
     * @param cls
     */
    private static Field[] getAllFields(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        Class superClass = cls.getSuperclass();
        if (!"java.lang.Object".equals(superClass.getName())) {
            Field[] superFields = superClass.getDeclaredFields();
            if (superFields.length > 0) {
                List<Field> list = new ArrayList<>(Arrays.asList(superFields));
                list.addAll(new ArrayList<>(Arrays.asList(fields)));
                fields = list.toArray(new Field[0]);
            }
        }
        return fields;
    }


    private static void setCellValue(XSSFCell cell, Class<?> typeClass, Object value) {
        if (typeClass == Date.class) {
            Date date = (Date) value;
            final DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cell.setCellValue(formater.format(date));
        } else {
            cell.setCellValue(value == null ? "" : String.valueOf(value));
        }
    }

    private static List<String> getTitle(Field[] fields) {
        List<String> titles = new ArrayList<>();
        if (fields == null){
            return titles;
        };
        for (Field field : fields) {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null) {
                String value = annotation.value();
                titles.add(value);
            }
        }
        return titles;
    }

    private static void writeTitle(List<String> titles, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = crateCellStyle(sheet.getWorkbook());
        for (int i = 0; i < titles.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(style);
        }
    }

    private static XSSFCellStyle crateCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static void exportUtil(List<?> datas, Class<?> dataClass, HttpServletResponse response, String fileName) throws IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        XSSFWorkbook workbook = ExcelExporter.export(datas, dataClass);
        // 捕获内存缓冲区的数据，转换成字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        // 获取内存缓冲中的数据
        byte[] content = out.toByteArray();
        // 将字节数组转化为输入流
        InputStream in = new ByteArrayInputStream(content);
        //通过调用reset（）方法可以重新定位。
        response.reset();
        // 如果文件名是英文名不需要加编码格式，如果是中文名需要添加"iso-8859-1"防止乱码
        response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xlsx").getBytes("UTF-8"), "iso-8859-1"));
        response.addHeader("Content-Length", "" + content.length);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        byte[] buff = new byte[8192];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
        outputStream.flush();
        outputStream.close();
    }


}
