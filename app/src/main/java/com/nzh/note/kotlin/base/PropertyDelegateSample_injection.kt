package com.nzh.note.kotlin.base

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


// 泛型、泛型特化、属性代理 综合运用：实现实例的注入
fun main() {
    // 初始化model
    ModelA()
    ModelB()

    val modelA by modelOf<ModelA>()
    val modelB by modelOf<ModelB>()

    println(modelA.getInfo())
    println(modelB.getInfo())

    // 通过属性名 的方式从代理中获取 实例 ： 缺点 是 需要注意名称的映射
    // 变量 ModelA 的名字要 和 存储实例map中的key 映射。
    val ModelA: ModelA by ModelDelegate2()

    //  变量ModelB 的名称 也可叫 abc, 但是这个abc 必须(在代理中获取时)映射 到 map中的key。
    val ModelB: ModelB by ModelDelegate3

    println(ModelA.getInfo())
    println(ModelB.getInfo())

}

/**
 * 方式1、通过 内联特化 传递类型 给 ModelDelegate
 * 内联特化应用： 将类型 传递给 ModelDelegate
 */
inline fun <reified T : AbsModel> modelOf(): ModelDelegate<T> {
    return ModelDelegate(T::class.java)
}

/**
 * 方式2、使用属性代理时，通过属性名称来获取 对象。
 *
 */
object ModelDelegate3 {

    operator fun <T : AbsModel> getValue(thisRef: Any?, property: KProperty<*>): T {
        // 方式1、
        return ModelCache.run {
            println(property.name.capitalize())
            property.name.capitalize().getByName1()
        }
//        // 方式2、
//        return ModelCache.getByName2(property.name)
    }
}

class ModelDelegate2<T : AbsModel> {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        // 方式1、
//        return ModelCache.run {
//            println(property.name.capitalize())
//            property.name.capitalize().getByName1()
//        }
        // 方式2、
        return ModelCache.getByName2(property.name)
    }
}


class ModelDelegate<T : AbsModel>(val clazz: Class<T>) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return ModelCache.get(clazz)
    }
}

open class AbsModel {

    init {
        ModelCache.regist(this)

        ModelCache.run {
            this@AbsModel.regist2()
        }
    }
}

class ModelA : AbsModel() {

    public fun getInfo(): String {
        return "{ model A data }"
    }
}

class ModelB : AbsModel() {

    fun getInfo(): String {
        return "{ model B data }"
    }
}

object ModelCache {

    // model 缓存池 : 通过类型(Class) 来获取 model
    private val map = ConcurrentHashMap<Class<out AbsModel>, AbsModel>()
    // model 缓存池 : 通过名称(String)来获取 model
    private val map2 = ConcurrentHashMap<String, AbsModel>()

    // 方式1、通过函数参数 传递 AbsModel 实例。
    fun regist(model: AbsModel) {
        map[model.javaClass] = model
    }

    // 方式2、通过扩展函数 传递 this (AbsModel 实例)
    fun AbsModel.regist2() {
        map2[this.javaClass.simpleName] = this
    }


    // 方式1、直接传递 java Class
    fun <T : AbsModel> get(clazz: Class<T>): T {
        return map[clazz] as T
    }

    fun <T : AbsModel> String.getByName1(): T {
        return map2[this] as T
    }

    fun <T : AbsModel> getByName2(name: String): T {
        return map2[name] as T
    }

    // 方式2、KClass 类上定义扩展函数 get. 调用get函数时拿到this（KClass实例），再拿到javaClass
    fun <T : AbsModel> KClass<T>.getByKClass(): T {

        return map[this.java] as T
    }


}