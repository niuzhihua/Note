package com.nzh.note.kotlin.base

// 1、constructor可以省略不写
// 1.1、只定义了 构造参数
class User constructor(name: String, age: Int)

// 1.2、构造函数列表中：加 val/var 表示即定义了构造器参数 ， 又定义了属性 。
class User2(val name: String, val age: Int)

// 1.3、 定义了1个 属性 name。 2个构造参数  name,age.
class User3(val name: String, age: Int)

// 1.3、私有化属性 name，外部无法访问
class User31(private val name: String, age: Int)

// 1.4、总结：
//      对于构造参数：只在构造函数、init代码块、属性初始化 才可见。
//        对于属性：类全局可见。

//2、工厂函数：跟类名相同的函数。
//      用途：1、通过函数重载的方式来 创建对象。
//            2、对象缓存池。
val hashMap = HashMap<String, Person>()

class Person(var name: String, var age: Int = 0) {
    init {
        hashMap.put(name, this)
    }

    override fun toString(): String {
        return "name=${name},age=${age}"
    }
}

// 1、函数重载
fun Person(name: String): Person {
    return Person(name, 1)
}

// 1、函数重载
fun Person(): Person {
    return Person("default", 0)
}

// 1、函数重载
fun Person(d: Double) = Person("1", 4)

// 2、
fun PersonByCache(name: String): Person {
    return hashMap[name] ?: Person()
}

fun main() {
    val u = User("a", 1)
    // println("${u.name} ${u.age}") // 没有name/age属性

    val u2 = User2("a", 1)
    println("${u2.name} ${u2.age}")


    // 函数工厂

    val p1 = Person("tom")      // 通过函数工厂 得到 实例  ： 都是函数重载的应用
    val p2 = Person(3.0)            // 通过函数工厂 得到 实例  ： 都是函数重载的应用
    val p3 = Person()                  // 通过函数工厂 得到 实例  ： 都是函数重载的应用
    val p4 = Person("jerry", 12)     // 直接调用构造 得到 实例 ：
    val p5 = PersonByCache("jerry")  // 通过函数工厂 得到 实例 ： 都是函数重载的应用
    println(p1)
    println(p2)
    println(p3)
    println(p4)
    println("${p5} ${p4 == p5}")

}