package com.baizhi.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wdwhwn on 2018/11/13.
 */
public class NioTestCopy {
    public static void main(String[] args) throws IOException {
        FileChannel channel = new FileInputStream("D:\\123.jpg").getChannel();
        FileChannel channel1 = new FileOutputStream("D:\\1234565.jpg").getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        while(true){
            int read = channel.read(allocate);
            if(read == -1){
                break;
            }
            allocate.flip();
            channel1.write(allocate);
            allocate.clear();
        }
        channel.close();
        channel.close();
    }
}
