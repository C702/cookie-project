package com.cy.cookietwo.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentHome2Binding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Home : Fragment() {

    val db = FirebaseDatabase.getInstance()
    private lateinit var binding: FragmentHome2Binding
    val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHome2Binding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLeaderboard()
        click()
        updateUi()
        binding.cookieButton.setImageResource(R.drawable.cookie4k3)
        binding.profileButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_home2_to_profile_page)
        }
    }

    private fun updateUi() {
        auth.currentUser?.let { user->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get().addOnSuccessListener { cookie->
                db.reference.child("leaderboard").child(user.uid).child("bestscore").get().addOnSuccessListener { best->
                    binding.cookieText.text = getString(R.string.cookietext)+ cookie.value.toString()
                    binding.highscoreText.text = getString(R.string.highscoretext)+ best.value.toString()
                    Log.d(TAG, "updateUi: " + getString(R.string.highscoretext)+ best.value.toString())
                }

            }
        }
    }

    private fun getLeaderboard() {
        val list = arrayListOf<RecyclerModel>()
        val adapter = Cookierecyclerview(object : UserClickedListener{
            override fun isuserclicked(id: String?) {
                val action = HomeDirections.actionHome2ToOtherProfilePage(userId = id)
                Navigation.findNavController(binding.root).navigate(action)
            }

        })
        binding.leaderboardRecycleView.layoutManager = LinearLayoutManager(context)
        binding.leaderboardRecycleView.adapter = adapter
        db.reference.child("leaderboard").get().addOnSuccessListener {
            for (child in it.children) {
                Log.d(TAG, "getLeaderboard: " + child.child("name").value.toString())
                Log.d(TAG, "getLeaderboard: " + child.child("highscore").value.toString().toInt())
                Log.d(TAG, "getLeaderboard: " + child.child("bestscore").value.toString().toInt())
                list.add(
                    RecyclerModel(
                        id = child.key,
                        name = child.child("name").value.toString(),
                        count = child.child("highscore").value.toString().toInt(),
                        high = child.child("bestscore").value.toString().toInt()
                    )
                )
            }
            list.sortByDescending {
                it.high
            }
            adapter.submitList(list)
            Log.d(TAG, "getLeaderboard: " + list)
        }
    }

    private fun check(cookie: Int, highScore: Int, user: FirebaseUser, cookie1: DataSnapshot) {
        var newHighScore = highScore
        var newCookie = cookie+1
        if (newCookie >= highScore) {
           newHighScore = newCookie
        }
        db.reference.child("leaderboard").child(user.uid).child("highscore").setValue(newCookie.toString().toInt())
        db.reference.child("leaderboard").child(user.uid).child("bestscore").setValue(newHighScore.toString().toInt())
        getLeaderboard()
        updateUi()
    }

    private fun click() {
        binding.cookieButton.setOnClickListener {
            add()
            Toast.makeText(context, "YUM!", Toast.LENGTH_SHORT).show()
        }
        binding.shopButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_home2_to_shop)
        }
    }

    private fun add() {
        auth.currentUser?.let { user->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get().addOnSuccessListener { cookie->
                db.reference.child("leaderboard").child(user.uid).child("bestscore").get().addOnSuccessListener { best->
                    check(cookie.value.toString().toInt(), best.value.toString().toInt(), user, cookie)

                }

            }
        }
    }
}