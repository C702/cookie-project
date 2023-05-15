package com.cy.cookietwo.profile.myprofile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentProfilePageBinding
import com.cy.cookietwo.profile.otherprofile.ShopModel
import com.cy.cookietwo.profile.otherprofile.ShopRecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfilePage : Fragment() {

    val db = FirebaseDatabase.getInstance()
    private lateinit var adapter: ShopRecyclerView
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
        initrecyclerview()
        getShopData()
    }

    private fun setIcon( moon: Boolean, star: Boolean) {
        if (moon) {
            binding.icon.isVisible = true
            binding.icon.setImageResource(R.drawable.ic_moon)
        }
        if (star) {
            binding.icon.isVisible = true
            binding.icon.setImageResource(R.drawable.ic_star)
        }
    }

    private fun initrecyclerview() {
        adapter = ShopRecyclerView(context)
        binding.shopRw.layoutManager = LinearLayoutManager(context)
        binding.shopRw.adapter = adapter
    }

    @SuppressLint("ResourceAsColor")
    private fun setUserName(
        userName: String,
        green: Boolean,
        yellow: Boolean,
        red: Boolean,
        pink: Boolean
    ) {
        if (green) {
            binding.userName.setTextColor(Color.parseColor("#2DD2A2"))
        }
        else if (yellow) {
            binding.userName.setTextColor(Color.parseColor("#ecdc13"))
        }
        else if (red) {
            binding.userName.setTextColor(Color.parseColor("#D22D5D"))
        }
        else if (pink) {
            binding.userName.setTextColor(Color.parseColor("#FFC0CB"))
        }
        binding.userName.text = userName
    }

    private fun getShopData() {
        val list = arrayListOf<ShopModel>()
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get()
                .addOnSuccessListener { cookie ->
                    db.reference.child("leaderboard").child(user.uid).child("name").get()
                        .addOnSuccessListener { name ->
                            db.reference.child("leaderboard").child(user.uid)
                                .child("bestscore")
                                .get().addOnSuccessListener { best ->
                                    db.reference.child("leaderboard").child(user.uid)
                                        .child("shop").child("vip")
                                        .get().addOnSuccessListener { vip ->
                                            db.reference.child("leaderboard")
                                                .child(user.uid)
                                                .child("shop").child("moon")
                                                .get().addOnSuccessListener { moon ->
                                                    db.reference.child("leaderboard")
                                                        .child(user.uid)
                                                        .child("shop").child("pink")
                                                        .get().addOnSuccessListener { pink ->
                                                            db.reference.child("leaderboard")
                                                                .child(user.uid)
                                                                .child("shop").child("star")
                                                                .get().addOnSuccessListener { star ->
                                                                    db.reference.child("leaderboard")
                                                                        .child(user.uid)
                                                                        .child("shop").child("red")
                                                                        .get()
                                                                        .addOnSuccessListener { red ->
                                                                            db.reference.child("leaderboard")
                                                                                .child(user.uid)
                                                                                .child("shop")
                                                                                .child("yellow")
                                                                                .get()
                                                                                .addOnSuccessListener { yellow ->
                                                                                    db.reference.child("leaderboard")
                                                                                        .child(user.uid)
                                                                                        .child("shop")
                                                                                        .child("rebirth")
                                                                                        .get()
                                                                                        .addOnSuccessListener { rebirth ->

                                                                                            db.reference.child(
                                                                                                "leaderboard"
                                                                                            )
                                                                                                .child(
                                                                                                    user.uid
                                                                                                )
                                                                                                .child("shop")
                                                                                                .child("green")
                                                                                                .get()
                                                                                                .addOnSuccessListener { green ->
                                                                                                    db.reference.child(
                                                                                                        "leaderboard"
                                                                                                    )
                                                                                                        .child(
                                                                                                            user.uid
                                                                                                        )
                                                                                                        .child(
                                                                                                            "shop"
                                                                                                        )
                                                                                                        .child(
                                                                                                            "adder"
                                                                                                        )
                                                                                                        .get()
                                                                                                        .addOnSuccessListener { adder ->
                                                                                                            list.add(
                                                                                                                ShopModel(
                                                                                                                    item = getString(
                                                                                                                        R.string.naming
                                                                                                                    ) + name.value.toString()
                                                                                                                )
                                                                                                            )
                                                                                                            list.add(
                                                                                                                ShopModel(
                                                                                                                    item = getString(
                                                                                                                        R.string.Cookkkies
                                                                                                                    ) + cookie.value.toString()
                                                                                                                )
                                                                                                            )
                                                                                                            list.add(
                                                                                                                ShopModel(
                                                                                                                    item = getString(
                                                                                                                        R.string.Highestt
                                                                                                                    ) + best.value.toString()
                                                                                                                )
                                                                                                            )
                                                                                                            list.add(
                                                                                                                ShopModel(
                                                                                                                    item = getString(
                                                                                                                        R.string.RebirthProfile
                                                                                                                    ) + rebirth.value.toString()
                                                                                                                )
                                                                                                            )
                                                                                                            list.add(
                                                                                                                ShopModel(
                                                                                                                    item = getString(
                                                                                                                        R.string.AdderProfile
                                                                                                                    ) + adder.value.toString()
                                                                                                                )
                                                                                                            )
                                                                                                            if (vip.value.toString()
                                                                                                                    .toInt() != 1
                                                                                                            ) {
                                                                                                                list.add(
                                                                                                                    ShopModel(
                                                                                                                        item = getString(
                                                                                                                            R.string.VIPYES
                                                                                                                        )
                                                                                                                    )
                                                                                                                )
                                                                                                            } else {
                                                                                                                list.add(
                                                                                                                    ShopModel(
                                                                                                                        item = getString(
                                                                                                                            R.string.VIPNO
                                                                                                                        )
                                                                                                                    )
                                                                                                                )
                                                                                                                Log.d(
                                                                                                                    "TAG",
                                                                                                                    "getShopData: " + pink.value.toString()
                                                                                                                        .toInt()
                                                                                                                )
                                                                                                                if (pink.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.PINKYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.PINKNO
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                if (green.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.GREENYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.GREENNO
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                if (red.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.REDYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.REDYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                if (moon.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.MOONYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.MOONNO
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                if (yellow.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.YELLOWYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.YELLOWNO
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                if (star.value.toString()
                                                                                                                        .toInt() > 0
                                                                                                                ) {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.STARYES
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                } else {
                                                                                                                    list.add(
                                                                                                                        ShopModel(
                                                                                                                            item = getString(
                                                                                                                                R.string.STARNO
                                                                                                                            )
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                                binding.titleProfilePage.text =
                                                                                                                    getString(
                                                                                                                        R.string.namingone
                                                                                                                    )
                                                                                                                setUserName(
                                                                                                                    name.value.toString(),
                                                                                                                    (green.value.toString()
                                                                                                                        .toInt() > 0),
                                                                                                                    (yellow.value.toString()
                                                                                                                        .toInt() > 0),
                                                                                                                    (red.value.toString()
                                                                                                                        .toInt() > 0),
                                                                                                                    (pink.value.toString()
                                                                                                                        .toInt() > 0)
                                                                                                                )
                                                                                                                setIcon((moon.value.toString().toInt() > 0), (star.value.toString().toInt() > 0))
                                                                                                                adapter.submitList(
                                                                                                                    list
                                                                                                                )
                                                                                                            }

                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                }
                                                                        }
                                                                }

                                                        }
                                                }
                                        }
                                }
                        }
                }
            }
        }

    //db.reference.child("leaderboard").child(user.uid).child("highscore").get()
    //.addOnSuccessListener { cookie ->

    /*private fun getInfo() {
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get()
                .addOnSuccessListener { cookie ->
                    db.reference.child("leaderboard").child(user.uid).child("name").get()
                        .addOnSuccessListener { name ->
                            db.reference.child("leaderboard").child(user.uid).child("bestscore")
                                .get().addOnSuccessListener { best ->
                                /*binding.textView9.text =
                                    getString(R.string.naming) + name.value.toString()
                                binding.textView10.text =
                                    getString(R.string.Cookkkies) + cookie.value.toString()
                                binding.textView11.text =
                                    getString(R.string.Highestt) + cookie.value.toString()*/
                            }

                        }
                }
        }

    }*/
}