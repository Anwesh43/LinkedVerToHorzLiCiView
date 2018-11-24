package com.anwesh.uiprojects.linkedverttohorzliciview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.verttohorzliciview.VertToHorzLiCiView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VertToHorzLiCiView.create(this)
    }
}
