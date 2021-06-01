package com.example.firstcode

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: ChatMsgViewHolder
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/21 1:46 下午
 *
 *
 */
sealed class ChatMsgViewHolder(view:View):RecyclerView.ViewHolder(view)
class LeftViewHolder(view: View) : ChatMsgViewHolder(view) {
    val leftMsg: TextView = view.findViewById(R.id.tv_left_msg_content)
}

class RightViewHolder(view: View) : ChatMsgViewHolder(view) {
    val rightMsg: TextView = view.findViewById(R.id.tv_right_msg_content)
}