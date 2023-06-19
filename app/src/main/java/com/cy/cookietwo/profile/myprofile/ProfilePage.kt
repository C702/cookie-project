package com.cy.cookietwo.profile.myprofile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentProfilePageBinding
import com.cy.cookietwo.profile.otherprofile.IsChoosenClick
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
        } else if (chosen == "yellow") {
            binding.userName.setTextColor(Color.parseColor("#ecdc13"))
        } else if (chosen == "red") {
            binding.userName.setTextColor(Color.parseColor("#D22D5D"))
        } else if (chosen == "pink") {
            binding.userName.setTextColor(Color.parseColor("#FFC0CB"))
        }
        binding.userName.text = userName
    }

    private fun initrecyclerview() {
        adapter = ShopRecyclerView(context, true, object : IsChoosenClick {
            override fun isChosen(param: ShopModel) {
                val keyword = param.item?.split("Bought?")?.get(0)?.trim()?.toLowerCase()
                if (keyword == "green" || keyword == "pink" || keyword == "yellow" || keyword == "red") {
                    changeChosenColour(keyword)
                } else if (keyword == "star" || keyword == "moon") {
                    changeChosenItem(keyword)
                }
            }

        })
        binding.shopRw.layoutManager = LinearLayoutManager(context)
        binding.shopRw.adapter = adapter
    }

    private fun changeChosenColour(keyword: String) {
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("shop").child("chosen_colour")
                .setValue(keyword).addOnSuccessListener {
                    initrecyclerview()
                    getShopData()
                }
        }
    }

    private fun changeChosenItem(keyword: String) {
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("shop").child("chosen_item")
                .setValue(keyword).addOnSuccessListener {
                    initrecyclerview()
                    getShopData()
                }
        }
    }

    private fun getColourShop(otherList: ArrayList<ShopModel>) {
        val list = arrayListOf<ShopModel>()
        otherList.forEach {
            list.add(it)
        }
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid)
                .child("shop").get().addOnSuccessListener { shop ->
                    val green = shop.child("green")
                    val red = shop.child("red")
                    val yellow = shop.child("yellow")
                    val pink = shop.child("pink")
                    val adder = shop.child("adder")
                    val vip = shop.child("vip")
                    val chosenColour = shop.child("chosen_colour")
                    val chosenItem = shop.child("chosen_item")
                    val rebirth = shop.child("rebirth")
                    val moon = shop.child("moon")
                    val star = shop.child("star")

                    setIcon(chosenItem.value.toString())

                    if (yellow.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.YELLOWYES
                                ),
                                isChosen = yellow.key.toString() == chosenColour.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.YELLOWNO
                                ),
                                isChosen = yellow.key.toString() == chosenColour.value.toString()
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
                                ),
                                isChosen = green.key.toString() == chosenColour.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.GREENNO
                                ),
                                isChosen = green.key.toString() == chosenColour.value.toString()
                            )
                        )
                    }

                    if (pink.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.PINKYES
                                ),
                                isChosen = pink.key.toString() == chosenColour.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.PINKNO
                                ),
                                isChosen = pink.key.toString() == chosenColour.value.toString()
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
                                ),
                                isChosen = red.key.toString() == chosenColour.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.REDNO
                                ),
                                isChosen = red.key.toString() == chosenColour.value.toString()
                            )
                        )
                    }

                    if (vip.value.toString()
                            .toInt() != 1
                    ) {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.VIPYES
                                ),
                                isChosen = vip.key.toString() == chosenColour.value.toString(),
                                isSwitchEnabled = false
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.VIPNO
                                ),
                                isChosen = vip.key.toString() == chosenColour.value.toString(),
                                isSwitchEnabled = false
                            )
                        )
                    }

                    list.add(
                        ShopModel(
                            item = getString(
                                R.string.AdderProfile
                            ) + adder.value.toString(),
                            isChosen = adder.key.toString() == chosenColour.value.toString(),
                            isSwitchEnabled = false
                        )
                    )
                    ShopModel(
                        item = getString(
                            R.string.RebirthProfile
                        ) + rebirth.value.toString(),
                        isChosen = rebirth.key.toString() == chosenColour.value.toString(),
                        isSwitchEnabled = false
                    )
                    if (moon.value.toString()
                            .toInt() > 0
                    ) {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.MOONYES
                                ),
                                isChosen = moon.key.toString() == chosenItem.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.MOONNO
                                ),
                                isChosen = moon.key.toString() == chosenItem.value.toString()
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
                                ),
                                isChosen = star.key.toString() == chosenItem.value.toString()
                            )
                        )
                    } else {
                        list.add(
                            ShopModel(
                                item = getString(
                                    R.string.STARNO
                                ),
                                isChosen = star.key.toString() == chosenItem.value.toString()
                            )
                        )
                    }
                    adapter.submitList(list)
                }
        }
    }

    private fun setuserName(userName: String, chosen: String) {
        binding.userName.text = userName
        if (chosen == "green") {
            binding.userName.setTextColor(Color.parseColor("#2DD2A2"))
        }
        else if (chosen == "yellow") {
            binding.userName.setTextColor(Color.parseColor("#ecdc13"))
        }
        else if (chosen == "red") {
            binding.userName.setTextColor(Color.parseColor("#D22D5D"))
        }
        else if (chosen == "pink") {
            binding.userName.setTextColor(Color.parseColor("#FFC0CB"))
        }
    }

    private fun getOthers() {
        val list = arrayListOf<ShopModel>()
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).get().addOnSuccessListener { others ->
                val score = others.child("highscore")
                val highscore = others.child("bestscore")
                val name = others.child("name")
                val chosenColour = others.child("shop").child("chosen_colour")
                setUserName(name.value.toString(),chosenColour.value.toString())
                list.add(
                    ShopModel(
                        item = getString(
                            R.string.naming
                        ) + name.value.toString(),
                        isChosen = false,
                        isSwitchEnabled = false
                    )
                )
                list.add(
                    ShopModel(
                        item = getString(
                            R.string.Cookkkies
                        ) + score.value.toString(),
                        isChosen = false,
                        isSwitchEnabled = false
                    )
                )
                list.add(
                    ShopModel(
                        item = getString(
                            R.string.Highestt
                        ) + highscore.value.toString(),
                        isChosen = false,
                        isSwitchEnabled = false
                    )
                )
                getColourShop(list)
            }
        }
    }

    private fun getShopData() {
        getOthers()
    }
}