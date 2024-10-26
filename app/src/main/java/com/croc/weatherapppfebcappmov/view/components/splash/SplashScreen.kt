package com.croc.weatherapppfebcappmov.view.components.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.croc.weatherapppfebcappmov.R
import com.croc.weatherapppfebcappmov.ui.theme.josefinSansFamily
import com.croc.weatherapppfebcappmov.utils.Constants

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
                text = Constants.APP_NAME + "\n" + Constants.PROJECT_NAME,
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
                text = stringResource(R.string.by_text) + "\n" + Constants.DEVELOPER + "\n\n" +
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