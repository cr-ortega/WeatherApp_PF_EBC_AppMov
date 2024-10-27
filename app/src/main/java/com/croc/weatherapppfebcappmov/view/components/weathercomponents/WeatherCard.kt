package com.croc.weatherapppfebcappmov.view.components.weathercomponents

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croc.weatherapppfebcappmov.ui.theme.DarkBlueJC
import com.croc.weatherapppfebcappmov.ui.theme.DarkGreenJC
import com.croc.weatherapppfebcappmov.ui.theme.josefinSansFamily

//Generador de las tarjetas del tablero en el que se muestran los datos de clima.
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
                    fontSize = 15.sp,
                    color = DarkBlueJC
                )
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
                    color = DarkGreenJC
                )
            }
        }
    }
}