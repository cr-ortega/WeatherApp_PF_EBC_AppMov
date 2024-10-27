package com.croc.weatherapppfebcappmov

// Estas clases modelan la respuesta JSON que la API de clima devuelve.
// Cuando la API responde, Retrofit usa GsonConverterFactory para convertir
// automáticamente el JSON en objetos de Kotlin.

//Toma el nombre de la ciudad y agrupa con este los datos de las otras clases.
data class WeatherResponse(
    val name : String,
    val main: Main,
    val weather: List<Weather>
)

// Datos de condiciones de temperatura y humedad.
data class Main(
    val temp : Float,
    val humidity : Int
)

// Datos de descripción del clima.
data class Weather(
    val description : String
)