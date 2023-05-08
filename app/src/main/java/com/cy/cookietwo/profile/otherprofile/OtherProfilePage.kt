package com.cy.cookietwo.profile.otherprofile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentOtherProfileBinding
import com.google.firebase.database.FirebaseDatabase

class OtherProfilePage : Fragment() {

    private lateinit var adapter: ShopRecyclerView
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
        //getInfo()
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

    private fun initrecyclerview() {
        adapter = ShopRecyclerView(context)
        binding.otherProfilePageRW.layoutManager = LinearLayoutManager(context)
        binding.otherProfilePageRW.adapter = adapter
    }

    private fun getShopData() {
        val list = arrayListOf<ShopModel>()
        db.reference.child("leaderboard").child(args.userId.toString()).child("highscore").get()
            .addOnSuccessListener { cookie ->
                db.reference.child("leaderboard").child(args.userId.toString()).child("name").get()
                    .addOnSuccessListener { name ->
                        db.reference.child("leaderboard").child(args.userId.toString())
                            .child("bestscore")
                            .get().addOnSuccessListener { best ->
                                db.reference.child("leaderboard").child(args.userId.toString())
                                    .child("shop").child("vip")
                                    .get().addOnSuccessListener { vip ->
                                        db.reference.child("leaderboard")
                                            .child(args.userId.toString())
                                            .child("shop").child("Moon")
                                            .get().addOnSuccessListener { moon ->
                                                db.reference.child("leaderboard")
                                                    .child(args.userId.toString())
                                                    .child("shop").child("Pink")
                                                    .get().addOnSuccessListener { pink ->
                                                        db.reference.child("leaderboard")
                                                            .child(args.userId.toString())
                                                            .child("shop").child("Star")
                                                            .get().addOnSuccessListener { star ->
                                                                db.reference.child("leaderboard")
                                                                    .child(args.userId.toString())
                                                                    .child("shop").child("Red")
                                                                    .get()
                                                                    .addOnSuccessListener { red ->
                                                                        db.reference.child("leaderboard")
                                                                            .child(args.userId.toString())
                                                                            .child("shop")
                                                                            .child("Yellow")
                                                                            .get()
                                                                            .addOnSuccessListener { yellow ->
                                                                                db.reference.child("leaderboard")
                                                                                    .child(args.userId.toString())
                                                                                    .child("shop")
                                                                                    .child("rebirth")
                                                                                    .get()
                                                                                    .addOnSuccessListener { rebirth ->

                                                                                        db.reference.child(
                                                                                            "leaderboard"
                                                                                        )
                                                                                            .child(
                                                                                                args.userId.toString()
                                                                                            )
                                                                                            .child("shop")
                                                                                            .child("Green")
                                                                                            .get()
                                                                                            .addOnSuccessListener { green ->
                                                                                                db.reference.child(
                                                                                                    "leaderboard"
                                                                                                )
                                                                                                    .child(
                                                                                                        args.userId.toString()
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
                                                                                                            binding.titleOtherProfilePage.text =
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
                                                                binding.backButtonOtherProfile.setOnClickListener {
                                                                    Navigation.findNavController(
                                                                        binding.root
                                                                    )
                                                                        .navigate(R.id.action_otherProfilePage_to_home2)
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