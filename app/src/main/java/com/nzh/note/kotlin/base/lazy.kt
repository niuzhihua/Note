package com.nzh.note.kotlin.base

import android.widget.TextView

// 属性的延迟初始化 ：有如下几种方式。

class Activity {

    // 方式1 ： 不推荐
    var tv: TextView? = null
    // 方式2 ： ( kotlin1.2 )不推荐
    // 开发这能够确定变量的 生命周期情况下 使用，否则不推荐。
    lateinit var tv2: TextView

    // 方式3：只有属性第一个被访问时，才执行 代码块的代码来初始化。
    val person by lazy {
        Person()
    }

    val tv4: TextView by lazy {
        findViewById(666)
    }
    val tv44 by lazy {
        findViewById(666)
    }

    fun findViewById(id: Int): TextView {
        return TextView(null)
    }

    fun onCreate() {

        // 采用 方式1 使用属性
//        tv = findViewById(R.id.xxx)
        tv?.text = "haha"
        tv!!.text = "haha"

        // 采用 方式2 使用属性
        if (::tv2.isInitialized) {
            //        tv = findViewById(R.id.xxx)
            tv?.text = "haha"
        }

        // 方式 3
        println(person.name)
        tv4.text = "haha"
        tv44.text = "haha"

    }
}