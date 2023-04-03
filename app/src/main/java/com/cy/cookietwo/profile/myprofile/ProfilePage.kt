package com.cy.cookietwo.profile.myprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentProfilePageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfilePage : Fragment() {

    val db = FirebaseDatabase.getInstance()
    private lateinit var binding: FragmentProfilePageBinding
    val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfo()
        binding.button3.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profile_page_to_home2)
        }
    }

    private fun getInfo() {
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get()
                .addOnSuccessListener { cookie ->
                    db.reference.child("leaderboard").child(user.uid).child("name").get()
                        .addOnSuccessListener { name ->
                            db.reference.child("leaderboard").child(user.uid).child("bestscore")
                                .get().addOnSuccessListener { best ->
                                binding.textView9.text =
                                    getString(R.string.naming) + name.value.toString()
                                binding.textView10.text =
                                    getString(R.string.Cookkkies) + cookie.value.toString()
                                binding.textView11.text =
                                    getString(R.string.Highestt) + cookie.value.toString()
                            }

                        }
                }
        }

    }
}