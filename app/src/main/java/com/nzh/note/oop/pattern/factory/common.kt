package com.nzh.note.oop.pattern.factory

class Result(val data: String) {
    override fun toString(): String {
        return data
    }
}

interface IFetcher {
    fun fetch(model: Any): Result
}

class ByteArrayFetcher : IFetcher {
    override fun fetch(model: Any): Result {
        return Result("ByteArrayFetcher: bitmap")
    }
}

class InputStreamFetcher : IFetcher {
    override fun fetch(model: Any): Result {
        return Result("InputStreamFetcher: bitmap")
    }
}

class UriFetcher : IFetcher {
    override fun fetch(model: Any): Result {
        return Result("UriFetcher: bitmap")
    }
}

class StringFetcher : IFetcher {
    override fun fetch(model: Any): Result {
        return Result("StringFetcher: bitmap")
    }
}

class FileFetcher : IFetcher {
    override fun fetch(model: Any): Result {
        return Result("FileFetcher: bitmap")
    }
}