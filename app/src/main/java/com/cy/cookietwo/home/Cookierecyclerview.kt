package com.cy.cookietwo.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
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
            setIcon(this.icon, holder.icon)
            if (this.colour != null) {
                setTextColour(this.colour, holder.name)
                //holder.name.setTextColor(R.color.)
            }
            holder.itemView.setOnClickListener {
                listener.isuserclicked(this.id)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun setTextColour(colour: String?, text: TextView) {
        when (colour) {
            "pink"-> text.setTextColor(Color.parseColor("#FFC0CB"))
            "red"-> text.setTextColor(Color.parseColor("#D22D5D"))
            "green"-> text.setTextColor(Color.parseColor("#2DD2A2"))
            "yellow"-> text.setTextColor(Color.parseColor("#ecdc13"))
        }
    }

    fun setIcon(icon: String?, icon1: ImageView) {
        if (icon == "star") {
            icon1.setImageResource(R.drawable.ic_star)
        }
        else if (icon == "moon") {
            icon1.setImageResource(R.drawable.ic_moon)
        }
    }

    inner class MatchViewHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val name: TextView = iv.findViewById(R.id.userNameInfo)
        val count: TextView = iv.findViewById(R.id.cookieNumber)
        val icon: ImageView = iv.findViewById(R.id.iconRW)
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