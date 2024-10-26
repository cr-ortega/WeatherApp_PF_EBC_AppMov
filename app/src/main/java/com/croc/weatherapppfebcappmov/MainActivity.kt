package com.croc.weatherapppfebcappmov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.croc.weatherapppfebcappmov.ui.theme.WeatherAppPFEBCAppMovTheme
import com.croc.weatherapppfebcappmov.view.components.weathercomponents.WeatherScreen
import com.croc.weatherapppfebcappmov.view.components.splash.SplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                // Muestra el Splash Screen al inicio
                SplashScreen(onSplashFinished = {
                    // Cambia a WeatherScreen después de que termine el splash
                    showSplash = false
                })
            } else {
                // Muestra la pantalla principal después del Splash Screen
                WeatherScreen()
            }
        }
    }
}

/*
* @Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    WeatherAppPFEBCAppMovTheme {
        WeatherScreen()
    }
}*/





