package com.example.firstcode.data.network

import com.example.firstcode.AppController
import com.example.firstcode.data.model.PlaceResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: PlaceService
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/1 2:17 下午
 *
 *
 */
interface PlaceService {
    @GET("v2/place?token=${AppController.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResp>
}