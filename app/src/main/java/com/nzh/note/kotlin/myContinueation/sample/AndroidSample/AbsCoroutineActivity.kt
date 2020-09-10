package com.nzh.note.kotlin.myContinueation.sample.AndroidSample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nzh.note.R
import kotlinx.coroutines.*

abstract class AbsCoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onDestroy() {
        super.onDestroy()
        // 取消协程：随着协程的取消，协程内的业务（例如:发的请求） 也会被取消。
        cancel()
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
    }
}

class CancelCoroutineActivity : AbsCoroutineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cancel_coroutine)
    }

    public fun click(view: View) {
        val btn: Button = view as Button

        launch {

            btn.text = async {
                kotlinx.coroutines.delay(2000)
                "haha"
            }.await()

            Toast.makeText(this@CancelCoroutineActivity, "btn.text", Toast.LENGTH_SHORT).show()
        }


    }
}