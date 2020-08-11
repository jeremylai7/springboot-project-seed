package com.jeremy.service.exception;

import java.text.MessageFormat;

/**
 * @Author: laizc
 * @Date: Created in 11:01 2019-01-07
 */
public class BusinessException extends Exception {
    private String code;

    private String[] args;

    public BusinessException(String code, String... args){
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage(){
        String message = ResponseCodes.getCodeMessage(code);
        if (args == null){
            return message;
        }
        return MessageFormat.format(message,args);
    }

}
