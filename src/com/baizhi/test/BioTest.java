package com.baizhi.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wdwhwn on 2018/11/13.
 */
public class BioTest {
    public static void main(String[] args) throws IOException {
//        复制
        FileInputStream fileInputStream = new FileInputStream("D:\\123.jpg");
        FileOutputStream outputStream = new FileOutputStream("D:\\2345.jpg");
        byte[] bytes = new byte[1024];
        while(true){
            int read = fileInputStream.read(bytes);
            if(read == -1){
                break;
            }
            outputStream.write(bytes,0,read);
        }
        fileInputStream.close();
        outputStream.close();
    }
}
