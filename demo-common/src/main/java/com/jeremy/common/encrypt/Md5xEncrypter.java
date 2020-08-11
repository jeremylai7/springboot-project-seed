package com.jeremy.common.encrypt;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in 10:41 2019-12-16
 * @desc: 扩展的MD5加密器
 */
public class Md5xEncrypter {

    public static final int ENCRYPTED_TEXT_LENGTH = 64;

    private final int staticKey;

    public Md5xEncrypter(int staticKey){
        if (staticKey < 0 || staticKey > 31){
            throw new IllegalArgumentException("The argument 'staticKey' must between [0,31]");
        }
        this.staticKey = staticKey;
    }

    public String encryptByMd5Source(String md5Source,Object secretKey){
        return encryptByMd5Source(md5Source,secretKey,this.staticKey);
    }

    private Integer[] getMd5SourceCharIndexs(int staticKey,int maxIndex){
        char[] c = Md5Encrypter.encrypt32(staticKey).toCharArray();
        int length = c.length;
        List<Integer> list = new ArrayList<>(length);
        for (int i = 0; i <length ; i++) {
            int value;
            if (i == 0){
                value = c[i] % maxIndex;
            }else {
                value = (list.get(i - 1) + c[i] % maxIndex) % maxIndex;
                while (list.contains(value)){
                    if (value < maxIndex - 1){
                        value++;
                    }else {
                        value = 0;
                    }
                }
            }
            list.add(value);
        }
        return list.toArray(new Integer[length]);
    }

    private String encryptByMd5Source(String md5Source,Object secretKey,int staticKey){
        if (secretKey == null){
            secretKey = "";
        }
        String keyMd5 = Md5Encrypter.encrypt32(staticKey + secretKey.toString());
        char[] keyChars = keyMd5.toCharArray();
        char[] sourceChars = md5Source.toLowerCase().toCharArray();
        int length = keyChars.length + sourceChars.length;
        char[] c = new char[length];
        Integer[] sourceCharIndexs = getMd5SourceCharIndexs(staticKey,length);
        for (int i = 0; i < sourceChars.length; i++) {
            c[sourceCharIndexs[i]] = sourceChars[i];
        }
        int index = 0;
        for (int i = 0; i <c.length ; i++) {
            if (c[i] == 0){
                c[i] = keyChars[index++];
            }
        }
        return new String(c);
    }

}
