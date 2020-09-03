package com.zk.java.tool;

import java.util.ArrayList;

public class SwitchDefaultTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("0");
        int a = 0;
        while (a < 10) {
            switch (a) {
                case 1:
                    list.add("1");
                    break;
                case 2:
                    list.add("2");
                    break;
                case 3:
                    list.add("3");
                    break;
                case 4:
                    list.add("4");
                    break;
                default:
                    list.add("10");
            }
            a++;
        }
        for (String s: list ) {
            System.out.println(s.toString());
        }
        System.out.println("done");
    }
}
