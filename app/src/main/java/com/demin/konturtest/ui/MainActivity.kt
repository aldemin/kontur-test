package com.demin.konturtest.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.demin.konturtest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMainFragment()
        }
    }

    private fun openMainFragment() {
        Log.d(TAG, "openMainFragment: ")
        supportFragmentManager.beginTransaction()
            .add(R.id.ac_main_container, MainFragment.newInstance())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "onOptionsItemSelected: pop back stack")
                supportFragmentManager.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val TAG = MainActivity::class.simpleName

    }

}