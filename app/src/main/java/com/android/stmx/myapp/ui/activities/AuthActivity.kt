package com.android.stmx.myapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.stmx.myapp.databinding.ActivityAuthBinding
import com.android.stmx.myapp.ui.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityAuthBinding

    companion object{
        fun getIntent(context: Context): Intent {
            return Intent(context,AuthActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            supportFragmentManager.beginTransaction().add(binding.fragmentContainerAuth.id,SignInFragment.newInstance()).commit()
        } else {
            startActivity(MainActivity.getIntent(this))
            Toast.makeText(applicationContext, "User signed in", Toast.LENGTH_LONG).show()
        }
    }

}