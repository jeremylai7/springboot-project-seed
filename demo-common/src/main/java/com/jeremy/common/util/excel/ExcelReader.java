package com.jeremy.common.util.excel;

import com.jeremy.common.util.excel.annotation.ExcelColumn;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Austen
 * @create 2018-03-30 15:26
 * @desc Excel读取器
 **/
public class ExcelReader {

    private static final DecimalFormat df = new DecimalFormat("0");// 格式化 number为整

    private static final DecimalFormat df_per = new DecimalFormat("##.00%");//格式化分比格式，后面不足2位的用0补齐

    //private static final DecimalFormat df_per_ = new DecimalFormat("0.00%");//格式化分比格式，后面不足2位的用0补齐,比如0.00,%0.01%

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串

    private static final DecimalFormat sc_number = new DecimalFormat("0.00E000"); //格式化科学计数器

    private static final Pattern points_ptrn = Pattern.compile("0.0+_*[^/s]+");


    public static <T> List<T> read(File file, Class<T> cls) throws IOException {
        if (file == null) {
            return Collections.EMPTY_LIST;
        }
        if (!file.exists()) {
            throw new IOException("file is nonexistent");
        }
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
        if ("xls".equals(extension) || "xlsx".equals(extension)) {
            return readExcel(new FileInputStream(file), cls);
        } else {
            throw new IOException("don't support file type");
        }
    }

    private static <T> List<T> readExcel(InputStream inputStream, Class<T> cls) {
        List<T> dataList = new LinkedList<T>();//
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            Map<String, List<Field>> classMap = new HashMap<String, List<Field>>();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                if (annotation != null) {
                    String value = annotation.value();
                    if (!classMap.containsKey(value)) {
                        classMap.put(value, new ArrayList<Field>());
                    }
                    field.setAccessible(true);
                    classMap.get(value).add(field);
                }
            }
            Map<Integer, List<Field>> reflectionMap = new HashMap<Integer, List<Field>>();
            int sheetsNumber = workbook.getNumberOfSheets();
            for (int n = 0; n < sheetsNumber; n++) {
                Sheet sheet = workbook.getSheetAt(n);
                for (int j = sheet.getRow(0).getFirstCellNum(); j < sheet.getRow(0).getLastCellNum(); j++) { //首行提取注解
                    Object cellValue = getCellValue(sheet.getRow(0).getCell(j));
                    if (classMap.containsKey(cellValue)) {
                        reflectionMap.put(j, classMap.get(cellValue));
                    }
                }
                Row row = null;
                Cell cell = null;
                for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    row = sheet.getRow(i);
                    boolean isEmptyRow = isRowEmpty(row);
                    if (isEmptyRow) {
                        continue;
                    }
                    T t = cls.newInstance();
                    for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        if (reflectionMap.containsKey(j)) {
                            Object cellValue = getCellValue(cell);
                            List<Field> fieldList = reflectionMap.get(j);
                            for (Field field : fieldList) {
                                try {
                                    field.set(t, cellValue);
                                } catch (Exception e) {
                                    //logger.error()
                                }
                            }
                        }
                    }
                    dataList.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataList = Collections.EMPTY_LIST;
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(inputStream);
        }
        return dataList;
    }

    /**
     * 获取excel 单元格数据
     *
     * @param cell
     * @return
     */
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

    private static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }
}
