package com.example.firstcode

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private val TAG = "MyService"
    private val mBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: -->")
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "foreground_service",
                "前台Service通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, ChatActivity::class.java)
        val pi = PendingIntent.getActivity(this, 100, intent, 0)
        val notification = NotificationCompat.Builder(this, "foreground_service")
            .setContentTitle("服务通知")
            .setContentText("服务正在运行中")
            .setSmallIcon(R.drawable.small_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()
        startForeground(1, notification)
    }



    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: -->")
    }

    class MyBinder : Binder() {
        fun executeFun(){
            Log.e("MyBinder", "executeFun: -->")
        }
    }
}