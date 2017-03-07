package io.vicp.frlib.util;

import java.util.Random;

/**
 * Created by zhoudr on 2017/2/20.
 */
public class Main {

    public static int nextInt(int num) {
        return new Random().nextInt(num);
    }

    public static void main(String[] args) throws Exception {
        Class staticFinalTestClass = StaticFinalTest.class;
        System.out.println("after creating class ref");
        System.out.println(StaticFinalTest.staticConstantFinal);
        System.out.println(StaticFinalTest.staticConstantNonFinal);
        System.out.println(StaticFinalTest.staticNonConstantFinal);
        Class.forName("io.vicp.frlib.util.StaticFinalTest").newInstance();
        System.out.println(StaticTest.staticNonFinal);
    }
}

class StaticFinalTest {
    public static final int staticConstantFinal = 258; // 常数静态域（因为存在final，所以在类验证阶段的准备工作时赋值）
    public static int staticConstantNonFinal = 100;// 常数静态域(类验证阶段为0，初始化时赋值为100)
    public static final int staticNonConstantFinal = Main.nextInt(1000);

    static {
        System.out.println("StaticFinalTest");
    }

    {
        System.out.println("non static block");
    }
}

class StaticTest {
    public static int staticNonFinal = 157;// 在类验证阶段的准备工作时赋值为0，而在类初始化时才赋值为157

    static {
        System.out.println("StaticTest");
    }
}
