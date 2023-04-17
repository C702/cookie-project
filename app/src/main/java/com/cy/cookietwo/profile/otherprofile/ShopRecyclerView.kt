package com.cy.cookietwo.profile.otherprofile

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cy.cookietwo.R

class ShopRecyclerView(
    val listener: Context?
) :
    ListAdapter<ShopModel, ShopRecyclerView.MatchViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop,
            parent,
            false
        )
        return MatchViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        with(getItem(position)) {
            holder.item.text = this.item
        }
    }

    inner class MatchViewHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val item: TextView = iv.findViewById(R.id.itemName)
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
        return oldItem.item == newItem.item
    }
}