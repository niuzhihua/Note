package com.nzh.note.kotlin.base

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

open class SharedPref(val appContext: Context) {

    // 公共默认值
    protected val stringPref = SharedPrefsDelegate(appContext, "initValue", "defaultName")

    // 指定默认值
    protected fun stringPref(initValue: String, defaultValue: String = ""): SharedPrefsDelegate<String> {
        return SharedPrefsDelegate(appContext, initValue, defaultValue)
    }

    protected fun stringPref(initValue: String): SharedPrefsDelegate<String> {
        return SharedPrefsDelegate(appContext, initValue, "defaultName2")
    }

    protected val intPref = SharedPrefsDelegate(appContext, 20, 1)
    protected val floatPref = SharedPrefsDelegate(appContext, 0.00f, 100.00f)
    protected val boolPref = SharedPrefsDelegate(appContext, true, false)
    protected val longPref = SharedPrefsDelegate(appContext, 1000L, 0L)
}

class Config(context: Context) : SharedPref(context.applicationContext) {

    var name by stringPref  // 什么都不指定，值为默认
    var firstName by stringPref("firstName")  // 指定初始值
    var lastName by stringPref("lastName", ">")  // 指定初始值，默认值

    var age by intPref
    var money by floatPref
    var isXX by boolPref
    var longProperty by longPref


    override fun toString(): String {
        return """name:${name}, 
             firstName:${firstName}
             lastName:${lastName}
             """.trimMargin()
    }
}


class SharedPrefsDelegate<T>(context: Context, var v: T, var defaultValue: T) {

    companion object

    val sharedPref by lazy {
        context.applicationContext.getSharedPreferences("property_delegate", Context.MODE_PRIVATE)
    }


    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {

        val result by lazy {

            when (v) {
                is String -> {
                    sharedPref.getString(property.name, (defaultValue as String))
                }
                is Int -> {
                    sharedPref.getInt(property.name, (defaultValue as Int))
                }
                is Long -> {
                    sharedPref.getLong(property.name, (defaultValue as Long))
                }
                is Float -> {
                    sharedPref.getFloat(property.name, (defaultValue as Float))
                }
                is Boolean -> {
                    sharedPref.getBoolean(property.name, (defaultValue as Boolean))
                }
                else -> {
                    defaultValue
                }

            }
        }

        return result as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.v = value
        val edit = sharedPref.edit()

        when (v) {
            is String -> {
                edit.putString(property.name, v as String)
            }
            is Int -> {
                edit.putInt(property.name, v as Int)
            }
            is Long -> {
                edit.putLong(property.name, v as Long)
            }
            is Float -> {
                edit.putFloat(property.name, v as Float)
            }
            is Boolean -> {
                edit.putBoolean(property.name, v as Boolean)
            }
            else -> {
                throw IllegalArgumentException("类型错误")
            }

        }
        edit.apply()

    }

}
