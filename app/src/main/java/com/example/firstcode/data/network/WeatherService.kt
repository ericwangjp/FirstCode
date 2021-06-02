package com.example.firstcode.data.network

import com.example.firstcode.AppController
import com.example.firstcode.data.model.DailyWeatherResp
import com.example.firstcode.data.model.RealtimeWeatherResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: WeatherService
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/2 11:22 上午
 *
 *
 */
interface WeatherService {
    @GET("v2.5/${AppController.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeWeatherResp>

    @GET("v2.5/${AppController.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyWeatherResp>
}