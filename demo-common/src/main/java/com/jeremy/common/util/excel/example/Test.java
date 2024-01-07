package com.jeremy.common.util.excel.example;


import com.jeremy.common.util.excel.ExcelExporter;
import com.jeremy.common.util.excel.annotation.ExcelColumn;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-07-29 10:46
 **/
public class Test {

    @ExcelColumn("字符串")
    private String a1;

    @ExcelColumn("数字")
    private int a2;

    @ExcelColumn("时间")
    private Date a3;

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public Date getA3() {
        return a3;
    }

    public void setA3(Date a3) {
        this.a3 = a3;
    }


    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        List<Test> tests = new ArrayList<>();
        XSSFWorkbook workbook = ExcelExporter.export(tests,Test.class);
        File file = new File("D:\\test.xlsx");
        OutputStream out = new FileOutputStream(file);
        workbook.write(out);
    }
}
