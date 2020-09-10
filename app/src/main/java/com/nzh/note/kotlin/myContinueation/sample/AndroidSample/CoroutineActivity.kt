package com.nzh.note.kotlin.myContinueation.sample.AndroidSample

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.nzh.note.R
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        val autoDisposeButton = findViewById<Button>(R.id.btnClickAutoDispose)

//        autoDisposeButton.setOnClickListenerAutoDispose {

        // https://github.com/enbandari/kotlin-coroutines-android/blob/master/app/src/main/java/com/bennyhuo/kotlin/coroutines/android/sample/MainActivity.kt


//        }
        val tempView = Button(this)
        tempView.text = "temp btn"
        val parent = autoDisposeButton.parent as LinearLayout
        // 添加 view
        parent.addView(tempView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        tempView.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main) {
                Log.d("-->", "request")
                // 移除view , 同时取消了协程。
                parent.removeView(tempView)
                delay(2500)
                Log.d("-->", "response")

            }.asAutoDisposeJob2(it)
        }


    }

    fun showDialog(view: View) {

        GlobalScope.launch(Dispatchers.Main) {

            val result = alert(this@CoroutineActivity)

            Toast.makeText(this@CoroutineActivity, result, Toast.LENGTH_SHORT).show()

        }

    }

    /**
     * suspendCancellableCoroutine :
     * 如果协程取消了，那么对话框也取消。
     */
    suspend fun alert(context: Context): String = suspendCancellableCoroutine { continuation ->

        AlertDialog.Builder(context)
                .setNegativeButton("取消") { dialogInterface, p2 ->

                    dialogInterface.dismiss()
                    continuation.resume("点击了取消")
                }
                .setPositiveButton("确定") { dialogInterface, p2 ->

                    dialogInterface.dismiss()
                    continuation.resume("点击了确定")
                }
                .setTitle("title")
                .setMessage("message")

                // 需要注意的是： Cancel ，Negative，Positive为互斥操作。所以设置 setOnCancelListener
                // 如果设置 setOnDismissListener , 那么协程就会重复执行恢复操作( resume ),
                // 抛如下异常:
//                  java.lang.IllegalStateException: Already resumed, but proposed with update dismiss
//                          at kotlinx.coroutines.CancellableContinuationImpl.alreadyResumedError
//                .setOnDismissListener {
//                    println("--setOnDismissListener--")
//                    continuation.resume("dismiss")
//                }
                .setOnCancelListener {
                    println("--setOnCancelListener--")
                    continuation.resume("Cancel")
                }
                .create()
                .also { dialog ->
                    // 为此协程注册一个 Cancellation，当协程取消时会执行
                    continuation.invokeOnCancellation {
                        println("--invokeOnCancellation--")
                        dialog.dismiss()
                    }
                }
                .show()

    }

    fun getTaskResult(view: View) {
        val btn: Button = view as Button
        // 启动协程运行在主线程
        val mainScope = MainScope()

        mainScope.launch {

            // 启动协程运行在 IO 线程，并将结果带回 主线程。
            btn.text = async(Dispatchers.IO) {
                delay(2000)
                "{abc}"
            }.await()

            cancel()
            Toast.makeText(this@CoroutineActivity,"isActive:${mainScope.isActive}",Toast.LENGTH_SHORT).show()
        }


    }

    fun autoCancel(view: View) {
        startActivity(Intent(this, CancelCoroutineActivity::class.java))
    }


    // 定义 扩展函数
    fun View.setOnClickListenerAutoDispose(
            context: CoroutineContext = Dispatchers.Main,
            handler: suspend () -> Unit) {

        // 设置view的点击事件
        setOnClickListener { view ->

            GlobalScope.launch(context) {

                handler()

            }.asAutoDisposeJob2(view)
        }
    }

    // 简写方式
    fun Job.asAutoDisposeJob(view: View) = AutoDisposeJob(view, this)

    // 正常写好理解
    // 将 Job 转为 AutoDisposeJob
    fun Job.asAutoDisposeJob2(view: View): AutoDisposeJob {
        return AutoDisposeJob(view, this)
    }


}
