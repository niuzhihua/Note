package com.nzh.note.kotlin.kotlin_1_4;

public abstract class Parent extends Parent0 {
    public Parent() {
        System.out.println("constructor Parent");
    }

    public int a() {
        System.out.println("--parent a()--");
        return 123;
    }

    public int b() {
        System.out.println("--parent  b()--");
        return 111;
    }

    public void dispatchPointerEvent() {
        System.out.println("parent : dispatchPointerEvent");
        int a = a();
        System.out.println("a=" + a);
    }


}