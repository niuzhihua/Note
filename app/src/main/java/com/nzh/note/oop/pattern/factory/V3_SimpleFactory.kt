package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.IllegalArgumentException

// 工厂模式：简单工厂 的第一种方式
class SimpleFactory {
    companion object {
        fun createFetcher(model: Any): IFetcher {
            // 如果非要将分支判断去掉，如何做呢？常见做法 是利用多态。
            when (model) {
                is String -> {
                    return StringFetcher()
                }
                is ByteArray -> {
                    return ByteArrayFetcher()
                }
                is InputStream -> {
                    return InputStreamFetcher()
                }
                is Uri -> {
                    return UriFetcher()
                }
                is File -> {
                    return FileFetcher()
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}

class Loader3 {
    fun load(model: Any): Result {
        val fetcher = SimpleFactory.createFetcher(model)
        val result = fetcher.fetch(model)
        return result
    }
}

fun main() {
    val loader = Loader3()
    val model = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png"
    println(loader.load(model))
}