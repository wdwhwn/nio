package com.baizhi.test;

import java.nio.ByteBuffer;

/**
 * Created by wdwhwn on 2018/11/13.
 */
public class NIoTest {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        System.out.println(allocate.position()+" ||"+allocate.limit()+"||"+allocate.capacity());
        allocate.put("abc".getBytes());
        System.out.println(allocate.position()+" ||"+allocate.limit()+"||"+allocate.capacity());
        allocate.flip();
        System.out.println(allocate.position()+" ||"+allocate.limit()+"||"+allocate.capacity());
        allocate.get();
        allocate.get();
        allocate.get();
        System.out.println(allocate.position()+" ||"+allocate.limit()+"||"+allocate.capacity());
        allocate.clear();
        System.out.println(allocate.position()+" ||"+allocate.limit()+"||"+allocate.capacity());


    }
}
