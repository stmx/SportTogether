package com.android.stmx.myapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.stmx.myapp.R
import com.android.stmx.myapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    companion object {
        fun newInstance(): SignUpFragment {
            val args = Bundle()
            val fragment = SignUpFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.bind(inflater.inflate(R.layout.fragment_sign_up, container, false))
        return binding.root
    }

}