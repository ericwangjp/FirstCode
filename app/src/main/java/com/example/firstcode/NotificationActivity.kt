package com.example.firstcode

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.firstcode.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationBinding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)
        initData()
    }

    private fun initData() {
        var notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("normal", "普通通知", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)

            val channelImportant =
                NotificationChannel("important", "重要通知，请务必查看", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channelImportant)
        }
        notificationBinding.btnSendNotification.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 1, intent, 0)
            val notification = NotificationCompat.Builder(this, "normal")
                .setContentTitle("这是通知标题")
                .setContentText(
                    "这里是通知内容,很长很长很长很长很长很长很长很长很长很长很长很" +
                            "很长很长很长很长很长很长很长很长很长很长很长很长长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长..."
                )
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "长文本通知内容,很长很长很长很长很长很长很长很长很长很长很长很" +
                                "很长很长很长很长很长很长很长很长很长很长很长很长长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长..."
                    )
                )
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_image))
                )
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1, notification)
        }

        notificationBinding.btnSendImportantNotification.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 2, intent, 0)
            val notificationImportant = NotificationCompat.Builder(this, "important")
                .setContentTitle("这是一条重要通知标题")
                .setContentText("重要通知内容，很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长...")
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(2, notificationImportant)
        }
    }
}