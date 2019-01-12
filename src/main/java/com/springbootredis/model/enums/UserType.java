package com.springbootredis.model.enums;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 13:15
 * @Description:
 */
public enum  UserType implements BaseCodeEnum {
    NORMAL("NL","正常"),
    APPRVL("AL","认证"),
    STOP("SP","禁用");

    private String code;

    private String caption;

    UserType(String code,String caption){
        this.code = code;
        this.caption = caption;
    }

    @Override
    public String getCode() {
        System.out.println(11111);
        return this.code;
    }

    @Override
    public String getCaption() {
        System.out.println(2222);
        return this.caption;
    }
}
