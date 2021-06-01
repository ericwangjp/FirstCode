package com.example.firstcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.firstcode.databinding.ActivityRoomBinding
import kotlin.concurrent.thread

private const val TAG = "RoomActivity"

class RoomActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var activityRoomBinding: ActivityRoomBinding
    private lateinit var userDao: UserDao
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRoomBinding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(activityRoomBinding.root)
        initData()
    }

    private fun initData() {
        activityRoomBinding.let {
            it.btnAddUser.setOnClickListener(this)
            it.btnDeleteUser.setOnClickListener(this)
            it.btnUpdateUser.setOnClickListener(this)
            it.btnGetUser.setOnClickListener(this)
            it.btnQueryUser.setOnClickListener(this)
        }
        userDao = AppDatabase.getDatabase(this).userDao()
        user1 = User("Tom", "Bake", 19)
        user2 = User("Jack", "Hill", 20)
        user3 = User("Mike", "Json", 50)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_user -> {
                thread {
                    user1.id = userDao.insertUser(user1)
                    user2.id = userDao.insertUser(user2)
                    user3.id = userDao.insertUser(user3)
                }
            }
            R.id.btn_delete_user -> {
                thread {
                    userDao.deleteUserByLastName("Hill")
                }
            }
            R.id.btn_update_user -> {
                thread {
                    user1.age = 30
                    userDao.updateUser(user1)
                }
            }
            R.id.btn_get_user -> {
                thread {
                    for (user in userDao.loadAllUsers()) {
                        Log.e(TAG, "所有的用户: ${user.toString()}")
                    }
                }
            }
            R.id.btn_query_user -> {
                thread {
                    for (user in userDao.loadUsersOlderThan(20)) {
                        Log.e(TAG, "符合条件的数据: $user")
                    }
                }
            }
        }
    }
}