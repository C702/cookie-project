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

    private lateinit var adapter: OtherProfileRecyclerView
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
        getProfileOthers()
    }

    private fun shopData(otherList: ArrayList<OtherProfileModel>) {
        val list = arrayListOf<OtherProfileModel>()
        otherList.forEach {
            list.add(it)
        }
        db.reference.child("leaderboard").child(args.userId.toString()).child("shop").get()
            .addOnSuccessListener { shop ->
                    val green = shop.child("green")
                    val red = shop.child("red")
                    val yellow = shop.child("yellow")
                    val pink = shop.child("pink")
                    val adder = shop.child("adder")
                    val vip = shop.child("vip")
                    val chosenItem = shop.child("chosen_item")
                    val rebirth = shop.child("rebirth")
                    val moon = shop.child("moon")
                    val star = shop.child("star")

                    setIcon(chosenItem.value.toString())

                    if (yellow.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.YELLOWYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.YELLOWNO
                                )
                            )
                        )
                    }

                    if (green.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.GREENYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.GREENNO
                                )
                            )
                        )
                    }

                    if (pink.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.PINKYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.PINKNO
                                )
                            )
                        )
                    }

                    if (red.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.REDYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.REDNO
                                )
                            )
                        )
                    }

                    if (vip.value.toString()
                            .toInt() != 1
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.VIPYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.VIPNO
                                )
                            )
                        )
                    }

                    list.add(
                        OtherProfileModel(
                            item = getString(
                                R.string.AdderProfile
                            ) + adder.value.toString()
                        )
                    )
                    OtherProfileModel(
                        item = getString(
                            R.string.RebirthProfile
                        ) + rebirth.value.toString(),
                    )
                    if (moon.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.MOONYES
                                ),
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.MOONNO
                                )
                            )
                        )
                    }
                    if (star.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.STARYES
                                )
                            )
                        )
                    } else {
                        list.add(
                            OtherProfileModel(
                                item = getString(
                                    R.string.STARNO
                                )
                            )
                        )
                    }
                    adapter.submitList(list)
                }
        }

    private fun getProfileOthers() {
        val list = arrayListOf<OtherProfileModel>()
        db.reference.child("leaderboard").child(args.userId.toString()).get()
            .addOnSuccessListener { others ->
                val score = others.child("highscore")
                val highscore = others.child("bestscore")
                val name = others.child("name")
                val chosenColour = others.child("shop").child("chosen_colour")
                setUserName(name.value.toString(),chosenColour.value.toString())
                list.add(
                    OtherProfileModel(
                        item = getString(
                            R.string.naming
                        ) + name.value.toString()
                    )
                )
                list.add(
                    OtherProfileModel(
                        item = getString(
                            R.string.Cookkkies
                        ) + score.value.toString()
                    )
                )
                list.add(
                    OtherProfileModel(
                        item = getString(
                            R.string.Highestt
                        ) + highscore.value.toString()
                    )
                )
                shopData(list)
            }
        }
    

    private fun setIcon(chosenIcon: String) {
        if (chosenIcon == "moon") {
            binding.icon.isVisible = true
            binding.icon.setImageResource(R.drawable.ic_moon)
        }
        if (chosenIcon == "star") {
            binding.icon.isVisible = true
            binding.icon.setImageResource(R.drawable.ic_star)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setUserName(
        userName: String,
        chosen: String
    ) {
        if (chosen == "green") {
            binding.userName.setTextColor(Color.parseColor("#2DD2A2"))
            binding.titleOtherProfilePage.setTextColor(Color.parseColor("#2DD2A2"))
        } else if (chosen == "yellow") {
            binding.userName.setTextColor(Color.parseColor("#ecdc13"))
            binding.titleOtherProfilePage.setTextColor(Color.parseColor("#ecdc13"))
        } else if (chosen == "red") {
            binding.userName.setTextColor(Color.parseColor("#D22D5D"))
            binding.titleOtherProfilePage.setTextColor(Color.parseColor("#D22D5D"))
        } else if (chosen == "pink") {
            binding.userName.setTextColor(Color.parseColor("#FFC0CB"))
            binding.titleOtherProfilePage.setTextColor(Color.parseColor("#FFC0CB"))
        }
        binding.userName.text = userName
        binding.titleOtherProfilePage.text = getString(R.string.namingone)
        binding.backButtonOtherProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_otherProfilePage_to_home2)
        }
    }

    private fun initrecyclerview() {
        adapter = OtherProfileRecyclerView(context, false)
        binding.otherProfilePageRW.layoutManager = LinearLayoutManager(context)
        binding.otherProfilePageRW.adapter = adapter
    }
}