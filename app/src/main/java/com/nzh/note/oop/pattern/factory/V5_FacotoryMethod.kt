package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.RuntimeException
import kotlin.reflect.KClass

// 工厂模式 : 工厂方法 第2步
// 优点：
//    需要添加新的 Fetcher时，只要创建 XXXFetcher 和 XXXFetcherFactory，并添加到 IFactory2.map中 就可以了。
//     代码改动少。符合开闭原则。

// 缺点：

// 毕竟只是一个创建对象操作，没有必要用 工厂方法。
// 工厂方法模式 增加了类，增加了复杂度。

// 应用场景： 当创建对象比较复杂的时候用 工厂模式。否则就直接 new 吧。

// 如果 需要创建的 Fetcher不是 很多的话(6个之内吧)，推荐还是用简单工厂 的方式。
// 如果 创建对象的逻辑比较复杂，不是简单的new一下， 还要组合其他对象，做一些初始化操作，
//    推荐使用工厂方法模式。 这样可以将复杂的创建逻辑 分配到工厂中，让每个工厂都不会太复杂。

// 如果 想在创建对象时避免 分支逻辑(if-else,switch,when)，那么用工厂方法。


interface IFactory2 {  // 创建工厂的工厂
    companion object {
        private val map = mutableMapOf<KClass<out Any>, IFactory>()

        init {
            map[String::class] = StringFetcherFactory()
            map[File::class] = FileFetcherFactory()
            map[Uri::class] = UriFetcherFactory()
            map[ByteArray::class] = ByteArrayFetcherFactory()
            map[InputStream::class] = InputStreamFetcherFactory()
        }

        fun createFactory(model: Any): IFactory? {
            return map[model::class]
        }
    }

}

class Loader6 {
    
    fun load(model: Any): Result {
        // 创建Fetcher工厂
        val factory = IFactory2.createFactory(model)
        factory ?: throw RuntimeException("IFactory created failure")

        // 创建Fetcher
        val fetcher = factory.createFetcher()
        return fetcher.fetch(model)
    }
}

fun main() {
    val loader = Loader6()
    val model = "baidu.png"
    println(loader.load(model))
}
