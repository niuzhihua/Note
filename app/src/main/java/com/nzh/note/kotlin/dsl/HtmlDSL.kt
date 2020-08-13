package com.nzh.note.kotlin.dsl


/**
 *  这里的receiver 是Node，用来将 html 节点对象 传给 html{} 里面的函数。
 *  在 head 函数中 使用this ,就可以获得 html对象了。
 *
 *  Node. 作用域范围：
 *      如果{} 内的函数A (b:receiverA:()-Unit) 有receiver , 那么在{} 的函数A中 使用this 就表示 receiverA对象
 *      如果{} 内的函数A (b：()-Unit) 没有receiver , 那么在{} 的函数A中 使用this 就表示 block 函数类型的receiver 所在类型的对象。
 *          这里block 的receiver 类型是Node.  那么就是 html对象。
 *
 *  总结： 这个 receiver (Node.) 是用来 给 block 的实现(函数体) 传递 Receiver类型的对象的。
 *          在block 的实现(函数体) 用this 获取。
 */
fun html(block: Element.() -> Unit): Node {  // 给 block函数体 传递 html 对象。
    // 创建 html 节点
    val html = Element("html")
    // 调用函数执行，传递 receiver为 html对象。
    html.block()

    return html
}

/**
 *  这里的receiver 指 Node.head(...)
 *  如果 给head函数指定了 receiver ， 并且receiver类型 和 head函数所在函数的receiver类型一样。
 *  那么就可以在 head函数中使用this 来接收 所在函数传递的对象了。
 *
 *  总结： 这里的 receiver 用来接收 本函数所在函数的 receiver对象。用this 接收。
 *
 */
fun Element.head(block: Element.() -> Unit) {  // block给函数体 传递 head对象。

    // 创建 本节点 ：head节点
    val head = Element("head")

    //由于 head函数的receiver 和 所在函数 的receiver 类型一致。所以用this 接收。
    // 上层节点 添加 本节点
    this.children += head  // 等同与 this.children.add(head)

    // 向下层传递 本节点对象
    head.block()
}

fun Element.body(block: Element.() -> Unit) {

    // 创建 本节点
    val body = Element("body")

    // 拿到上层节点 ，并添加本节点
    this.children += body

    // 向下层传递本节点
    body.block()

}

fun main() {

    // DSL 编写规律：
    // 最外层的节点 来返回结果。
    // 内层的节点 有两个receiver: 一个用来接收 所在函数传递的对象 ，一个用来向 内层 传递对象(通常是本节点对象)。 (根据业务需要)
    //

    val s = html {

        // head 函数 有两个receiver: 一个用来接收 上面传递的对象 ，一个用来 向下 传递对象
        head {

            // 由于invoke函数 就是 () 已经有 receiver了。如何拿到head节点对象呢？
            // 将运算符重载函数 写在 head 节点的类中。 这样就可以通过

            "meta"{
                // ()运算符重载

                //println("这里使用 this 表示 String对象:${this}")

                "charset"("utf-8") // ()运算符重载

                "style"("""width:200px;
                         height:200px;
                         background-color:#ff4657
                """.trimMargin()) // ()运算符重载
            }
        }

        body {

            "span"{

                +"dsl test "
            }
        }

    }.getContent()
    println(s)



}