package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Enable Left-to-Right structure if needed, but Persian is RTL. 
    // Splash screen can be centered and balanced.
    val scaleAnim = remember { Animatable(0.8f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        animate(
            initialValue = 0.8f,
            targetValue = 1.05f,
            animationSpec = twinPercentSpringSpec()
        ) { value, _ ->
            // Mild scale bounce
        }
    }

    LaunchedEffect(key1 = true) {
        scaleAnim.animateTo(
            targetValue = 1.0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    LaunchedEffect(key1 = true) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(1200)
        )
        // Wait and then go to Brand Selection
        delay(2200)
        onNavigateToSelection()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000), // Pure top black
                        Color(0xFF020E22), // Rich navy blue core
                        Color(0xFF010A16)  // Midnight low edge
                    )
                )
            )
            .testTag("splash_screen_root")
    ) {
        // Decorative glowing circle behind
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.Center)
                .alpha(0.08f)
                .background(Color(0xFF0056A4), shape = CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Main branding container
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            ) {
                // Outer glow border for image
                Box(
                    modifier = Modifier
                        .size(174.dp)
                        .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_car_parts_splash),
                        contentDescription = "سامانه قطعات همراه",
                        modifier = Modifier
                            .size(158.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "همراه قطعات",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("splash_title")
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "سامانه فروش قطعات خودرو",
                    fontSize = 17.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.testTag("splash_subtitle")
                )
            }

            // Bottom loading progress & version details
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFA855F7), // Soft modern purple light indicator
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("splash_loading")
                )

                Text(
                    text = "نسخه ۱.۰ (MVP UI)",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.4f),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

private fun twinPercentSpringSpec(): AnimationSpec<Float> {
    return spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessVeryLow
    )
}
