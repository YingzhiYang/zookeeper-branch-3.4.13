package com.zk.java.tool;

public class SwitchBreakTest {
    public static void main(String[] args) {
        int a=0, b=2,c=3;
        while (a<10){
            switch (a){
                case 1:
                    System.out.println("continue 1");
                    break;
                case 2:
                    b=3;
                    System.out.println("b is 3");
                    break;
                case 3:
                    if (b==2){
                        System.out.println("b==2");
                        a=3;
                        break;
                    }else if (c==3){
                        System.out.println("c==3");
                    }else if(c==1){
                        System.out.println("c==1");
                    }
                    System.out.println("we are not out");
                    break;
                case 4:
                    System.out.println("a==4");
                    break;
            }
            a++;
            System.out.println("a++");
        }

        System.out.println("done");
    }
}
