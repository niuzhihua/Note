package com.nzh.note.kotlin.annotation

/**
 * kotlin的注解：
 *   可以对类，函数，函数参数，属性做标注
 *   注解可用于 源码级，编译器，运行时
 */
// 注解的定义方式
@Target(AnnotationTarget.CLASS)         // 用于 类
@Retention(AnnotationRetention.RUNTIME) // 运行时注解
annotation class Api(val url: String)   // 注解参数类型 String

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val url: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(val param: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val param: String = "")

@Api("https://api.github.com")
interface GithubApi {

    @Api("users")
    interface Users {

        @GET("https://api.github.com/users/{myName}")
        fun getUserName5(@Query("age") age: Int, @Path("myName") name: String)

        @GET("https://api.github.com/users/{myName}")
        fun getUserName5_(@Path("myName") name: String, @Query("age") age: Int)

        @GET("https://api.github.com/users/niuzhihua")
        fun getUserName4(@Query("username") name: String, @Query("age") age: Int, desc: String)

        @GET("https://api.github.com/users/{username}")
        fun getUserName1(@Path("username") name: String)

        @GET("https://api.github.com/users/{username}/{id}")
        fun getUserName2(@Path("username") name: String, @Path("id") id: Int)

        @GET("https://api.github.com/users/niuzhihua")
        fun getUserName3()

        @GET("https://api.github.com/users/niuzhihua?username=haha&age=11")
        fun getUserName3_()


    }
}


