package com.cy.cookietwo.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cy.cookietwo.HomeActivity
import com.cy.cookietwo.NewUserActivity
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentSplashScreenBinding
import com.cy.cookietwo.login.rememberme.UserSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    val auth = Firebase.auth
    private lateinit var sharedPreferences: UserSettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = UserSettings()
        context?.let { sharedPreferences.instancePref(it) }
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                println(millisUntilFinished)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                checking()
            }
        }.start()
    }

    private fun checking() {
        Log.d("TAG", "checking: "+sharedPreferences.getPassword()+sharedPreferences.getEmail())
        if (!sharedPreferences.getEmail().isNullOrBlank() && !sharedPreferences.getPassword().isNullOrBlank()) {
            auth.signInWithEmailAndPassword(sharedPreferences.getEmail().toString(), sharedPreferences.getPassword().toString()).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(activity, HomeActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                    //Navigation.findNavController(binding.root).navigate(R.id.action_splashScreen_to_home2)
                }
                else {
                    Toast.makeText(context, getString(R.string.splashscreenunable), Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, NewUserActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                }
            }
        }
        else {
            val intent = Intent(activity, NewUserActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }
}