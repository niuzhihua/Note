package com.nzh.note.kotlin.dsl

// DSL: 领域特定语言。 dsl 的实现就是高阶函数的应用，传入函数，调用函数。

//先来用一个html 的dsl 例子来学习： html 都是由节点构成的，首先来定义节点。

interface Node {
    /**
     * node有几个子类型：
    Element,
    Text,
    Attribute,
    RootElement,
    Comment,
    Namespace等
    Element是可以有属性和子节点的node。
     */
    fun getContent(): String
}

class Text(val text: String) : Node {
    override fun getContent(): String {
        return text
    }
}

open class Element(val name: String) : Node {

    override fun getContent(): String {
        return """<${name} ${properties.map { "${it.key}='${it.value}'" }.joinToString(" ")}>
            ${children.joinToString("") { it.getContent() }} 
                  </${name}>
        """.trimMargin()
    }

    // 当前节点的 子节点
    // 这里泛型能是 Element 吗？ 不能，因为最后一层节点 是Text ，需要将 Text节点添加进来。
    val children = ArrayList<Node>()

    // 当前节点的 属性
    val properties = HashMap<String, Any>()


    override fun toString(): String {
        return "${name}"
    }

    /**
     * 由于定义在 Node类中，自动含有一个receiver 为Node类型。
     *
     *  所以有2个 receiver ： Node ,String .
     *
     *  Node对象获取： this@Node
     *  String对象获取： this
     */
    operator fun String.invoke(block: Element.() -> Unit) {  // 节点
        // 创建本节点
        val meta = Element(this)

        // 如何接收上层节点？由于定义在 Node类中，自动含有一个receiver 为Node类型。
        // 接收上层节点，并添加本节点
        this@Element.children += meta

        // 向下层节点传递本节点
        meta.block()

    }

    operator fun String.invoke(param: String) {  // 属性

        // 向所在节点添加 属性
        val key = this
        val value = param
        this@Element.properties.put(key, value)
        // 等同于
//        this@Node.properties[key] = value

    }

    // 最后一层
    operator fun String.unaryPlus() {

        // 创建本节点
        val text = Text(this)

        // 接收上层节点，并添加本节点
        this@Element.children += text

    }

}


/**
 * html{    // html 是一个函数 ，用Node 类型描述 成为一个节点
 *
 *      head{  // head 也是一个函数 ，用Node 类型描述 成为一个节点
 *
 *          "meta"{  // String类的 ()运算符重载 , 用Node 类型描述 成为一个节点
 *
 *              "charset"("utf-8")  // String类的 ()运算符重载 ： 用属性描述。 属于上层节点 (meta节点)的属性。
 *          }
 *
 *      }
 *
 *      body{  // 节点
 *
 *          "span"{ // 节点
 *
 *              "style"("""         // 属性
 *                  属性:值;
 *                  属性:值;
 *                  属性:值
 *              """)
 *
 *               +"我是小阿giao"   // String类的 ()运算符重载 ， 注意是 末节点。
 *          }
 *      }
 *
 *      //
 *      receiver1.body(block:reveiver2.()->Unit){
 *
 *      }
 *
 *
 *      0、每一个描述(html,head,body) 都 表示一个节点。这个节点用 相应的函数(html函数，body函数...)返回。
 *      1、需要理解的是 head函数、body函数 都是在 html函数体中的函数调用。
 *      2、所有的节点信息都是由最外层的 html函数 来统计 ，因此 head,body 节点需要 被添加到 html 节点。
 *          为了能做到这点， 需要在最在 head 、body 函数中 调用
 *
 *              htmlNode.children .add(headNode)
 *              htmlNode.children .add(bodyNode)
 *
 *          那么结合高阶函数 如何做到这点呢？ 高阶函数：函数最为参数或返回值。
 *
 *
 *  }
 *
 */

