package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.DataSeeder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateToSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alphaAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        alphaAnim.animateTo(targetValue = 1f, animationSpec = tween(1000))
        delay(2000)
        alphaAnim.animateTo(targetValue = 0f, animationSpec = tween(1000))
        scope.launch {
            try { DataSeeder.seedIfEmpty() } catch (e: Exception) {}
        }
        onNavigateToSelection()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A1628)),
        contentAlignment = Alignment.CenterStart
    ) {
        // گردیانت نرم پشت لوگو
        Box(
            modifier = Modifier
                .size(320.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1A3A6E).copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    )
                )
        )

        Image(
            painter = painterResource(id = R.drawable.img_car_parts_splash),
            contentDescription = "یدک مارکت",
            modifier = Modifier
                .width(450.dp)
                .alpha(alphaAnim.value),
            contentScale = ContentScale.Fit
        )
    }
}
