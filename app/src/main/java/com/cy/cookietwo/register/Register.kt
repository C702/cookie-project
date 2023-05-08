package com.cy.cookietwo.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Register : Fragment() {

    val auth = Firebase.auth
    private lateinit var binding: FragmentRegisterBinding
    val db = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun register(){
        auth.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                it.result.user?.uid
                db.reference.child("users").child(it.result.user?.uid.toString()).setValue(UserDetail(name = binding.userNameText.text.toString(), email = binding.emailText.text.toString()))
                db.reference.child("leaderboard").child(it.result.user?.uid.toString()).setValue(UserCookieDetail(name = binding.userNameText.text.toString(), bestscore = 0, highscore = 0, shop = Shop()))
                Navigation.findNavController(binding.root).navigate(R.id.action_register_to_home2)
                Toast.makeText(context, getString(R.string.wlc), Toast.LENGTH_SHORT).show()
                println("Success!!")
            }
        }
    }

    private fun checkView(){
        if (binding.emailText.text.startsWith("@")) {
            Toast.makeText(context, getString(R.string.email), Toast.LENGTH_SHORT).show()
        }
        else if (binding.emailText.text.endsWith("@")){
            Toast.makeText(context, getString(R.string.email), Toast.LENGTH_SHORT).show()
        }
        else if (!binding.emailText.text.contains("@")){
            Toast.makeText(context, getString(R.string.email), Toast.LENGTH_SHORT).show()
        }
        else if (!binding.emailText.text.contains(".com")){
            Toast.makeText(context, getString(R.string.email), Toast.LENGTH_SHORT).show()
        }
        else if (binding.passwordText.text.length < 6) {
            Toast.makeText(context, getString(R.string.password), Toast.LENGTH_SHORT).show()
        }
        else {
            register()
        }
    }

    private fun clickListener() {
        binding.registerButton.setOnClickListener {
            checkView()
        }
        binding.loginChange.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_register_to_login)
        }
    }
}