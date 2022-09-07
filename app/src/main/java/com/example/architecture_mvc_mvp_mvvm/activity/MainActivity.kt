package com.example.architecture_mvc_mvp_mvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.architecture_mvc_mvp_mvvm.R
import com.example.architecture_mvc_mvp_mvvm.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}