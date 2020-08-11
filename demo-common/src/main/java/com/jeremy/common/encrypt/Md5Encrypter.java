package com.jeremy.common.encrypt;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @Author: laizc
 * @Date: Created in 10:19 2019-12-16
 * @desc: MD5加密
 */
public class Md5Encrypter {

    private static Logger logger = LoggerFactory.getLogger(Md5Encrypter.class);

    public static String encrypt32(Object source){
        byte[] data;
        try {
            if (source instanceof File) {
                data = FileUtils.readFileToByteArray((File) source);
            }else if (source instanceof InputStream){
                data = IOUtils.toByteArray((InputStream) source);
            }else if (source instanceof Reader){
                data = IOUtils.toByteArray((Reader) source);
            }else if (source instanceof byte[]){
                data = (byte[]) source;
            }else {
                data =source.toString().getBytes();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
        return DigestUtils.md5Hex(data);
    }
}
