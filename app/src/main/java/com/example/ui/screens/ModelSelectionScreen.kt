package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CarModel
import com.example.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

fun getBrandTitle(brandId: String): String {
    return when (brandId) {
        "iran_khodro" -> "ایران‌خودرو"
        "saipa" -> "سایپا"
        else -> "سایر خودروها"
    }
}

fun getBrandColor(brandId: String): Color {
    return when (brandId) {
        "iran_khodro" -> Color(0xFF0D2C54)
        "saipa" -> Color(0xFFC96A1A)
        else -> Color(0xFF1F4D3A)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSelectionScreen(
    brandId: String,
    onNavigateBack: () -> Unit,
    onNavigateToCategory: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val brandTitle = getBrandTitle(brandId)
    val brandColor = getBrandColor(brandId)
    
    val firebaseRepo = remember { FirebaseRepository() }
    val scope = rememberCoroutineScope()
    
    var models by remember { mutableStateOf<List<CarModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(brandId) {
        isLoading = true
        error = null
        try {
            models = firebaseRepo.getModels(brandId)
        } catch (e: Exception) {
            error = "خطا در بارگذاری مدل‌ها: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0B1220),
                            Color(0xFF142033),
                            Color(0xFF0B1220)
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brandColor.copy(alpha = 0.3f))
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "برگشت",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "انتخاب مدل - $brandTitle",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Accent line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(brandColor)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Content
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = brandColor)
                        }
                    }
                    error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = error ?: "خطای ناشناخته",
                                    color = Color.Red,
                                    fontSize = 14.sp
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            isLoading = true
                                            error = null
                                            try {
                                                models = firebaseRepo.getModels(brandId)
                                            } catch (e: Exception) {
                                                error = "خطا در بارگذاری مدل‌ها: ${e.message}"
                                            } finally {
                                                isLoading = false
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = brandColor)
                                ) {
                                    Text("تلاش مجدد")
                                }
                            }
                        }
                    }
                    models.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "مدلی یافت نشد",
                                color = Color(0xFFA7B1C2),
                                fontSize = 16.sp
                            )
                        }
                    }
                    else -> {
                        // Models List
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                            items(models) { model ->
                                ModelCard(
                                    model = model,
                                    brandColor = brandColor,
                                    onClick = {
                                        onNavigateToCategory(brandId, model.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModelCard(
    model: CarModel,
    brandColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, Color(0xFF243447), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2D40))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Colored accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(brandColor)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (model.year.isNotEmpty()) {
                    Text(
                        text = model.year,
                        fontSize = 12.sp,
                        color = Color(0xFFA7B1C2)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
