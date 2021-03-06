package com.geen.jetpacklearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geen.jetpacklearning.ui.main.MainFragment
import com.geen.jetpacklearning.ui.main.MainViewModel

class LearnViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNowAllowingStateLoss()
        }
    }
}