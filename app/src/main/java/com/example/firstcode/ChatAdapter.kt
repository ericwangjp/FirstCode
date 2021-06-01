package com.example.firstcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: ChatAdapter
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/20 4:36 下午
 *
 *
 */
class ChatAdapter(val msgList: List<ChatMsg>) : RecyclerView.Adapter<ChatMsgViewHolder>() {

//    另外一种采用密封类处理方式见 ChatMsgViewHolder.kt
//    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val leftMsg: TextView = view.findViewById(R.id.tv_left_msg_content)
//    }
//
//    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val rightMsg: TextView = view.findViewById(R.id.tv_right_msg_content)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMsgViewHolder =
        if (viewType == ChatMsg.TYPE_RECEIVED) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_msg_left, parent, false)
            LeftViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_msg_right, parent, false)
            RightViewHolder(view)
        }

    override fun onBindViewHolder(holder: ChatMsgViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) {
            is LeftViewHolder -> holder.leftMsg.text = msg.content
            is RightViewHolder -> holder.rightMsg.text = msg.content
        }
    }

    override fun getItemCount(): Int = msgList.size

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }
}