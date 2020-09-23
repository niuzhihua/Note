package com.nzh.note.kotlin.myContinueation.async_await

import com.nzh.note.kotlin.myContinueation.context.MyJob


interface DefferredJob<T> : MyJob {

    /**
     * 1、相当于 有返回值的join
     * 2、await是主动获取结果，和取消操作矛盾。
      */
    suspend fun await(): T

}