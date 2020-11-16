package com.nzh.note.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nzh.note.R

class StandardActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard2)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("StandardActivity2 onNewIntent")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("StandardActivity2 is destroyed")
    }

    fun next(view: View) {
        val intent = Intent(this, SingleTaskActivity::class.java)
        startActivity(intent)
    }
}