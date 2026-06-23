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
    // Force Persian RTL Direction for authentic localize Persian layout
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0B1220), // Dark background top
                            Color(0xFF142033), // Transition core background
                            Color(0xFF0B1220)  // Deep background bottom
                        )
                    )
                )
        ) {
            // Background line art of mechanical piston, gears and auto parts at the bottom
            BottomCarPartsDecoration(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .align(Alignment.BottomCenter)
            )

            // Scrollable view for the content to ensure clean UX across all screen sizes
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Modern 3D/Metallic generated Piston and Shield brand logo
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(36.dp))
                        .background(Color.White.copy(alpha = 0.02f))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF3B82F6).copy(alpha = 0.2f), // Elegant blue highlight rim
                            shape = RoundedCornerShape(36.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_car_parts_logo_1782171418216),
                        contentDescription = "همراه قطعات",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(36.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Persian Title
                Text(
                    text = "همراه قطعات",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Colored Center Accent Indicator bar
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(Color(0xFF3B82F6))
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Persian Subtitle
                Text(
                    text = "سامانه مدیریت و فروش قطعات خودرو",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFA7B1C2), // Secondary text color
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Selection Cards representing each brand, designed exactly as shown in the mockup:
                // under Persian RTL direction, the Brand Logo is on the right, title/desc in middle, and arrow on the left.
                
                // 1. IRAN KHODRO (Royal Blue premium theme with custom vector Horse shield and watermark)
                BrandCardMockup(
                    title = "ایران‌خودرو",
                    subtitle = "قطعات خودروهای ایران‌خودرو",
                    startColor = Color(0xFF0D2C54),
                    endColor = Color(0xFF0A2241),
                    borderColor = Color(0xFF243447),
                    onClick = onNavigateToIranKhodro,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_ikco),
                            contentDescription = "ایران‌خودرو",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    watermarkContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(96.dp)
                                .alpha(0.06f)
                                .offset(x = (-16).dp, y = 12.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_ikco),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_ikco")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // 2. SAIPA (Orange/Rust premium theme with custom vector wind star emblem and watermark)
                BrandCardMockup(
                    title = "سایپا",
                    subtitle = "قطعات خودروهای سایپا",
                    startColor = Color(0xFFC96A1A),
                    endColor = Color(0xFF9E5212),
                    borderColor = Color(0xFF243447),
                    onClick = onNavigateToSaipa,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_saipa),
                            contentDescription = "سایپا",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    watermarkContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(96.dp)
                                .alpha(0.06f)
                                .offset(x = (-16).dp, y = 12.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_saipa),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_saipa")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // 3. OTHER CARS (Forest Green premium theme with custom vector Front car outline and watermark)
                BrandCardMockup(
                    title = "سایر خودروها",
                    subtitle = "قطعات سایر برندهای خودرو",
                    startColor = Color(0xFF1F4D3A),
                    endColor = Color(0xFF173B2C),
                    borderColor = Color(0xFF243447),
                    onClick = onNavigateToOtherCars,
                    logoContent = {
                        Icon(
                            painter = painterResource(R.drawable.ic_car),
                            contentDescription = "سایر خودروها",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    watermarkContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(96.dp)
                                .alpha(0.06f)
                                .offset(x = (-16).dp, y = 12.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_car),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .testTag("brand_card_others")
                )

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun BrandCardMockup(
    title: String,
    subtitle: String,
    startColor: Color,
    endColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    logoContent: @Composable () -> Unit,
    watermarkContent: @Composable () -> Unit,
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
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(startColor, endColor)
                    )
                )
                .fillMaxSize()
        ) {
            // Elegant Watermark backdrop aligned to CenterEnd (left side in RTL mode)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
            ) {
                watermarkContent()
            }

            // Foreground Layout Content conforming to the precise RTL spec:
            // Under Persian CompositionLocalProvider (RTL), elements are laid out from Right to Left:
            // 1. Logo container (far right)
            // 2. Vertical split divider line
            // 3. Title column text (middle, stretched)
            // 4. Keyboard Left chevron arrow icon (far left)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rightmost part: Corporate brand logo
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(76.dp),
                    contentAlignment = Alignment.Center
                ) {
                    logoContent()
                }

                // Split divider line between brand logo and Persian details
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(0.45f)
                        .background(Color.White.copy(alpha = 0.12f))
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Middle part: Title & Subtitle detail
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

                // Leftmost part: Keyboard action navigation chevron
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "انتخاب",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun IkcoLogo(
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    forceDrawOutlineOnly: Boolean = false
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val mainTint = tint

        // Symmetrically calculated geometric outer shield of Iran Khodro
        val shieldPath = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            cubicTo(w * 0.88f, h * 0.12f, w * 0.96f, h * 0.32f, w * 0.96f, h * 0.52f)
            cubicTo(w * 0.96f, h * 0.78f, w * 0.72f, h * 0.92f, w * 0.5f, h * 0.92f)
            cubicTo(w * 0.28f, h * 0.92f, w * 0.04f, h * 0.78f, w * 0.04f, h * 0.52f)
            cubicTo(w * 0.04f, h * 0.32f, w * 0.12f, h * 0.12f, w * 0.5f, h * 0.12f)
            close()
        }

        if (forceDrawOutlineOnly) {
            drawPath(shieldPath, color = mainTint, style = Stroke(width = 1.5.dp.toPx()))
        } else {
            drawPath(shieldPath, color = mainTint.copy(alpha = 0.15f), style = Fill)
            drawPath(shieldPath, color = mainTint, style = Stroke(width = 1.5.dp.toPx()))
            
            // Stylized, highly accurate geometric Horse Head looking left (correct IKCO logo direction)
            val horsePath = Path().apply {
                // Nose/snout bridge pointing LEFT
                moveTo(w * 0.68f, h * 0.78f)
                lineTo(w * 0.68f, h * 0.54f)
                cubicTo(w * 0.68f, h * 0.42f, w * 0.60f, h * 0.26f, w * 0.46f, h * 0.26f) // Crest of the mane
                lineTo(w * 0.36f, h * 0.34f) // Ears / head top curve
                lineTo(w * 0.24f, h * 0.48f) // Nose bridge downward slope to left
                lineTo(w * 0.26f, h * 0.54f) // Snout front
                lineTo(w * 0.36f, h * 0.54f) // Mouth cut
                lineTo(w * 0.40f, h * 0.61f) // Jawline cut back
                lineTo(w * 0.34f, h * 0.78f) // Chest sweep
                close()
            }
            drawPath(horsePath, color = mainTint, style = Fill)
        }
    }
}

@Composable
fun SaipaLogo(
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val radius = Math.min(w, h) * 0.38f

        // High fidelity symmetrical three-pronged Saipa logo.
        // It consists of three congruent diamond blades rotated 120 degrees apart
        for (i in 0..2) {
            val angle = i * 120f
            withTransform({
                rotate(angle, Offset(cx, cy))
            }) {
                val bladePath = Path().apply {
                    moveTo(cx, cy - radius)
                    lineTo(cx - radius * 0.38f, cy - radius * 0.12f)
                    lineTo(cx, cy - radius * 0.28f)
                    lineTo(cx + radius * 0.38f, cy - radius * 0.12f)
                    close()
                }
                drawPath(path = bladePath, color = tint, style = Fill)
            }
        }
    }
}

@Composable
fun CarOutlineLogo(
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = 1.8.dp.toPx()
        val color = tint

        // Sleek flat modern sports vehicle face outline
        // Upper canopy (Windshield & Roof)
        val cabinPath = Path().apply {
            moveTo(w * 0.30f, h * 0.40f)
            lineTo(w * 0.70f, h * 0.40f)
            lineTo(w * 0.82f, h * 0.60f)
            lineTo(w * 0.18f, h * 0.60f)
            close()
        }
        drawPath(cabinPath, color = color, style = Stroke(width = strokeWidth))
        drawPath(cabinPath, color = color.copy(alpha = 0.15f), style = Fill)

        // Lower bumper chassis
        val bodyPath = Path().apply {
            moveTo(w * 0.18f, h * 0.60f)
            quadraticTo(w * 0.08f, h * 0.64f, w * 0.08f, h * 0.78f) // Bumper wing left
            lineTo(w * 0.92f, h * 0.78f)                           // Bottom
            quadraticTo(w * 0.92f, h * 0.64f, w * 0.82f, h * 0.60f) // Bumper wing right
            close()
        }
        drawPath(bodyPath, color = color, style = Stroke(width = strokeWidth))

        // Left Headlight
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.14f, h * 0.66f),
            size = Size(w * 0.15f, h * 0.05f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Fill
        )

        // Right Headlight
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.71f, h * 0.66f),
            size = Size(w * 0.15f, h * 0.05f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Fill
        )

        // Sleek Radiator grille center
        drawRoundRect(
            color = color.copy(alpha = 0.3f),
            topLeft = Offset(w * 0.36f, h * 0.67f),
            size = Size(w * 0.28f, h * 0.04f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Fill
        )
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.36f, h * 0.67f),
            size = Size(w * 0.28f, h * 0.04f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

@Composable
fun BottomCarPartsDecoration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val color = Color.White.copy(alpha = 0.06f) // Subtle background integration
        val strokeWidth = 1.2.dp.toPx()

        // 1. Left side item: Brake Disc rotor (centered around w * 0.10f, h * 0.55f)
        val r1 = 34.dp.toPx()
        val cx1 = w * 0.10f
        val cy1 = h * 0.55f
        drawCircle(color = color, radius = r1, center = Offset(cx1, cy1), style = Stroke(strokeWidth))
        drawCircle(color = color, radius = r1 * 0.52f, center = Offset(cx1, cy1), style = Stroke(strokeWidth))
        drawCircle(color = color, radius = r1 * 0.22f, center = Offset(cx1, cy1), style = Stroke(strokeWidth))
        // Vent holes for high-performance look
        for (a in 0 until 360 step 30) {
            val angleRad = Math.toRadians(a.toDouble())
            val hx = cx1 + Math.cos(angleRad).toFloat() * (r1 * 0.76f)
            val hy = cy1 + Math.sin(angleRad).toFloat() * (r1 * 0.76f)
            drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(hx, hy), style = Fill)
        }
        val caliperPath = Path().apply {
            moveTo(cx1 - r1 * 0.3f, cy1 - r1 - 3.dp.toPx())
            lineTo(cx1 + r1 * 0.75f, cy1 - r1 + 6.dp.toPx())
            lineTo(cx1 + r1 * 0.55f, cy1 - r1 * 0.55f)
            lineTo(cx1 - r1 * 0.4f, cy1 - r1 * 0.75f)
            close()
        }
        drawPath(caliperPath, color = color, style = Stroke(strokeWidth))

        // 2. Coil Suspension Spring (centered around w * 0.28f, h * 0.58f)
        val cx2 = w * 0.28f
        val cy2 = h * 0.58f
        val spW = 18.dp.toPx()
        val spH = 50.dp.toPx()
        drawLine(color = color, start = Offset(cx2, cy2 - spH * 0.6f), end = Offset(cx2, cy2 + spH * 0.6f), strokeWidth = strokeWidth)
        val coilsCount = 5
        val step = spH / coilsCount
        for (i in 0 until coilsCount) {
            val yStart = cy2 - spH * 0.5f + i * step
            val yEnd = yStart + step
            drawLine(color = color, start = Offset(cx2 - spW * 0.5f, yStart), end = Offset(cx2 + spW * 0.5f, yEnd), strokeWidth = strokeWidth * 2f)
        }

        // 3. Car battery (centered around w * 0.50f, h * 0.58f)
        val cx3 = w * 0.50f
        val cy3 = h * 0.58f
        val batW = 50.dp.toPx()
        val batH = 34.dp.toPx()
        drawRect(
            color = color,
            topLeft = Offset(cx3 - batW * 0.5f, cy3 - batH * 0.5f),
            size = Size(batW, batH),
            style = Stroke(strokeWidth)
        )
        // Battery Terminals
        drawRect(
            color = color,
            topLeft = Offset(cx3 - batW * 0.35f, cy3 - batH * 0.5f - 5.dp.toPx()),
            size = Size(8.dp.toPx(), 5.dp.toPx()),
            style = Stroke(strokeWidth)
        )
        drawRect(
            color = color,
            topLeft = Offset(cx3 + batW * 0.15f, cy3 - batH * 0.5f - 5.dp.toPx()),
            size = Size(8.dp.toPx(), 5.dp.toPx()),
            style = Stroke(strokeWidth)
        )
        // Indicator plus/minus glyphs
        drawLine(color = color, start = Offset(cx3 - batW * 0.25f - 3.dp.toPx(), cy3), end = Offset(cx3 - batW * 0.25f + 3.dp.toPx(), cy3), strokeWidth = strokeWidth)
        drawLine(color = color, start = Offset(cx3 - batW * 0.25f, cy3 - 3.dp.toPx()), end = Offset(cx3 - batW * 0.25f, cy3 + 3.dp.toPx()), strokeWidth = strokeWidth)
        drawLine(color = color, start = Offset(cx3 + batW * 0.25f - 3.dp.toPx(), cy3), end = Offset(cx3 + batW * 0.25f + 3.dp.toPx(), cy3), strokeWidth = strokeWidth)

        // 4. Tech Mechanical Gear (centered around w * 0.72f, h * 0.58f)
        val cx4 = w * 0.72f
        val cy4 = h * 0.58f
        val gearR = 26.dp.toPx()
        drawCircle(color = color, radius = gearR, center = Offset(cx4, cy4), style = Stroke(strokeWidth))
        drawCircle(color = color, radius = gearR * 0.4f, center = Offset(cx4, cy4), style = Stroke(strokeWidth))
        for (a in 0 until 360 step 45) {
            val angleRad = Math.toRadians(a.toDouble())
            val cos = Math.cos(angleRad).toFloat()
            val sin = Math.sin(angleRad).toFloat()
            drawLine(
                color = color,
                start = Offset(cx4 + cos * (gearR - 2.dp.toPx()), cy4 + sin * (gearR - 2.dp.toPx())),
                end = Offset(cx4 + cos * (gearR + 5.dp.toPx()), cy4 + sin * (gearR + 5.dp.toPx())),
                strokeWidth = strokeWidth * 2.5f
            )
        }

        // 5. Speedometer Gauge (centered around w * 0.90f, h * 0.56f)
        val cx5 = w * 0.90f
        val cy5 = h * 0.56f
        val gR = 24.dp.toPx()
        drawArc(
            color = color,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(cx5 - gR, cy5 - gR),
            size = Size(gR * 2, gR * 2),
            style = Stroke(strokeWidth)
        )
        val nAngleRad = Math.toRadians(235.0)
        drawLine(
            color = color,
            start = Offset(cx5, cy5),
            end = Offset(cx5 + Math.cos(nAngleRad).toFloat() * (gR * 0.75f), cy5 + Math.sin(nAngleRad).toFloat() * (gR * 0.75f)),
            strokeWidth = strokeWidth * 2
        )
    }
}
