package com.ghost;

/**
 * Created by ghost on 2018-05-07.
 * 测试final 修饰符
 */
public class FinalTest {
    public static void main(String[] args) {
        //1.用final修饰的变量最多只能赋值一次 值不可变
        // 对象不可重新赋值或等于,但是内部属性可改变,例如数组和对象
        final String a;
        a = "s";
        final int b = 4;
        final Integer c = 5;
        final String[] d = new String[2];
        d[0]="a";
        d[1]="b";
        d[0]="c";
        System.out.println(d[0].toString());
        final int[] e = new int[2];
        e[0]=1;
        e[1]=2;
        e[0]=3;
        System.out.println(e[0]);




    }
}
