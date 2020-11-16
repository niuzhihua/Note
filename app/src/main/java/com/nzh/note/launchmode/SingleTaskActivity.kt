package com.nzh.note.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nzh.note.R

class SingleTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_task)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("SingleTaskActivity onNewIntent")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("SingleTaskActivity is destoryed")
    }

    fun next(view: View) {
        val i = Intent(this, StandardActivity2::class.java)
        startActivity(i)
    }
}