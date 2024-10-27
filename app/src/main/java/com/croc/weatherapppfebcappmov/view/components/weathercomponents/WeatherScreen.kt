package com.croc.weatherapppfebcappmov.view.components.weathercomponents

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.croc.weatherapppfebcappmov.R
import com.croc.weatherapppfebcappmov.ui.theme.BlueJC
import com.croc.weatherapppfebcappmov.ui.theme.DarkBlueJC
import com.croc.weatherapppfebcappmov.ui.theme.DarkGreenJC
import com.croc.weatherapppfebcappmov.ui.theme.josefinSansFamily
import com.croc.weatherapppfebcappmov.utils.Constants
import com.croc.weatherapppfebcappmov.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

// saveSelected y getSelected manejan el almacenamiento y recuperación de la unidad de medida.
fun saveSelectedUnit(context: Context, unit: String) {
    val sharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("selected_unit", unit).apply()
}

fun getSelectedUnit(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("selected_unit", "metric") ?: "metric" // "metric" por defecto
}

// Convertidor de unidades con Celsius por defecto.
@Composable
fun convertTemperature(temp: Float, unit: String): String {
    return when (unit) {
        "imperial" -> "${((temp * 9 / 5) + 32).roundToInt()}" + Constants.FAHRENHEIT_SYMBOL
        "standard" -> "${(temp + 273.15).roundToInt()}" + Constants.KELVIN_SYMBOL
        else -> "${temp.roundToInt()}" + Constants.CELSIUS_SYMBOL
    }
}

// Bloque de la pantalla principal de la aplicación, donde se gestionan los elementos visuales y de interacción.
@Composable
fun WeatherScreen() {
    val context = LocalContext.current
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = Constants.API_KEY
    // Definir el color en formato 0x
    val customBackgroundColor = Color(0xFFE9F7EF)
    val unitsButtonColor = Color(0xFF6AB04C)
    val checkWeatherButtonColor = Color(0xFF625b71)
    val buttonTextColor = Color(0xFFFFFFFF)
    val dropdownBackgroundColor = Color(0xFFBCCCC3)

    // Cargar la animación Lottie desde el archivo .json en res/raw
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_weather))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    var expanded by remember { mutableStateOf(false) }

    // Recuperar la unidad seleccionada desde SharedPreferences
    var selectedUnit by remember { mutableStateOf(getSelectedUnit(context)) }

    val unitsList = listOf(
        Constants.CELSIUS_UNIT,
        Constants.FAHRENHEIT_UNIT,
        Constants.KELVIN_UNIT
    )

    val unitQueryMapping = mapOf(
        Constants.CELSIUS_UNIT to "metric",
        Constants.FAHRENHEIT_UNIT to "imperial",
        Constants.KELVIN_UNIT to "standard"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Establece el fondo
            .background(customBackgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Animación Lottie dentro del Box
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(180.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            //Cuadro de texto de búsqueda
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(
                        stringResource(R.string.city_name),
                        fontFamily = josefinSansFamily,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 22.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = BlueJC,
                    focusedIndicatorColor = DarkGreenJC,
                    focusedLabelColor = DarkBlueJC
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Botón de consulta del clima
            Button(
                onClick = { viewModel.fetchWeather(city, apiKey) },
                colors = ButtonDefaults.buttonColors(checkWeatherButtonColor),
            ) {
                Text(
                    text = stringResource(R.string.check_weather_button_text),
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para desplegar el menú de selección de unidad de medida
            Box(

            ) {
                Button(
                { expanded = true },
                colors = ButtonDefaults.buttonColors(unitsButtonColor, buttonTextColor)
                    ) {
                Text(
                    text = stringResource(R.string.select_unit_button),
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                    )
                }


                // DropdownMenu para seleccionar la unidad
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(color = dropdownBackgroundColor)
                ) {
                    unitsList.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(text = unit) },
                            onClick = {
                                selectedUnit = unitQueryMapping[unit] ?: "metric"
                                expanded = false
                                // Guardar en SharedPreferences
                                saveSelectedUnit(
                                    context,
                                    selectedUnit
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            //Tarjetas para mostrar los datos de clima.
            weatherData?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(
                        label = stringResource(R.string.city_name),
                        value = it.name,
                        icon = Icons.Default.Place
                    )
                    WeatherCard(
                        label = stringResource(R.string.temperature),
                        value = convertTemperature(it.main.temp, selectedUnit),
                        icon = Icons.Default.Star
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(
                        label = stringResource(R.string.humidity),
                        value = "${it.main.humidity}" + Constants.PERCENTAGE_SYMBOL,
                        icon = Icons.Default.Warning
                    )
                    WeatherCard(
                        label = stringResource(R.string.weather_description),
                        value = it.weather[0].description,
                        icon = Icons.Default.Info
                    )
                }
            }
        }
    }

    //Datos inferiores de información de la aplicación.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Texto inferior
        Text(
            text = Constants.APP_NAME,
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.by_text) + "\n" + Constants.DEVELOPER,
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(top = 10.dp),
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}