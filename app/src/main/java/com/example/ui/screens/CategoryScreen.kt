package com.example.ui.screens


import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.Category
import com.example.data.repository.FirebaseRepository
import com.example.data.repository.Part
import kotlinx.coroutines.launch

private data class BrandTheme(
    val brandName: String,
    val subtitleText: String,
    val bgColors: List<Color>,
    val accentColor: Color,
    val highlightColor: Color,
    val topBarColor: Color
)

fun getModelName(modelId: String): String {
    return when (modelId) {
        "peugeot_pars" -> "پژو پارس"
        "samand" -> "سمند"
        "dena" -> "دنا"
        "rana" -> "رانا"
        "peugeot_206" -> "پژو ۲۰۶"
        "peugeot_405" -> "پژو ۴۰۵"
        "haima" -> "هایما S5"
        "tara" -> "تارا"
        "tondar" -> "تندر ۹۰"
        "xantia" -> "زانتیا"
        "pride" -> "پراید"
        "tiba" -> "تیبا"
        "saina" -> "ساینا"
        "shahin" -> "شاهین"
        "quick" -> "کوییک"
        "atlas" -> "اطلس"
        "other" -> "سایر مدل‌ها"
        else -> "قطعات خودرو"
    }
}

// داده‌های نمایش برای هر دسته (ترکیب Category از Firebase + قطعات)
data class CategoryWithParts(
    val category: Category,
    val parts: List<Part>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    brandId: String,
    modelId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = remember { FirebaseRepository() }
    val scope = rememberCoroutineScope()

    val modelName = getModelName(modelId)

    val themeConfig = remember(brandId) {
        when (brandId) {
            "saipa" -> BrandTheme(
                brandName = "سایپا",
                subtitleText = "دسته بندی قطعات یدکی سایپا",
                bgColors = listOf(Color(0xFF0B1220), Color(0xFF142033), Color(0xFF0B1220)),
                accentColor = Color(0xFFC96A1A),
                highlightColor = Color(0xFFFF8A50),
                topBarColor = Color(0xFF0F172A)
            )
            "other_cars" -> BrandTheme(
                brandName = "سایر خودروها",
                subtitleText = "دسته بندی قطعات سایر خودروها",
                bgColors = listOf(Color(0xFF0B1220), Color(0xFF142033), Color(0xFF0B1220)),
                accentColor = Color(0xFF1F4D3A),
                highlightColor = Color(0xFF10B981),
                topBarColor = Color(0xFF0F172A)
            )
            else -> BrandTheme(
                brandName = "ایران‌خودرو",
                subtitleText = "دسته بندی قطعات یدکی ایران‌خودرو",
                bgColors = listOf(Color(0xFF0B1220), Color(0xFF142033), Color(0xFF0B1220)),
                accentColor = Color(0xFF0D2C54),
                highlightColor = Color(0xFF3B82F6),
                topBarColor = Color(0xFF0F172A)
            )
        }
    }

    // State
    var isLoading by remember { mutableStateOf(true) }
    var categoriesWithParts by remember { mutableStateOf<List<CategoryWithParts>>(emptyList()) }
    var selectedCategoryWithParts by remember { mutableStateOf<CategoryWithParts?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    // بارگذاری دسته‌ها و قطعات از Firebase
    LaunchedEffect(brandId, modelId) {
        isLoading = true
        try {
            val cats = repository.getCategories(brandId, modelId)
            val result = cats.map { cat ->
                val parts = repository.getParts(brandId, modelId, cat.id)
                CategoryWithParts(category = cat, parts = parts)
            }
            categoriesWithParts = result
        } catch (e: Exception) {
            Toast.makeText(context, "خطا در بارگذاری اطلاعات", Toast.LENGTH_SHORT).show()
        }
        isLoading = false
    }

    val filteredCategories = remember(searchQuery, categoriesWithParts) {
        if (searchQuery.isBlank()) {
            categoriesWithParts
        } else {
            categoriesWithParts.filter { cwp ->
                cwp.category.name.contains(searchQuery, ignoreCase = true) ||
                cwp.parts.any { it.name.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = modelName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "دسته بندی قطعات یدکی",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = themeConfig.topBarColor,
                        titleContentColor = Color.White
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(colors = themeConfig.bgColors)
                    )
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                color = themeConfig.highlightColor,
                                strokeWidth = 3.dp
                            )
                        }
                    }

                    categoriesWithParts.isEmpty() -> {
                        // Firebase خالیه — داده seed نشده
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = themeConfig.highlightColor,
                                    modifier = Modifier.size(48.dp)
                                )
                                Text(
                                    text = "اطلاعاتی یافت نشد",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "لطفاً اتصال اینترنت را بررسی کنید یا دوباره امتحان کنید",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.5f),
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            isLoading = true
                                            try {
                                                val cats = repository.getCategories(brandId, modelId)
                                                categoriesWithParts = cats.map { cat ->
                                                    CategoryWithParts(cat, repository.getParts(brandId, modelId, cat.id))
                                                }
                                            } catch (e: Exception) { }
                                            isLoading = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = themeConfig.highlightColor)
                                ) {
                                    Text("تلاش مجدد", color = Color.White)
                                }
                            }
                        }
                    }

                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // جستجو
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = {
                                    Text(
                                        "جستجوی قطعه یا دسته‌بندی...",
                                        color = Color.White.copy(alpha = 0.4f),
                                        fontSize = 14.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = null, tint = themeConfig.highlightColor)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = themeConfig.highlightColor,
                                    unfocusedBorderColor = Color(0xFF243447),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = themeConfig.highlightColor
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                singleLine = true
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                contentPadding = PaddingValues(bottom = 80.dp)
                            ) {
                                if (filteredCategories.isEmpty()) {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 60.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = themeConfig.accentColor.copy(alpha = 0.8f),
                                                modifier = Modifier.size(36.dp)
                                            )
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Text(
                                                text = "نتیجه‌ای یافت نشد",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                } else {
                                    items(filteredCategories, key = { it.category.id }) { cwp ->
                                        FirebaseCategoryCard(
                                            categoryWithParts = cwp,
                                            accentColor = themeConfig.highlightColor,
                                            onClick = { selectedCategoryWithParts = cwp }
                                        )
                                    }
                                }

                                item { Spacer(modifier = Modifier.height(16.dp)) }
                            }
                        }
                    }
                }

                // Bottom Sheet قطعات
                if (selectedCategoryWithParts != null) {
                    val current = selectedCategoryWithParts!!
                    ModalBottomSheet(
                        onDismissRequest = { selectedCategoryWithParts = null },
                        containerColor = Color(0xFF142033),
                        contentColor = Color.White,
                        tonalElevation = 16.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 40.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = current.category.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Surface(
                                    color = themeConfig.highlightColor.copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, themeConfig.highlightColor.copy(alpha = 0.3f)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = "تعداد: ${current.parts.size}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = themeConfig.highlightColor,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (current.parts.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "قطعه‌ای در این دسته ثبت نشده است",
                                        fontSize = 14.sp,
                                        color = Color.White.copy(alpha = 0.5f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                Text(
                                    text = "لیست قطعات موجود در فروشگاه:",
                                    fontSize = 13.sp,
                                    color = Color(0xFFA7B1C2)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                current.parts.forEachIndexed { index, part ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp)
                                            .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(14.dp))
                                            .border(1.dp, Color(0xFF243447), RoundedCornerShape(14.dp))
                                            .clickable {
                                                Toast.makeText(context, "${part.name} به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show()
                                            }
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            color = themeConfig.highlightColor.copy(alpha = 0.15f),
                                            modifier = Modifier.size(28.dp),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = "${index + 1}",
                                                    color = themeConfig.highlightColor,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = part.name,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color.White
                                            )
                                            if (part.price > 0) {
                                                Text(
                                                    text = "${part.price.formatPrice()} تومان",
                                                    fontSize = 12.sp,
                                                    color = themeConfig.highlightColor
                                                )
                                            }
                                        }
                                        IconButton(
                                            onClick = {
                                                Toast.makeText(context, "${part.name} به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "افزودن",
                                                tint = themeConfig.highlightColor,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirebaseCategoryCard(
    categoryWithParts: CategoryWithParts,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .border(1.dp, Color(0xFF243447), RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    color = accentColor.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = getCategoryIcon(categoryWithParts.category.id),
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = categoryWithParts.category.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "${categoryWithParts.parts.size} قطعه موجود",
                        fontSize = 12.sp,
                        color = Color(0xFFA7B1C2)
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.35f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

fun getCategoryIcon(categoryId: String): ImageVector {
    return when (categoryId) {
        "engine" -> Icons.Default.Settings
        "body" -> Icons.Default.Home
        "suspension" -> Icons.Default.Build
        "electrical" -> Icons.Default.Star
        "gearbox" -> Icons.Default.Menu
        else -> Icons.Default.List
    }
}
