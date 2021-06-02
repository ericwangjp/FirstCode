package com.example.firstcode.data.model

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: Weather
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/2 11:19 上午
 *
 *
 */
data class Weather(val realtime: RealtimeWeatherResp.Realtime, val daily: DailyWeatherResp.Daily)
