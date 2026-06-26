package com.example.ui.screens
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
        // Fade in
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000)
        )
        // نگه داشتن
        delay(2000)
        // Fade out
        alphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(1000)
        )
        // Seed و رفتن به صفحه اصلی
        scope.launch {
            try { DataSeeder.seedIfEmpty() } catch (e: Exception) {}
        }
        onNavigateToSelection()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF003087)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.garage_logo),
            contentDescription = "کاراژ",
            modifier = Modifier
                .width(280.dp)
                .alpha(alphaAnim.value),
            contentScale = ContentScale.Fit
        )
    }
}
