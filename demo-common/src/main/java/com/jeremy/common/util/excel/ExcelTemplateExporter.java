package com.jeremy.common.util.excel;

import com.jeremy.common.util.excel.model.Merge;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel根据模版导出数据
 *
 * @author
 * @create 2019-12-04 11:01
 **/
public class ExcelTemplateExporter {

    public static Workbook export(File tempateFile, Map<String, Object> datas) throws FileNotFoundException, InvalidFormatException {
        InputStream in = new BufferedInputStream(new FileInputStream(tempateFile));
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformXLS(in, datas);
        return workbook;
    }

    private static <T> Object useMethod(T t, String sx) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        // 一般传入get方法
        return  t.getClass().getMethod(sx, null).invoke(t, null);
    }

    public static <T> List<Merge> getMerge(List<T> list, String sx) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        // 可以传入 想合并的属性值 传入一个字符串 用反射找到相应的get方法 指定调用此方法
        List<Merge> ml = new ArrayList<Merge>();
        for (int i = 0; i < list.size() - 1; i++) {
            if (useMethod(list.get(i), sx).equals(useMethod(list.get(i + 1), sx))) {
                Object property = useMethod(list.get(i), sx);
                Merge merge = new Merge();
                int fromRow = i, toRow = i + 1;
                if (toRow + 1 < list.size()) {
                    for (int j = i + 2; j < list.size(); j++) {
                        if (useMethod(list.get(j), sx).equals(property)) {
                            toRow++;
                        } else {
                            i = j - 1;
                            break;
                        }
                    }
                    if(toRow>=list.size()-1){
                        i=toRow;
                    }
                }
                merge.setFromRow(fromRow);
                merge.setToRow(toRow);
                ml.add(merge);
            }
        }
        return ml;
    }

}

