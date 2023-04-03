package com.cy.cookietwo.profile.otherprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentOtherProfileBinding
import com.google.firebase.database.FirebaseDatabase

class OtherProfilePage : Fragment() {

    val args by navArgs<OtherProfilePageArgs>()
    val db = FirebaseDatabase.getInstance()
    private lateinit var binding: FragmentOtherProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfo()
    }

    private fun getInfo() {
            db.reference.child("leaderboard").child(args.userId.toString()).child("highscore").get()
                .addOnSuccessListener { cookie ->
                    db.reference.child("leaderboard").child(args.userId.toString()).child("name").get()
                        .addOnSuccessListener { name ->
                            db.reference.child("leaderboard").child(args.userId.toString()).child("bestscore")
                                .get().addOnSuccessListener { best ->
                                    binding.textView9.text =
                                        getString(R.string.naming) + name.value.toString()
                                    binding.textView10.text =
                                        getString(R.string.Cookkkies) + cookie.value.toString()
                                    binding.textView11.text =
                                        getString(R.string.Highestt) + cookie.value.toString()
                                    binding.textView17.text =
                                        getString(R.string.namingone) + " " + name.value.toString() + getString(R.string.twodot)
                                }

                        }
                }
        binding.button3.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_otherProfilePage_to_home2)
        }
        }

    }