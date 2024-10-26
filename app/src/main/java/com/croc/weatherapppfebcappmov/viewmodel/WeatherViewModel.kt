package com.croc.weatherapppfebcappmov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.croc.weatherapppfebcappmov.domain.WeatherApi
import com.croc.weatherapppfebcappmov.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData : StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherApi.create()

    fun fetchWeather(city: String, apiKey: String, units: String = "metric") {
        viewModelScope.launch {
            try {
                // Pasamos la unidad seleccionada (units) a la llamada de la API
                val response = weatherApi.getWeather(city, apiKey, units)
                _weatherData.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}