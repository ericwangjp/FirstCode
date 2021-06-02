package com.example.firstcode.data.dao

import android.content.Context
import androidx.core.content.edit
import com.example.firstcode.AppController
import com.example.firstcode.data.model.Place
import com.google.gson.Gson

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: PlaceDao
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/6/2 7:39 下午
 *
 *
 */
object PlaceDao {

    private fun sharedPreferences() =
        AppController.context.getSharedPreferences("weather", Context.MODE_PRIVATE)

    fun isPlaceSaved() = sharedPreferences().contains("place")

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }
}