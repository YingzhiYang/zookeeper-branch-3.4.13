package com.zk.java.tool;

public class OutLooPTest {
    public static void main(String[] args) {
        int i=0;
        outerLoop:
        while (i<10){
            i++;
            switch (i){
                case 1:
                    System.out.println(i);
                    break;
                case 2:
                    System.out.println(i);
                    break;
                case 3:
                    System.out.println(i);
                    break;
                case 4:
                    System.out.println(i);
                    break;
                case 5:
                    System.out.println(i);
                    break outerLoop;
                case 6:
                    System.out.println(i);
                    break;
            }
        }
        System.out.println("jump out");
    }
}
