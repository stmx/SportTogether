package com.android.stmx.myapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.stmx.myapp.databinding.ActivityMainBinding
import com.android.stmx.myapp.ui.fragments.ActionListFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    companion object{
        fun getIntent(context: Context):Intent {
            return Intent(context,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSignOut.setOnClickListener(this)
        var fragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        if (fragment == null) {
            fragment = ActionListFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(binding.fragmentContainer.id, fragment)
                .commit()
        }
    }

    override fun onClick(v: View?) {
        Firebase.auth.signOut()
        startActivity(AuthActivity.getIntent(this))
    }

}