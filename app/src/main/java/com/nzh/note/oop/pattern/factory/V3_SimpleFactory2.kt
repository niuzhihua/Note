package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.RuntimeException
import kotlin.reflect.KClass

// 工厂模式：简单工厂 的第二种方式
class SimpleFactory2 {
    companion object {
        private val map = mutableMapOf<KClass<out Any>, IFetcher>()

        init {
            map[String::class] = StringFetcher()
            map[InputStream::class] = InputStreamFetcher()
            map[Uri::class] = UriFetcher()
            map[File::class] = FileFetcher()
            map[ByteArray::class] = ByteArrayFetcher()
        }

        fun createFetcher(model: Any): IFetcher {
            val fetcher = map.get(model::class)
            return fetcher ?: throw RuntimeException("fecher is null")
        }
    }
}

class Loader4 {
    fun load(model: Any): Result {
        val fetcher = SimpleFactory2.createFetcher(model)
        val result = fetcher.fetch(model)
        return result
    }
}

fun main() {
    val loader = Loader4()
    val model = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png"
    println(loader.load(model))
}