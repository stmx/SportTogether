package com.android.stmx.myapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment(),View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignInBinding

    companion object {
        fun newInstance(): SignInFragment {
            val args = Bundle()
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.bind(inflater.inflate(R.layout.fragment_sign_in,container,false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.buttonSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        createAccount(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
    }

    private fun createAccount(email:String,password:String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
            } else {
                Toast.makeText(context, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(context, "User signed in", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "User don't signed in", Toast.LENGTH_LONG).show()
        }
    }

}