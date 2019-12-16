package com.springbootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in 11:35 2019-12-13
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class Test4 {

    @Test
    public void ioTest() throws IOException {
        File file = new File("F:\\demo\\aa.txt");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[1000];
        int aa;
        while ((aa= inputStream.read(bytes))>0){
            System.out.println(new String(bytes,0,aa));
        }
        //System.out.println(aa);
        inputStream.close();

        String bb = "小明ABC";
        byte[] buffer = bb.getBytes();
        for (byte b: buffer){
            //展示字节
            //字节转成16进制方式
            System.out.println( b+"  "+ Integer.toHexString(b & 0xff)+ " ");
        }


    }

    @Test
    public void replaceTest() throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put(null,"aa");
        map.put("bb","b");
        //map.hashCode()
        String aa = "123";
        System.out.println(aa.hashCode());
       /* FileOutputStream fileOutputStream = new FileOutputStream("aa.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);

        String aa = "http://www.baidu.com,http://www.taobao.com";
        System.out.println(aa.replaceAll("http://",""));*/
    }

}
