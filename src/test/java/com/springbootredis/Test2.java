package com.springbootredis;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in 13:56 2019-05-31
 */
public class Test2 {
    public static void main(String[] args) {
        /*char[] s ={'H','a','n','n','a','h'};
        swap(0,s.length -1,s);*/
        String a = "2";
        String b = "4";
        transform(a,b);
        System.out.println(a+"  "+b);
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,2);
        Integer aa = map.get(2);
        System.out.println(aa);
        String aav = "aa";
        aav.hashCode();
    }

    public static void swap(int start, int end, char[] s){
        if (start >= end){
            return;
        }
        char temp = s[start];
        s[start] = s[end];
        s[end] = temp;
        swap(start + 1,end-1,s);
    }

    public static void transform(String a,String b){
        a = "20";
        b = "30";
    }

}
