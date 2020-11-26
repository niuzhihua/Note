package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.IllegalArgumentException

class Loader {
    fun load(model: Any): Result {
        var fetcher: IFetcher
        when (model) {
            is String -> {
                fetcher = StringFetcher()
            }
            is ByteArray -> {
                fetcher = ByteArrayFetcher()
            }
            is InputStream -> {
                fetcher = InputStreamFetcher()
            }
            is Uri -> {
                fetcher = UriFetcher()
            }
            is File -> {
                fetcher = FileFetcher()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }

        val result = fetcher.fetch(model)

        return result
    }
}

// 不使用 工厂模式的 例子代码
fun main() {
    val loader = Loader()
    val model = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png"
    println(loader.load(model))
}

