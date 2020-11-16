package com.nzh.note.launchmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nzh.note.R

class SingleIntanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_intance)
    }
}