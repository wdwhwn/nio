package com.baizhi.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wdwhwn on 2018/11/14.
 */
//NIO 编程   使用了事件列表   同步未阻塞IO   不用等到一个完整的IO执行完毕才会开启线程
public class NIOTestTruth {
    public static void main(String[] args) throws IOException {
//            创建引导对象  注册列表的通道必须为阻塞  且绑定 事件的通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
//        获取选择器
        Selector selector = Selector.open();
//        在注册列表注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("1 服务器启动成功");
//        事件列表遍历
        while(true){
//            获取事件列表中  selectionKey的数量
            int i = selector.select();
//            表明只有当事件列表中存在selectionkey时才执行下面方法
            if (i != 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if(next.isAcceptable()){
                        System.out.println("2 等待接收客户端请求");
//                        获取服务器套接字通道
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
//                          获取连接对象
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }else if(next.isReadable()){
                        System.out.println("3 读取客户端发送的请求");
//                        获取通道
                        SocketChannel ss= (SocketChannel) next.channel();
//                        获取buffer
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
//                        获取输出流
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        while(true){
                            int read = ss.read(allocate);
                            if(read==-1){
                                break;
                            }
                            allocate.flip();
                            byteArrayOutputStream.write(allocate.array(),0,read);
                            allocate.clear();
                        }
                        System.out.println("客户端发送的请求为:"+new String(byteArrayOutputStream.toByteArray()));
//                        关闭输入功能
                        ss.shutdownInput();
//                        注册输出事件
                        ss.configureBlocking(false);
                        ss.register(selector, SelectionKey.OP_WRITE);
                    }else if(next.isWritable()){
                        System.out.println("4 向客户端响应数据");
//                        从selectionkey中获取通道
                        SocketChannel sc= (SocketChannel) next.channel();
//                          创建buffer
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
//                        输出
                            allocate.put("客户端你好呀！！！".getBytes());
                            allocate.flip();
                            sc.write(allocate);
                            allocate.clear();
//                            注册
                        sc.shutdownOutput();
//                        释放资源
                        sc.close();
                    }
                    iterator.remove();
                }

            }
        }
//
    }
}
