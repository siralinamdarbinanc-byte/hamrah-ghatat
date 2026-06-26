package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
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

@Composable
fun BrandSelectionScreen(
    onNavigateToIranKhodro: () -> Unit,
    onNavigateToSaipa: () -> Unit,
    onNavigateToOtherCars: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFE31E24))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // لوگو کاراژ
                Image(
                    painter = painterResource(id = R.drawable.garage_logo),
                    contentDescription = "کاراژ",
                    modifier = Modifier
                        .width(220.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(40.dp))

                // کارت ایران‌خودرو
                BrandCardMockup(
                    title = "ایران\u200Cخودرو",
                    subtitle = "قطعات خودروهای ایران\u200Cخودرو",
                    cardColor = Color(0xFF0D2C54),
                    borderColor = Color(0xFF1A3F6F),
                    onClick = onNavigateToIranKhodro,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_ikco),
                            contentDescription = "ایران\u200Cخودرو",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_ikco")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // کارت سایپا
                BrandCardMockup(
                    title = "سایپا",
                    subtitle = "قطعات خودروهای سایپا",
                    cardColor = Color(0xFFC96A1A),
                    borderColor = Color(0xFFD97B2A),
                    onClick = onNavigateToSaipa,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_saipa),
                            contentDescription = "سایپا",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_saipa")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // کارت سایر خودروها
                BrandCardMockup(
                    title = "سایر خودروها",
                    subtitle = "قطعات سایر برندهای خودرو",
                    cardColor = Color(0xFF1F4D3A),
                    borderColor = Color(0xFF2A6A4F),
                    onClick = onNavigateToOtherCars,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_car),
                            contentDescription = "سایر خودروها",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_others")
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureItem(icon = Icons.Default.Verified, title = "ضمانت اصالت")
                    FeatureItem(icon = Icons.Default.Star, title = "کیفیت تضمینی")
                    FeatureItem(icon = Icons.Default.LocalShipping, title = "ارسال سریع")
                    FeatureItem(icon = Icons.Default.HeadsetMic, title = "پشتیبانی 24/7")
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun BrandCardMockup(
    title: String,
    subtitle: String,
    cardColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    logoContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Box(
            modifier = Modifier
                .background(cardColor)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(76.dp),
                    contentAlignment = Alignment.Center
                ) {
                    logoContent()
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(0.45f)
                        .background(Color.White.copy(alpha = 0.12f))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFA7B1C2)
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "انتخاب",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun FeatureItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.15f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}
