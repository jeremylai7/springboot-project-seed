package com.springbootredis.util;

import com.springbootredis.model.enums.BaseCodeEnum;

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
