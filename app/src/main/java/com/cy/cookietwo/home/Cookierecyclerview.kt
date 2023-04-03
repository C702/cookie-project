package com.cy.cookietwo.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cy.cookietwo.R

class Cookierecyclerview(val listener: UserClickedListener
) :
    ListAdapter<RecyclerModel, Cookierecyclerview.MatchViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row,
            parent,
            false
        )
        return MatchViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        with(getItem(position)) {
            holder.name.text = this.name
            holder.count.text = this.high.toString()
            holder.itemView.setOnClickListener {
                listener.isuserclicked(this.id)
            }
        }
    }

    inner class MatchViewHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val name: TextView = iv.findViewById(R.id.textView7)
        val count: TextView = iv.findViewById(R.id.textView8)
        init {
            iv.setOnClickListener() {
                listener.isuserclicked(currentList.get(this.adapterPosition).id)
            }
        }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<RecyclerModel>() {
    override fun areItemsTheSame(oldItem: RecyclerModel, newItem: RecyclerModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: RecyclerModel,
        newItem: RecyclerModel
    ): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.count == newItem.count
    }
}