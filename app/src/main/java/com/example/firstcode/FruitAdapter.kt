package com.example.firstcode

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstcode.databinding.ItemFruitBinding

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: FruitAdapter
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/28 2:44 下午
 *
 *
 */
class FruitAdapter(val context: Context, private val fruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    class ViewHolder(itemFruitBinding: ItemFruitBinding) :
        RecyclerView.ViewHolder(itemFruitBinding.root) {
        val fruitImg: ImageView = itemFruitBinding.imgFruit
        val fruitName: TextView = itemFruitBinding.tvFruitName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemFruitBinding: ItemFruitBinding =
            ItemFruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(itemFruitBinding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val fruit = fruitList[position]
            val intent = Intent(context, FruitDetailActivity::class.java).apply {
                putExtra(FruitDetailActivity.FRUIT_NAME, fruit.name)
                putExtra(FruitDetailActivity.FRUIT_IMG_ID, fruit.imgId)
            }
            context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruitItem = fruitList[position]
        holder.fruitName.text = fruitItem.name
        Glide.with(context).load(fruitItem.imgId).into(holder.fruitImg)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}