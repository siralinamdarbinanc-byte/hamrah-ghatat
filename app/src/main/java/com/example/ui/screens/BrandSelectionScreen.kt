package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.draw.clip
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

@Composable
fun BrandSelectionScreen(
    onNavigateToIranKhodro: () -> Unit,
    onNavigateToSaipa: () -> Unit,
    onNavigateToOtherCars: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        // رنگ پس‌زمینه اصلی: خاکستری روشن (مثل تصویر سمت راست)
        val backgroundColor = Color(0xFF003087)

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                // ===== بخش 1: هدر قرمز رنگ =====
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF003087))
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.garage_logo),
                            contentDescription = "کاراژ",
                            modifier = Modifier
                                .width(240.dp)
                                .height(96.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "قطعات خودرو",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== بخش 2: کارت‌ها (با پس‌زمینه سفید) =====
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // کارت ایران‌خودرو
                    BrandCardLight(
                        title = "ایران‌خودرو",
                        subtitle = "قطعات خودروهای ایران‌خودرو",
                        iconColor = Color(0xFF003087),
                        onClick = onNavigateToIranKhodro,
                        logoContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_ikco),
                                contentDescription = "ایران‌خودرو",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("brand_card_ikco")
                    )

                    // کارت سایپا
                    BrandCardLight(
                        title = "سایپا",
                        subtitle = "قطعات خودروهای سایپا",
                        iconColor = Color(0xFF003087), // نارنجی
                        onClick = onNavigateToSaipa,
                        logoContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_saipa),
                                contentDescription = "سایپا",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("brand_card_saipa")
                    )

                    // کارت سایر خودروها
                    BrandCardLight(
                        title = "سایر خودروها",
                        subtitle = "قطعات سایر برندهای خودرو",
                        iconColor = Color(0xFF003087), // سبز
                        onClick = onNavigateToOtherCars,
                        logoContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_car),
                                contentDescription = "سایر خودروها",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("brand_card_others")
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== بخش 3: آیکون‌های خدمات (پس‌زمینه سفید) =====
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureItemLight(icon = Icons.Default.HeadsetMic, title = "پشتیبانی 24/7")
                    FeatureItemLight(icon = Icons.Default.LocalShipping, title = "ارسال سریع")
                    FeatureItemLight(icon = Icons.Default.Star, title = "کیفیت تضمینی")
                    FeatureItemLight(icon = Icons.Default.Verified, title = "ضمانت اصالت")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BrandCardLight(
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit,
    logoContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                logoContent()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8E8E93)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "انتخاب",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun FeatureItemLight(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(72.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF003087))
                .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(12.dp)),
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
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            softWrap = true
        )
    }
}
