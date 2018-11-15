package com.baizhi.bio;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1 单一请求
 * 2 多次请求
 * 3 线程请求
 * 4 多线程请求
 * Created by wdwhwn on 2018/11/14.
 */
public class BIOServerTest {

    public static void main(String[] args) throws IOException {
//        service1();
//        service2();
//        service3();
        service4();
    }

    public static void service1() throws IOException {
        //                初始化  设定访问的端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器端开启端口，等待链接");

//        等待客户端 建立连接
        Socket accept = serverSocket.accept();
//        获取输入流
        InputStream inputStream = accept.getInputStream();
//        字节数组
        byte[] bytes = new byte[1024];
//        字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        输入流数据  通过输出流输出打印
        while (true) {
            int read = inputStream.read(bytes);
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(bytes, 0, read);
        }
//        shutdown
        System.out.println("从客户端获取的请求内容为：" + new String(byteArrayOutputStream.toByteArray()));
        accept.shutdownInput();
//        获取输出流
        OutputStream outputStream = accept.getOutputStream();
//        输出内容
        outputStream.write("客户端你好".getBytes());
//        完成输出
        accept.shutdownOutput();
        //        释放资源
        accept.close();
    }

    public static void service2() throws IOException {
        //                初始化  设定访问的端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器端开启端口，等待链接");
        while (true) {
//        等待客户端 建立连接
            Socket accept = serverSocket.accept();
//        获取输入流
            InputStream inputStream = accept.getInputStream();
//        字节数组
            byte[] bytes = new byte[1024];
//        字节输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        输入流数据  通过输出流输出打印
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bytes, 0, read);
            }
//        shutdown
            System.out.println("从客户端获取的请求内容为：" + new String(byteArrayOutputStream.toByteArray()));
            accept.shutdownInput();
//        获取输出流
            OutputStream outputStream = accept.getOutputStream();
//        输出内容
            outputStream.write("客户端你好".getBytes());
//        完成输出
            accept.shutdownOutput();
            //        释放资源
            accept.close();
        }
    }

    public static void service3() throws IOException {
        //                初始化  设定访问的端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器端开启端口，等待链接");
        while (true) {
//        等待客户端 建立连接
            Socket accept = serverSocket.accept();
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        //        获取输入流
                        InputStream inputStream = accept.getInputStream();
//        字节数组
                        byte[] bytes = new byte[1024];
//        字节输出流
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        输入流数据  通过输出流输出打印
                        while (true) {
                            int read = inputStream.read(bytes);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bytes, 0, read);
                        }
//        shutdown
                        System.out.println("从客户端获取的请求内容为：" + new String(byteArrayOutputStream.toByteArray()));
                        accept.shutdownInput();
//        获取输出流
                        OutputStream outputStream = accept.getOutputStream();
//        输出内容
                        outputStream.write("客户端你好".getBytes());
//        完成输出
                        accept.shutdownOutput();
                        //        释放资源
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }).start();
        }
    }

    public static void service4() throws IOException {
        //                初始化  设定访问的端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器端开启端口，等待链接");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
//        等待客户端 建立连接
            Socket accept = serverSocket.accept();
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println("当前线程为："+Thread.currentThread().getId());
                        //        获取输入流
                        InputStream inputStream = accept.getInputStream();
//        字节数组
                        byte[] bytes = new byte[1024];
//        字节输出流
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        输入流数据  通过输出流输出打印
                        while (true) {
                            int read = inputStream.read(bytes);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bytes, 0, read);
                        }
//        shutdown
                        System.out.println("从客户端获取的请求内容为：" + new String(byteArrayOutputStream.toByteArray()));
                        accept.shutdownInput();
//        获取输出流
                        OutputStream outputStream = accept.getOutputStream();
//        输出内容
                        outputStream.write("客户端你好".getBytes());
//        完成输出
                        accept.shutdownOutput();
                        //        释放资源
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }
}