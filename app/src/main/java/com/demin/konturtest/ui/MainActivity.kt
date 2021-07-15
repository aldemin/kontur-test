package com.demin.konturtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demin.konturtest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openMainFragment()
    }

    private fun openMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.ac_main_container, MainFragment.newInstance())
            .addToBackStack(MainFragment::class.simpleName).commit()
    }
}