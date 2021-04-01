package com.nzh.note.kotlin.kotlin_1_4;

public class Sub extends Parent {
    public Sub() {
        System.out.println("constructor Sub");
    }

    @Override
    public int a() {
        System.out.println("--Sub a()--");
        b();
        return 456;
    }

    @Override
    public int b() {
        System.out.println(super.b());

        return 999;
    }
}