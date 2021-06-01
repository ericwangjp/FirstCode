package com.example.firstcode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: BookDao
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/31 5:10 下午
 *
 *
 */
@Dao
interface BookDao {
    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>
}