package com.zk.java.tool;

import java.util.LinkedList;

public class LinkArraysTest {

    public static void main(String[] args) {
        LinkedList<String> queue=new LinkedList<>();
        queue.add("aaa");
        queue.add("sss");
        queue.add("ddd");
        queue.add("fff");
        queue.add("ggg");
        String test=queue.remove();
        System.out.println(test);
        System.out.println("***********After delete*************");
        for (String s: queue) {
            System.out.println(s);
        }
    }
}
