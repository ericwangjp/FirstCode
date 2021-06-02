package com.example.firstcode.data

import androidx.lifecycle.liveData
import com.example.firstcode.data.dao.PlaceDao
import com.example.firstcode.data.model.Place
import com.example.firstcode.data.model.Weather
import com.example.firstcode.data.network.HttpNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

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
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = HttpNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            kotlin.Result.success(places)
        } else {
            kotlin.Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                HttpNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                HttpNetwork.getDailyWeather(lng, lat)
            }

            val realtimeResp = deferredRealtime.await()
            val dailyResp = deferredDaily.await()
            if (realtimeResp.status == "ok" && dailyResp.status == "ok") {
                val weather = Weather(realtimeResp.result.realtime, dailyResp.result.daily)
                kotlin.Result.success(weather)
            } else {
                kotlin.Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResp.status}" +
                                "daily response status is ${dailyResp.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> kotlin.Result<T>) =
        liveData<kotlin.Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                kotlin.Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}