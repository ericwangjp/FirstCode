package com.example.firstcode

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: Book
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/31 5:09 下午
 *
 *
 */
@Entity
data class Book(var name: String, var pages: Int, var author: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

