package com.example.firstcode.ui.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firstcode.R
import com.example.firstcode.data.model.Weather
import com.example.firstcode.data.model.getSky
import com.example.firstcode.databinding.ActivityWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    lateinit var activityWeatherBinding: ActivityWeatherBinding
    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        activityWeatherBinding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(activityWeatherBinding.root)
        initData()
    }

    private fun initData() {
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
            activityWeatherBinding.swipeRefresh.isRefreshing = false
        })

        activityWeatherBinding.swipeRefresh.let {
            it.setColorSchemeResources(R.color.purple_700)
            it.setOnRefreshListener {
                refreshWeather()
            }
        }

        refreshWeather()

        activityWeatherBinding.now.navBtn.setOnClickListener {
            activityWeatherBinding.layoutDrawer.openDrawer(GravityCompat.START)
        }

        activityWeatherBinding.layoutDrawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                Log.e("weather", "onDrawerSlide: $slideOffset")
            }

            override fun onDrawerOpened(drawerView: View) {
                Log.e("weather", "onDrawerOpened: ")
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    activityWeatherBinding.layoutDrawer.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {
                Log.e("weather", "onDrawerStateChanged: $newState")
            }

        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        activityWeatherBinding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        activityWeatherBinding.now.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
//        now ??????
        val currentTempText = "${realtime.temperature.toInt()} ??C"
        activityWeatherBinding.now.let {
            it.currentTemp.text = currentTempText
            it.currentSky.text = getSky(realtime.skycon).info
            it.currentAQI.text = "???????????? ${realtime.airQuality.aqi.chn.toInt()}"
            it.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        }

//        forecast??????
        activityWeatherBinding.forecast.let {
            it.forecastLayout.removeAllViews()
            val days = daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]
                val view = LayoutInflater.from(this).inflate(
                    R.layout.item_forecast,
                    it.forecastLayout, false
                )
                val dateInfo = view.findViewById(R.id.dateInfo) as TextView
                val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
                val skyInfo = view.findViewById(R.id.skyInfo) as TextView
                val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
                val simpleDateFormat = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )
                dateInfo.text = simpleDateFormat.format(skycon.date)
                val sky = getSky(skycon.value)
                skyIcon.setImageResource(sky.icon)
                skyInfo.text = sky.info
                temperatureInfo.text = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ??C"
                it.forecastLayout.addView(view)
            }
        }
//        life_index??????
        val lifeIndex = daily.lifeIndex
        activityWeatherBinding.index.let {
            it.coldRiskText.text = lifeIndex.coldRisk[0].desc
            it.dressingText.text = lifeIndex.dressing[0].desc
            it.ultravioletText.text = lifeIndex.ultraviolet[0].desc
            it.carWashingText.text = lifeIndex.carWashing[0].desc
            activityWeatherBinding.weatherLayout.visibility = View.VISIBLE
        }

    }
}