package com.croc.weatherapppfebcappmov.domain

import com.croc.weatherapppfebcappmov.WeatherResponse
import com.croc.weatherapppfebcappmov.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//este código configura la comunicación con una API de clima usando Retrofit, permitiendo realizar
// solicitudes de red asincrónicas y recibir respuestas convertidas automáticamente en objetos.

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city : String,
        @Query("appid") apiKey : String,
        @Query("units") units: String = Constants.DEFAULT_UNIT
    //El método retorna un WeatherResponse, que es una clase de datos diseñada para recibir y almacenar
    // los datos de la respuesta de la API.
    ) : WeatherResponse

    // Este bloque crea una función de fábrica que permite construir una instancia de WeatherApi
    // utilizando Retrofit, una biblioteca de Android que facilita las solicitudes HTTP.
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