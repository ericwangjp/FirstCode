package com.example.firstcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstcode.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private val msgList = ArrayList<ChatMsg>()
    private var adapter: ChatAdapter? = null
    private lateinit var chatBinding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)
        initData()
    }

    private fun initData() {
        val msg1 = ChatMsg("Hello,李磊", ChatMsg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = ChatMsg("Hello，韩梅梅", ChatMsg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = ChatMsg("吃了吗？", ChatMsg.TYPE_RECEIVED)
        msgList.add(msg3)
        val msg4 = ChatMsg("你猜", ChatMsg.TYPE_SENT)
        msgList.add(msg4)



        chatBinding.let {
            val layoutManager = LinearLayoutManager(this)
            it.rcvList.layoutManager = layoutManager
            adapter = ChatAdapter(msgList)
            it.rcvList.adapter = adapter
            it.btnSend.setOnClickListener {
                val content = chatBinding.edtMsg.text.toString()
                if (content.isNotBlank()) {
                    val msg = ChatMsg(content, ChatMsg.TYPE_SENT)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1)
                    chatBinding.rcvList.scrollToPosition(msgList.size - 1)
                    chatBinding.edtMsg.setText("")
                }
            }
        }
    }
}