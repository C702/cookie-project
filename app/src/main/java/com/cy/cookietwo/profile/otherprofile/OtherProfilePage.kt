package com.cy.cookietwo.profile.otherprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun initrecyclerview() {
        adapter = ShopRecyclerView(context)
        binding.shopRw.layoutManager = LinearLayoutManager(context)
        binding.shopRw.adapter = adapter
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
                                            .child(args.userId.toString()).child("shop")
                                            .child("rebirth")
                                            .get().addOnSuccessListener { rebirth ->
                                                db.reference.child("leaderboard")
                                                    .child(args.userId.toString()).child("shop")
                                                    .child("adder")
                                                    .get().addOnSuccessListener { adder ->
                                                        binding.textView17.text =
                                                            getString(R.string.namingone) + " " + name.value.toString() + getString(
                                                                R.string.twodot
                                                            )
                                                        list.add(
                                                            ShopModel(
                                                                item = getString(R.string.naming) + name.value.toString()
                                                            )
                                                        )
                                                        list.add(
                                                            ShopModel(
                                                                item = getString(R.string.Cookkkies) + cookie.value.toString()
                                                            )
                                                        )
                                                        list.add(
                                                            ShopModel(
                                                                item = getString(R.string.Highestt) + best.value.toString()
                                                            )
                                                        )
                                                        Log.d("TAG", "getShopData: " + vip.value.toString())
                                                        if (vip.value.toString().toInt() != 1) {
                                                            list.add(
                                                                ShopModel(
                                                                    item = getString(R.string.VIPYES)
                                                                )
                                                            )
                                                        }
                                                        else {
                                                            list.add(
                                                                ShopModel(
                                                                    item = getString(R.string.VIPNO)
                                                                )
                                                            )
                                                        }
                                                            list.add(
                                                                ShopModel(
                                                                    item = getString(R.string.RebirthProfile) + rebirth.value.toString()
                                                                )
                                                            )
                                                        list.add(
                                                            ShopModel(
                                                                item = getString(R.string.AdderProfile) + adder.value.toString()
                                                            )
                                                        )
                                                        adapter.submitList(list)
                                                    }

                                            }
                                    }
                            }
                    }
            }
        binding.button3.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_otherProfilePage_to_home2)
        }
    }

}