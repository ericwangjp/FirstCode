package com.example.firstcode

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.firstcode.databinding.ActivityMainBinding
import com.example.firstcode.ui.place.PlaceActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "MainActivity"
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var timeChangeReceiver: TimeChangeReceiver
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.e(TAG, "onServiceConnected: ==>")
            val myBinder = p1 as MyService.MyBinder
            myBinder.executeFun()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected: -->")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initData()
    }

    private fun initData() {
        lifecycle.addObserver(MyLifecycleObserver())
        if (::mainBinding.isLateinit) {
            mainBinding.let {
                it.btnChat.setOnClickListener(this)
                it.btnContact.setOnClickListener(this)
                it.btnNotification.setOnClickListener(this)
                it.btnTakePhoto.setOnClickListener(this)
                it.btnStartService.setOnClickListener(this)
                it.btnStopService.setOnClickListener(this)
                it.btnStartIntentService.setOnClickListener(this)
                it.btnDrawerNavigation.setOnClickListener(this)
                it.btnRoom.setOnClickListener(this)
                it.btnWorkManager.setOnClickListener(this)
                it.btnPlace.setOnClickListener(this)
            }
        }

        val intentFilter = IntentFilter("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    inner class TimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0, "时间变化了", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_chat -> {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_contact -> {
                val intent = Intent(this, ContentResolverActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_notification -> {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_take_photo -> {
                val intent = Intent(this, TakePhotoActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_start_service -> {
                val intent = Intent(this, MyService::class.java)
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
//                startService(intent)
            }
            R.id.btn_stop_service -> {
                val intent = Intent(this, MyService::class.java)
//                stopService(intent)
                unbindService(connection)
            }
            R.id.btn_start_intent_service -> {
                MyIntentService.startActionBaz(this, "第一个参数", "第二个参数")
                MyIntentService.startActionFoo(this, "第一个参数", "第二个参数")
            }
            R.id.btn_drawer_navigation -> {
                val intent = Intent(this, DrawerNavigationActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_room -> {
                val intent = Intent(this, RoomActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_work_manager -> {
                val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                    .setInitialDelay(3, TimeUnit.SECONDS)
                    .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                    .addTag("simple")
                    .build()
                WorkManager.getInstance(this).let { workManager ->
                    workManager.enqueue(request)
                    workManager.getWorkInfoByIdLiveData(request.id)
                        .observe(this) {
                            if (it.state == WorkInfo.State.SUCCEEDED) {
                                Log.e(TAG, "work state: success")
                            } else if (it.state == WorkInfo.State.FAILED) {
                                Log.e(TAG, "work state: failed")
                            }
                        }
                }
            }

            R.id.btn_place -> {
                val intent = Intent(this, PlaceActivity::class.java)
                startActivity(intent)
            }
        }
    }
}