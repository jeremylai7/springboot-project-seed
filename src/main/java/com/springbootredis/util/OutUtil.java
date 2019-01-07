package com.springbootredis.util;

import com.alibaba.fastjson.JSONObject;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.Result;


/**
 * 输出到HttpServletResponse工具类
 */
public class OutUtil {

    public static Result success(Object data) {
        return getResult(ResponseCodes.SUCCESS, data);
    }

    public static Result error(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(new JSONObject());
        return result;
    }

    public static Result getResult(String code, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(ResponseCodes.getCodeMessage(code));
        if (data != null) {
            result.setData(data);
        } else {
            result.setData(new JSONObject());
        }
        return result;
    }

    public static JSONObject  respose(String code, Object data){
        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",ResponseCodes.getCodeMessage(code));
        if(data != null){
            result.put("data",data);
        }else{
            result.put("data","");
        }
        return  result;
    }
}
