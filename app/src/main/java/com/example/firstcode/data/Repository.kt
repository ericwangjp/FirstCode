package com.example.firstcode.data

import androidx.lifecycle.liveData
import com.example.firstcode.data.model.Place
import com.example.firstcode.data.network.HttpNetwork
import kotlinx.coroutines.Dispatchers

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: Repository
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/1 2:46 下午
 *
 *
 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = HttpNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                kotlin.Result.success(places)
            } else {
                kotlin.Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
        emit(result)
    }
}