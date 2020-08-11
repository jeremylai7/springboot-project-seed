package com.jeremy.data.enums.util;

import com.jeremy.data.enums.BaseCodeEnum;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 13:54
 * @Description:
 */
public class CodeEnumUtil {
    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> enumClass, String code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }
}
