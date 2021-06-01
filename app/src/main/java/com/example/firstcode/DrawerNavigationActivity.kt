package com.example.firstcode

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstcode.databinding.ActivityDrawerNavigationBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class DrawerNavigationActivity : AppCompatActivity() {
    private lateinit var activityDrawerNavigationBinding: ActivityDrawerNavigationBinding
    private val fruits = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )
    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDrawerNavigationBinding = ActivityDrawerNavigationBinding.inflate(layoutInflater)
        setContentView(activityDrawerNavigationBinding.root)
        initData()
    }

    private fun initData() {
        setSupportActionBar(activityDrawerNavigationBinding.toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
//        activityDrawerNavigationBinding.navView.setCheckedItem(R.id.navCall)
        activityDrawerNavigationBinding.navView.setNavigationItemSelectedListener {
            activityDrawerNavigationBinding.layoutDrawer.closeDrawers()
            true
        }

        activityDrawerNavigationBinding.swipeRefresh.run {
            setColorSchemeResources(R.color.purple_700)
            setOnRefreshListener {
//                runBlocking {
//                    fruitList.clear()
//                    repeat(50) {
//                        val index = (0 until fruits.size).random()
//                        fruitList.add(fruits[index])
//                    }
//                    delay(2000)
//                    Log.e("数据处理中。。。",Thread.currentThread().name)
//                }
//                Log.e("开始刷新",Thread.currentThread().name)
                thread {
                    Thread.sleep(2000)
                    fruitList.clear()
                    repeat(50) {
                        val index = (0 until fruits.size).random()
                        fruitList.add(fruits[index])
                    }
                    runOnUiThread {
                        activityDrawerNavigationBinding.rcvList.adapter?.notifyDataSetChanged()
                        isRefreshing = false
                    }
                }
            }
        }


        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
        activityDrawerNavigationBinding.rcvList.layoutManager = GridLayoutManager(this, 2)
        activityDrawerNavigationBinding.rcvList.adapter = FruitAdapter(this, fruitList)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.backup -> {
                Snackbar.make(activityDrawerNavigationBinding.fb, "备份", Snackbar.LENGTH_SHORT)
                    .setAction("这是action按钮") { v ->
                        Toast.makeText(
                            v?.context,
                            "你点击了snackBar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
            R.id.delete -> {
                Snackbar.make(activityDrawerNavigationBinding.root, "删除", Snackbar.LENGTH_SHORT)
                    .setAction("这是action按钮") { v ->
                        Toast.makeText(
                            v?.context,
                            "你点击了snackBar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
            R.id.settings -> {
                Snackbar.make(activityDrawerNavigationBinding.root, "设置", Snackbar.LENGTH_SHORT)
                    .setAction("这是action按钮") { v ->
                        Toast.makeText(
                            v?.context,
                            "你点击了snackBar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }

            android.R.id.home -> {
                activityDrawerNavigationBinding.layoutDrawer.openDrawer(GravityCompat.START)
            }
        }
        return true
    }
}