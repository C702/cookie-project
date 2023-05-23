package com.cy.cookietwo.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cy.cookietwo.HomeActivity
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentLoginBinding
import com.cy.cookietwo.login.rememberme.UserSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    val auth = Firebase.auth
    private lateinit var sharedPreferences: UserSettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = UserSettings()
        context?.let { sharedPreferences.instancePref(it) }
        clickListener()
    }

    private fun login(){
        val intent = Intent(activity, HomeActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
        //Navigation.findNavController(binding.root).navigate(R.id.action_login_to_home2)
    }

    private fun checkrememberme() {
        if (binding.rememberMe.isChecked) {
            sharedPreferences.setNumber(2)
            sharedPreferences.setEmail(binding.emailText.text.toString())
            sharedPreferences.setPassword(binding.passwordText.text.toString())
        }
    }

    private fun checkView(){
        auth.signInWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                checkrememberme()
                login()
                Toast.makeText(context, getString(R.string.wlcbck), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.inncrt),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clickListener() {
        binding.loginButton.setOnClickListener {
            checkView()
        }
    }
}