package com.springbootredis;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: laizc
 * @Date: Created in 17:06 2019-06-12
 */
public class Test3 implements Runnable{
    private String threadName;

    public Test3(String threadName){
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i <100 ; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName+" "+i);
        }
    }

    public static void main(String[] args) {
        Test3 test3 = new Test3("线程1");
        Thread thread = new Thread(test3);
        thread.start();
        Test3 test4 = new Test3("线程2");
        Thread thread2 = new Thread(test4);
        thread2.start();
        final String aa = new String();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("3","3");
    }
}
