package com.nzh.note.oop.pattern.factory

import android.net.Uri
import java.io.File
import java.io.InputStream
import java.lang.RuntimeException

// 工厂模式 : 工厂方法 第一步
// 当我们要新增一种 IFetcher 的时候，只要增加一个 实现了 IFactory接口的Factory即可。

// 到这一步，既没有去掉分支语句，反而使得设计更复杂了。  如何解决？
// 再增加一层 , 用来创建 IFactory的实例，也就是工厂的工厂。
interface IFactory {
    fun createFetcher(): IFetcher
}

class StringFetcherFactory : IFactory {
    override fun createFetcher(): IFetcher {
        return StringFetcher()
    }
}

class FileFetcherFactory : IFactory {
    override fun createFetcher(): IFetcher {
        return FileFetcher()
    }
}

class ByteArrayFetcherFactory : IFactory {
    override fun createFetcher(): IFetcher {
        return ByteArrayFetcher()
    }
}

class UriFetcherFactory : IFactory {
    override fun createFetcher(): IFetcher {
        return UriFetcher()
    }
}

class InputStreamFetcherFactory : IFactory {
    override fun createFetcher(): IFetcher {
        return InputStreamFetcher()
    }
}

class Loader5 {
    fun load(model: Any): Result {
        val factory: IFactory
        when (model) {
            is String -> {
                factory = StringFetcherFactory()
            }
            is File -> {
                factory = FileFetcherFactory()
            }
            is ByteArray -> {
                factory = ByteArrayFetcherFactory()
            }
            is InputStream -> {
                factory = InputStreamFetcherFactory()
            }
            is Uri -> {
                factory = UriFetcherFactory()
            }
            else -> {
                throw RuntimeException("model type matched failure")
            }
        }
        val fetcher = factory.createFetcher()

        return fetcher.fetch(model)
    }
}
