package com.jeremy.admin.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: laizc
 * @Date: Created in 11:38 2019-01-10
 * @desc 工程工具类
 */
public class ProjectUtil {

    private static final String USER = "user";

    private static final String USER_ID = "userId";

    public static Object getUser(HttpServletRequest request) {
        Object user = request.getAttribute(USER);
        if (user ==null){
            return null;
        }
        return user;
    }

    public static void setUser(HttpServletRequest request, Object user){
        request.setAttribute(USER,user);
    }

    public static Object getUserId(HttpServletRequest request){
        Object userId = request.getAttribute(USER_ID);
        if (userId == null){
            return null;
        }
        return userId;
    }

    public static void setUserId(HttpServletRequest request, Object object){
        request.setAttribute(USER_ID,object);
    }
}
