package com.springbootredis;

import javafx.scene.media.SubtitleTrack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in 9:06 2019-04-30
 */
public class Test1 {
    public static void main(String[] args) {
        /*Test1 test = new Test1();
        for (int i = 0; i <5 ; i++) {
            test.test();
        }*/
        Test1 test1 = new Test1();
        char[] s = new char[3];
        int a =test1.reverseString(3);
        System.out.println(a);
    }

    public void test(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <100000000; i++) {
            list.add(100);
        }
        //for遍历
        long beginTime = new Date().getTime();
        int array = 0;
        for (int i = 0; i <list.size() ; i++) {
            array = list.get(i);
        }
        long endTime = new Date().getTime();
        System.out.println();

        //foreach循环
        long beginTime1 = new Date().getTime();
        for(Integer num:list){
            array = num;
        }
        long endTime1 = new Date().getTime();
        System.out.println((endTime - beginTime) +"--->"+ (endTime1 - beginTime1));
    }

    public char[] reverseString(char[] s){
        
        return s;
    }

    public int reverseString(int s){
        if (s == 1 || s==0){
            return 1;
        }
        String[] a = new String[5];


        return reverseString(s-1) - reverseString(s-2);
    }

}