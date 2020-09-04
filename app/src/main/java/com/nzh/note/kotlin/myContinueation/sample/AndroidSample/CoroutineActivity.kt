package com.nzh.note.kotlin.myContinueation.sample.AndroidSample

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nzh.note.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
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


}
