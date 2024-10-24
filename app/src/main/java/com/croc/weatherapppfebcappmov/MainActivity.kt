package com.croc.weatherapppfebcappmov

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.croc.weatherapppfebcappmov.ui.theme.BlueJC
import com.croc.weatherapppfebcappmov.ui.theme.DarkBlueJC
import com.croc.weatherapppfebcappmov.ui.theme.WeatherAppPFEBCAppMovTheme
import com.croc.weatherapppfebcappmov.ui.theme.josefinSansFamily
import kotlin.math.roundToInt
import kotlin.math.roundToLong


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

// Composable para el Splash Screen
@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    // Cargar la animación Lottie desde el archivo .json en res/raw
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_weather))
    val progress by animateLottieCompositionAsState(composition = composition,
        iterations = LottieConstants.IterateForever)

    val customBackgroundColor = Color(0xFFE9F7EF)

    // Este LaunchedEffect se asegura de que la animación dure 3 segundos y luego cambie de pantalla
    LaunchedEffect(key1 = true) {
        // Tiempo que dura el splash screen
        kotlinx.coroutines.delay(3000)
        // Cambia a la siguiente pantalla
        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(customBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
        modifier = Modifier
            // Establece el fondo
            .background(customBackgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
        ) {
        // Mostrar la animación Lottie
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(300.dp) // Tamaño de la animación
        ) }

        Box(
            modifier = Modifier
                .background(customBackgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Texto superior
            Text(
                text = stringResource(R.string.app_name) + "\n" + stringResource(R.string.project_name),
                fontFamily = josefinSansFamily,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 35.sp,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                // Establece el fondo
                .background(customBackgroundColor) 
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Texto inferior
            Text(
                text = stringResource(R.string.by_text) + "\n" + stringResource(R.string.developer_name) + "\n\n" +
                        stringResource(R.string.loading_text),
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun saveSelectedUnit(context: Context, unit: String) {
    val sharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("selected_unit", unit).apply()
}

fun getSelectedUnit(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("selected_unit", "metric") ?: "metric" // "metric" por defecto
}

@Composable
fun convertTemperature(temp: Float, unit: String): String {
    return when (unit) {
        "imperial" -> "${((temp * 9 / 5) + 32).roundToInt()}" + stringResource(R.string.farenheit_grades_symbol) // Conversión a Fahrenheit
        "standard" -> "${(temp + 273.15).roundToInt()}" + stringResource(R.string.kelvin_grades_symbol) // Conversión a Kelvin
        else -> "${temp.roundToInt()}" + stringResource(R.string.celsius_grades_symbol) // Por defecto Celsius (métrico)
    }
}

@Composable
fun WeatherScreen() {
    val context = LocalContext.current
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "c3fa666b0136d6bd8f167559e4351f60"
    // Definir el color en formato 0x
    val customBackgroundColor = Color(0xFFE9F7EF)
    val unitsButtonColor = Color(0xFF6AB04C)
    val checkWeatherButtonColor = Color(0xFFA7E35F)
    val buttonTextColor = Color(0xFFFFFFFF)
    val dropdownBackgroundColor = Color(0xFF73F3AF)

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
        stringResource(R.string.celsius_unit),
        stringResource(R.string.farenheit_unit),
        stringResource(R.string.kelvin_unit)
    )
    val unitQueryMapping = mapOf(
        stringResource(R.string.celsius_unit) to "metric",
        stringResource(R.string.farenheit_unit) to "imperial",
        stringResource(R.string.kelvin_unit) to "standard"
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
                //.align(Alignment.TopCenter)
            )
            Spacer(modifier = Modifier.height(5.dp))

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
                    focusedIndicatorColor = BlueJC,
                    focusedLabelColor = DarkBlueJC
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para seleccionar la unidad
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
                            saveSelectedUnit(
                                context,
                                selectedUnit
                            ) // Guardar en SharedPreferences
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        value = "${it.main.humidity}" + stringResource(R.string.percentage_symbol),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Texto inferior
        Text(
            text = stringResource(R.string.app_name),
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.by_text) + "\n" + stringResource(R.string.developer_name),
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

@Composable
fun WeatherCard(label : String, value : String, icon : ImageVector) {
    Card(modifier = Modifier
        .padding(8.dp)
        .size(150.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = DarkBlueJC,
                    modifier = Modifier.width(17.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label,
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = DarkBlueJC)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight (1F),
                contentAlignment = Alignment.Center
            ) {
                Text(text = value,
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = BlueJC)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    WeatherAppPFEBCAppMovTheme {
        WeatherScreen()
    }
}



