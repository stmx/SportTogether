package com.android.stmx.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.stmx.myapp.ui.fragments.ActionListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = ActionListFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

}