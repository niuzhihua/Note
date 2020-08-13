package com.nzh.note.kotlin.myContinueation.async_await

import com.nzh.note.kotlin.myContinueation.context.MyJob


interface DefferredJob<T> : MyJob {

    // 相当于 有返回值的join
    suspend fun await(): T

}