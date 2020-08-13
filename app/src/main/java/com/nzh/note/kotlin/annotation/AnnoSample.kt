// 告知编译器 本文件编译完了以后 命名为 AnnoSampleTest。
// JvmMultifileClass : 生成的文件名可重复
package com.nzh.note.kotlin.annotation

fun main() {

    val api = MyRetrofit.create<GithubApi.Users>()
    api.getUserName1("niuzhihua")
    api.getUserName2("niuzhihua", 1)

    api.getUserName3()
    api.getUserName3_()

    api.getUserName4("tom", 18, "desc")


//    val a = object :A{}
//    val aa = AA()
//    val b = A.B()
//    val c = A.B.C()
//    println(a.javaClass.enclosingClass)
//    println(aa.javaClass.enclosingClass)
//    println(b.javaClass.enclosingClass)
//    println(c.javaClass.enclosingClass)


}

class AA {

}

interface A {
    class B {

        class C {

        }
    }
}
