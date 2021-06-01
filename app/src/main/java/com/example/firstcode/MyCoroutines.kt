package com.example.firstcode

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.*
import java.time.Duration

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: MyCoroutines
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/27 2:07 下午
 *
 *
 */
fun main() {
//    GlobalScope.launch {
//        println("codes run in coroutine scope")
//    }
//    runBlocking {
//        println("aaaa")
//        delay(1500)
//        println("bbbb")
//    }

//    val job = Job()
//    val scope = CoroutineScope(job)
//    scope.launch {
//        println("协程中运行：${Thread.currentThread().name}")
//    }
//    job.cancel()
}

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

