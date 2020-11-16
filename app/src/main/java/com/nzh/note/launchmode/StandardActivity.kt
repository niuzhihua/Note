package com.nzh.note.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nzh.note.R

class StandardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("StandardActivity is destroyed")
    }

    fun next(view: View) {
        startActivity(Intent(this, StandardActivity2::class.java))
    }

    fun toSingleTask(view: View) {
        val i = Intent(this, SingleTaskActivity::class.java)
        startActivity(i)
    }

    fun toSingleInstance(view: View) {
        val i = Intent(this, SingleIntanceActivity::class.java)
        startActivity(i)
    }
}