package com.jeremy.data.user.enums;

import com.jeremy.data.enums.BaseCodeEnum;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 13:15
 * @Description:
 */
public enum UserType implements BaseCodeEnum {
    NORMAL("NL","正常"),
    APPRVL("AL","认证"),
    STOP("SP","禁用");

    private String code;

    private String caption;

    UserType(String code, String caption){
        this.code = code;
        this.caption = caption;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }
}
