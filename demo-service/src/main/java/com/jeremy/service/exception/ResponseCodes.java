package com.jeremy.service.exception;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @Author: laizc
 * @Date: Created in 14:16 2019-01-07
 * @desc 响应状态码
 */
public class ResponseCodes {
    private static ResourceBundle bundle;

    public static final String SUCCESS = "0";

    public static final String FAIL = "-1";

    public static final String USERNAME_EXISTING = "-2";//用户名已存在

    public static final String PASSWORD_ERROR = "-3"; //密码错误

    public static final String TOKEN_FAILURE = "-401";// 未知的token错误

    public static final String TOKENERROR = "-402";//token解析错误

    public static final String TOKENOVERDUE = "-403";//token已经过期

    public static final String TOKENNULL = "-404";//找不到token

    public static String getCodeMessage(String code){
        if (bundle == null){
            bundle = loadProperties();
        }
        return bundle.getString(code);
    }

    private static ResourceBundle loadProperties(){
        ResourceBundle bundle =ResourceBundle.getBundle("message/reponse-message", Locale.getDefault());
        return bundle;
    }


}
