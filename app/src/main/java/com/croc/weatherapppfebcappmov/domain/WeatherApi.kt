package com.croc.weatherapppfebcappmov.domain

import com.croc.weatherapppfebcappmov.WeatherResponse
import com.croc.weatherapppfebcappmov.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city : String,
        @Query("appid") apiKey : String,
        @Query("units") units: String = Constants.DEFAULT_UNIT
    ) : WeatherResponse

    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create() : WeatherApi {
            val retrofit = Retrofit.Builder ()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherApi::class.java)
        }
    }
}