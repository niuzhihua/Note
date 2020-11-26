package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.IllegalArgumentException

class Loader2 {

    // 为了 让类的职责更加单一，代码更加清晰，继续重构，将此函数放入到独立的类中。
    fun createFetcher(model: Any): IFetcher {
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

    fun load(model: Any): Result {
        val fetcher = createFetcher(model)
        val result = fetcher.fetch(model)
        return result
    }
}

fun main() {
    val loader = Loader2()
    val model = "png data".toByteArray()
    println(loader.load(model))
}