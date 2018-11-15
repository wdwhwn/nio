package com.baizhi.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by wdwhwn on 2018/11/14.
 */
//基于BIO的 客户端编程
public class BIOClientTest {
    public static void main(String[] args) throws IOException {
//        初始化客户端链接对象
        Socket socket = new Socket("127.0.0.1", 9999);
//        IO 操作
//        获取输出流  对服务器端发送请求
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好，服务器端".getBytes());
        socket.shutdownOutput();
//       获取输入流
        InputStream inputStream = socket.getInputStream();
//        获取字节书输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//         字节数组
        byte[] bytes = new byte[1024];
        while(true){
            int read = inputStream.read(bytes);
            if(read==-1){
                break;
            }
            byteArrayOutputStream.write(bytes,0,read);
        }
        System.out.println("接收到的响应数据为："+new String(byteArrayOutputStream.toByteArray()));
        socket.shutdownInput();
//        释放资源
        socket.close();
    }
}
