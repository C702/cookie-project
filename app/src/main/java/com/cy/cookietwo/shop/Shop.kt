package com.cy.cookietwo.shop

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cy.cookietwo.R
import com.cy.cookietwo.databinding.FragmentShopBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Shop : Fragment() {

    var cookieCount = 0
    private lateinit var adapter: Shoprecyclerview
    val db = FirebaseDatabase.getInstance()
    private lateinit var binding: FragmentShopBinding
    val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCookie()
        clicker()
        initrecyclerview()
        getShopData()
    }

    private fun clicker() {
        binding.button7.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_shop_to_home2)
        }
    }

    private fun getCookie() {
        auth.currentUser?.let { user ->
            db.reference.child("leaderboard").child(user.uid).child("highscore").get()
                .addOnSuccessListener { cookie ->
                    cookieCount = cookie.value.toString().toInt()
                    binding.textView13.text =
                        getString(R.string.Cookkkies) + cookie.value.toString()

                }
        }
    }

    private fun initrecyclerview() {
        adapter = Shoprecyclerview(object : BuyClickListener{
            override fun isBuyClicked(item: ShopModel) {
                if (item.value <= cookieCount) {
                    val name = item.name?.lowercase()
                    auth.currentUser?.let { user ->
                         db.reference.child("leaderboard").child(user.uid).child("shop").get().addOnSuccessListener {
                             Log.d(TAG, "isBuyClicked: " + name.toString())
                             var numbertwo = it.child(name.toString()).value.toString().toInt()
                            Log.d(TAG, "isBuyClicked: " + it.child(name.toString()).value.toString().toInt())
                             val newCookieCount = cookieCount-item.value
                            db.reference.child("leaderboard").child(user.uid).child("highscore").setValue(newCookieCount)
                            db.reference.child("leaderboard").child(user.uid).child("shop").child(item.name.toString()).setValue(numbertwo.toString().toInt()+1)
                             getCookie()
                         }
                    }
                }
                else {
                    Toast.makeText(context, "Insufficent amount of cookies", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.rwtwo.layoutManager = LinearLayoutManager(context)
        binding.rwtwo.adapter = adapter
    }

    private fun getShopData() {
        val list = arrayListOf<ShopModel>()
        db.reference.child("shop").get().addOnSuccessListener {
            for (child in it.children) {
                var icon: String? = null
                var colour: String? = null
                child.child("icon").value?.let {
                    icon = it.toString()
                }
                child.child("colour").value?.let {
                    colour = it.toString()
                }
                list.add(
                    ShopModel(
                        id = child.child("id").value.toString().toInt(),
                        name = child.child("name").value.toString(),
                        text = child.child("text").value.toString(),
                        value = child.child("value").value.toString().toInt(),
                        textColour = colour,
                        userName = auth.currentUser?.email.toString(),
                        icon = icon
                    )
                )
            }
            adapter.submitList(list)
        }
    }
}