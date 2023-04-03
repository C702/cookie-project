package com.cy.cookietwo.shop

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cy.cookietwo.R
import com.cy.cookietwo.home.RecyclerModel
import com.cy.cookietwo.home.UserClickedListener

class Shoprecyclerview(val listener: BuyClickListener
) :
    ListAdapter<ShopModel, Shoprecyclerview.MatchViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rowtwo,
            parent,
            false
        )
        return MatchViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        with(getItem(position)) {
            holder.info.text = this.text
            holder.price.text = "Price: " + this.value.toString()
            holder.buy.setOnClickListener {
                listener.isBuyClicked(this)
            }
        }
    }

    inner class MatchViewHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val info: TextView = iv.findViewById(R.id.textView14)
        val price: TextView = iv.findViewById(R.id.textView15)
        val buy: Button = iv.findViewById(R.id.button4)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<ShopModel>() {
    override fun areItemsTheSame(oldItem: ShopModel, newItem: ShopModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ShopModel,
        newItem: ShopModel
    ): Boolean {
        return oldItem.text == newItem.text &&
                oldItem.value == newItem.value
    }
}