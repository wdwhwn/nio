package com.baizhi.nio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wdwhwn on 2018/11/14.
 */
//伪网络编程  没有使用事件列表
public class NIOTest {
    public static void main(String[] args) throws IOException {
//        获取引导对象   套接字通道
        ServerSocketChannel open = ServerSocketChannel.open();
//        绑定端口
        open.bind(new InetSocketAddress(8888));
//        指定为阻塞通道
        open.configureBlocking(true);
        System.out.println("NIO 伪网络编程");
//        建立连接对象
        SocketChannel accept = open.accept();
        System.out.println("测试是否为阻塞");
//        IO操作
//        创建buffer
        ByteBuffer allocate = ByteBuffer.allocate(1024);
//        创建字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        由读到写  flip（） clear（） 并打印
        while(true){
            int read = accept.read(allocate);
            if(read==-1){
                break;
            }
            allocate.flip();
            byteArrayOutputStream.write(allocate.array(),0,read);
            allocate.clear();
        }
//        读操作完毕
        System.out.println("客户端发送的请求为："+new String(byteArrayOutputStream.toByteArray()));
        accept.shutdownInput();
//        创建输出流  响应给客户端
        allocate.put("NIO发送：客户端你好".getBytes());
        allocate.flip();
        accept.write(allocate);
        accept.shutdownOutput();
//        释放资源
        accept.close();

    }
}
