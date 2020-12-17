package com.arimyeon.testmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map_btn.setOnClickListener {
            val mapIntent = Intent(this, NaverMapActivity::class.java)
            startActivity(mapIntent)
        }
        map_view_btn.setOnClickListener {
            val mapViewIntent = Intent(this, NaverMapViewActivity::class.java)
            startActivity(mapViewIntent)
        }
    }
}
