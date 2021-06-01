package com.example.firstcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.firstcode.databinding.ActivityFruitDetailBinding

class FruitDetailActivity : AppCompatActivity() {
    private lateinit var activityFruitDetailBinding: ActivityFruitDetailBinding

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMG_ID = "fruit_img_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFruitDetailBinding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(activityFruitDetailBinding.root)
        initData()
    }

    private fun initData() {
        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: ""
        val fruitImgId = intent.getIntExtra(FRUIT_IMG_ID, 0)
        setSupportActionBar(activityFruitDetailBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activityFruitDetailBinding.collapsingLayout.title = fruitName
        Glide.with(this).load(fruitImgId).into(activityFruitDetailBinding.imgFruit)
        activityFruitDetailBinding.tvFruitDesc.text = fruitName.repeat(500)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}