package com.nzh.note.kotlin.annotation

import java.lang.StringBuilder
import java.lang.reflect.Proxy


val lambdaEnclosingClses = { currentClass: Class<*>? ->
    var tempCls = currentClass
    sequence {

        while (tempCls != null) {
            yield(tempCls)
            tempCls = tempCls?.enclosingClass
        }
    }
}


object MyRetrofit {


    /**
     * retrofit :
     *  1、将注解信息读取出来
     *  2、通过动态代理为 api 接口生成代理实现类
     *  3、在代理实现类中 调用okhttp (传入 注解参数值)
     */

    inline fun <reified T> create(): T {

        // 获取接口中的方法 : key ：name   value: Method
        val methodMap = T::class.java.declaredMethods.map { it.name to it }.toMap()

        val classloader = T::class.java.classLoader
        val interfaces = arrayOf(T::class.java)


        // 生成代理对象
        return Proxy.newProxyInstance(classloader, interfaces) { proxy, method, args ->

            // 获取参数的所有注解
            var paramAnnotions = mutableListOf<Annotation>()
            method.parameterAnnotations?.forEach {
                it.forEach {
                    paramAnnotions.add(it)
                }
            }

            // 获取所有的 @Path 注解
            val paramPathAnotationMap = method.parameters?.mapIndexed { index, parameter ->
                parameter to index
            }.takeWhile {
                it.first.isAnnotationPresent(Path::class.java)
            }.toMap().map {
                it.key.getAnnotation(Path::class.java).param to args[it.value]
            }.toMap()


            //获取所有的  @Query 注解
            //  将 method 上带 @query注解的参数列表 整理为map. key：@query值  value: 实参
            val queryAnotationMap = method.parameters?.mapIndexed { index, parameter ->
                parameter to index
            }.takeWhile {
                it.first.isAnnotationPresent(Query::class.java)
            }.toMap().map {
                it.key.getAnnotation(Query::class.java).param to args[it.value]
            }.toMap()


            // 获取方法上的注解， 筛选出 自定义的注解。
            methodMap[method.name]?.declaredAnnotations?.forEachIndexed { index, it ->
                when (it) {
                    is GET -> {

                        val url: String
                        if (it.url.startsWith("http") || it.url.startsWith("https")) {
                            url = fillParam(it.url, paramPathAnotationMap, queryAnotationMap)

                        } else {
                            // 获取 baseUrl :
                            //  1、获取当前 method 所在的接口 上的 Api 注解
                            //  2、以及上层(enclosingClass)接口 上的 Api 注解
                            //  聚合 注解上的url.

                            // takeWhile: 参数是元素，即遍历元素
                            // takeIf : 参数是集合。
                            val clses = lambdaEnclosingClses(T::class.java)?.takeWhile { it!!.isInterface }.toList()

                            // 聚合 注解上的url
                            val partsUrl = clses.foldRight(StringBuilder()) { acc, sb ->
                                val partUrl = acc?.getDeclaredAnnotation(Api::class.java)?.url
                                sb.append(partUrl).append("/")
                            }.toString()

                            val totalUrl = fillParam(partsUrl, paramPathAnotationMap, queryAnotationMap)
                            url = totalUrl

                        }

                        println(url)
                    }
                }


            }


        } as T
    }


    /**
     * 为url 填充参数
     * @param pathAnnotation @Path 注解信息
     * @param  queryAnnotation @Query注解信息
     *
     */
    fun fillParam(partsUrl: String,
                  pathAnnotation: Map<String, Any>,
                  queryAnnotation: Map<String, Any>
    ): String {

        var temp = ""
        if (!partsUrl.endsWith('/')) {
            temp = partsUrl + "/"
        }

        if (pathAnnotation.size > 0) {

            pathAnnotation.forEach { key, value ->
                if (key.trim().length > 0 && temp.contains("{${key}}")) {
                    temp = temp.replace("{${key}}", "${value}")
                } else {
                    println("----@Path:${key}替换失败-------")
                }
            }

        }


        if (queryAnnotation.size > 0) {
            val urlParam = StringBuilder("?")
            queryAnnotation.forEach { key, value ->
                urlParam.append(key).append("=").append(value).append("&")
            }
            urlParam.deleteCharAt(urlParam.length - 1)
            temp += urlParam
        }

        return temp
    }

}