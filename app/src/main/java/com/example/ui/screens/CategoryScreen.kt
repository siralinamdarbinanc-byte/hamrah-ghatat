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

// Common category class defined locally for self-contained usage
data class PartCategory(
    val id: Int,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val parts: List<String>
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
    
    // Luxury dark/ambient theme configuration tailored for each vehicle brand
    val modelName = getModelName(modelId)
    val themeConfig = remember(brandId) {
        when (brandId) {
            "saipa" -> {
                BrandTheme(
                    brandName = "سایپا",
                    subtitleText = "دسته بندی قطعات یدکی سایپا",
                    bgColors = listOf(
                        Color(0xFF0B1220), // Dark background top
                        Color(0xFF142033), // Transition core background
                        Color(0xFF0B1220)  // Deep background bottom
                    ),
                    accentColor = Color(0xFFC96A1A), // Saipa Rust Orange
                    highlightColor = Color(0xFFFF8A50), // Brighter amber orange for highlights
                    topBarColor = Color(0xFF0F172A)
                )
            }
            "other_cars" -> {
                BrandTheme(
                    brandName = "سایر خودروها",
                    subtitleText = "دسته بندی قطعات سایر خودروها",
                    bgColors = listOf(
                        Color(0xFF0B1220),
                        Color(0xFF142033),
                        Color(0xFF0B1220)
                    ),
                    accentColor = Color(0xFF1F4D3A), // Dark emerald green
                    highlightColor = Color(0xFF10B981), // Vibrant emerald for highlights
                    topBarColor = Color(0xFF0F172A)
                )
            }
            else -> { // "iran_khodro" as default/fallback
                BrandTheme(
                    brandName = "ایران‌خودرو",
                    subtitleText = "دسته بندی قطعات یدکی ایران‌خودرو",
                    bgColors = listOf(
                        Color(0xFF0B1220),
                        Color(0xFF142033),
                        Color(0xFF0B1220)
                    ),
                    accentColor = Color(0xFF0D2C54), // IKCO Royal Navy Blue
                    highlightColor = Color(0xFF3B82F6), // Vibrant blue for highlights
                    topBarColor = Color(0xFF0F172A)
                )
            }
        }
    }

    // Dynamic data lists corresponding to the selected brand
    val categories = remember(brandId) {
        listOf(
            PartCategory(
                id = 1,
                name = "موتور (Engine)",
                description = when (brandId) {
                    "saipa" -> "رینگ و پیستون M15، سرسیلندر، سوپاپ"
                    "other_cars" -> "رینگ و پیستون خودروهای وارداتی و لوکس"
                    else -> "رینگ و پیستون عظام، سرسیلندر کامل، تسمه تایم"
                },
                icon = Icons.Default.Settings,
                parts = when (brandId) {
                    "saipa" -> listOf("پیستون موتور M15 شاهین توربو", "مجموعه سرسیلندر تیبا یورو ۴", "تسمه تایم اصلی دانگیل پراید", "واشر سرسیلندر اورجینال ساینا", "سوپاپ دود و هوا کوییک سایپا یدک")
                    "other_cars" -> listOf("پیستون کامل هیوندای سانتافه ۲.۴", "تسمه تایم اصل میتسوبیشی اوتلندر", "واشر سرسیلندر جک S5 توربو شرکتی", "رینگ خودروهای ام‌وی‌ام ۳۱۵ چری", "صافی بنزین اصلی کیا اپتیما جی‌دی‌آی")
                    else -> listOf("رینگ و پیستون عظام (IKCO)", "سرسیلندر کامل ایساکو دنا", "تسمه تایم پاورگریپ سمند", "واشر سرسیلندر استاندارد پژو ۴۰۵", "اویل پمپ کامل موتور ملی EF7")
                }
            ),
            PartCategory(
                id = 2,
                name = "بدنه (Body Parts)",
                description = when (brandId) {
                    "saipa" -> "چراغ جلو شاهین، سپر، گلگیر ساینا"
                    "other_cars" -> "آینه بغل، چراغ جلو، سپر خودروهای خارجی"
                    else -> "چراغ دنا پلاس، سپر فابریک پارس، آینه تارا"
                },
                icon = Icons.Default.Home,
                parts = when (brandId) {
                    "saipa" -> listOf("چراغ جلو کوییک اس کروز چپ", "سپر جلو ساینا اس اصلی سفید رنگ", "درب صندوق عقب شاهین سایپا یدک", "آینه رو گلگیر چپ پراید ۱۳۱", "جلوپنجره ساینا طلایی مجهز")
                    "other_cars" -> listOf("چراغ جلو جک S5 شرکت کرمان موتور چپ", "سپر جلو مزدا ۳ نیو اصلی", "آینه بغل تاشو برقی هیوندای توسان راست", "شلگیر جلو تویوتا کرولا فابریک", "جلوپنجره نواری کیا اسپورتیج")
                    else -> listOf("چراغ جلو دنا پلاس کروز", "سپر جلو پژو پارس سفید مهرکام", "کاپوت جلو سمند آستر شده", "گلگیر جلو چپ پژو ۲۰۷ دکمه‌دار", "آینه بغل راهنمادار تارا چپ")
                }
            ),
            PartCategory(
                id = 3,
                name = "جلوبندی و تعلیق (Suspension)",
                description = when (brandId) {
                    "saipa" -> "کمک فنر عظام پراید، کمک عقب شاهین"
                    "other_cars" -> "کمک فنر گازی، بوش فرمان خودرو کار"
                    else -> "کمک فنر جلو ۲۰۶ عظام، بوش طبق ۴۰۵"
                },
                icon = Icons.Default.Build,
                parts = when (brandId) {
                    "saipa" -> listOf("کمک فنر جلو پراید مارک عظام", "سیبک فرمان ساینا لاهیجان", "جعبه فرمان هیدرولیک تیبا سایپایدک", "جعبه فرمان تقویت شده کوییک دنده‌ای", "کمک فنر عقب شاهین عظام")
                    "other_cars" -> listOf("کمک فنر گازی جلو کیا سراتو KYB", "سیبک فرمان راست هیوندای سوناتا کره", "طبق کامل راست سانتافه ایساکو وارداتی", "بوش جناقی بزرگ تویوتا کمری ژاپن", "بلبرینگ چرخ جلو جک J5 اصلی")
                    else -> listOf("کمک فنر جلو پژو ۲۰۶ عظام", "سیبک طبق راست امیرنیا", "بوش طبق لبه‌دار بزرگ پژو ۴۰۵", "پلوس کامل چپ سمند ۲۲ خار", "طبق کامل راست تارا ایساکو")
                }
            ),
            PartCategory(
                id = 4,
                name = "برق و ECU (Electronics)",
                description = when (brandId) {
                    "saipa" -> "ایسیو کروز ساینا، کویل دوبل زیمنس مدل کوییک"
                    "other_cars" -> "سنسور اکسیژن، شمع سوزنی، کویل چری"
                    else -> "ایسیو زیمنس کروز، کویل دوبل رانا، سنسور اکسیژن"
                },
                icon = Icons.Default.Warning,
                parts = when (brandId) {
                    "saipa" -> listOf("ایسیو کروز ساینا یورو ۵", "دینام پراید ۹۰ آمپر عظام", "سنسور اکسیژن زیمنس ساینا شرکت سایپا", "کویل دوبل زیمنس مدل کوییک دنده‌ای", "استارت کامل تیبا مگنتی")
                    "other_cars" -> listOf("کوئل اصلی موتور چری تیگو ۵", "سنسور اکسیژن تویوتا پرادو بوش اصل", "کیت پارک سنسور هیوندای جنسیس سدان", "دینام کره ای کیا سورنتو اورجینال", "شمع سوزنی پلاتینیوم ان‌جی‌کی ژاپن ۴ عددی")
                    else -> listOf("ایسیو زیمنس شبکه کروز (فابریک)", "کویل دوبل رانا موتور TU5", "سنسور اکسیژن زیمنس سیم‌بلند", "کروز کنترل کامل دنا پلاس غربیلک جدید", "شمع سوزنی اکیوم اریجینال ۴ عددی")
                }
            ),
            PartCategory(
                id = 5,
                name = "گیربکس و انتقال قدرت (Gearbox)",
                description = when (brandId) {
                    "saipa" -> "کیت کلاچ والئو ترک شاهین، صفحه پراید سکو"
                    "other_cars" -> "فیلتر گیربکس اتومات، دیسک جک دنده‌ای"
                    else -> "کیت کلاچ والئو پژو، دنده برنجی دنا"
                },
                icon = Icons.Default.Menu,
                parts = when (brandId) {
                    "saipa" -> listOf("کیت کلاچ شاهین والئو ترک درجه یک", "دیسک و صفحه کلاچ پراید سکو کره اورجینال", "پوسته گیربکس کوییک دنده‌ای شرکت سایپا", "دنده برنجی ۵ پراید پایا", "بلبرینگ کلاچ تیبا کره")
                    "other_cars" -> listOf("کیت روغن و فیلتر گیربکس اتوماتیک AL4", "دیسک و صفحه کلاچ جک J5 دنده‌ای سیکو", "ساعت کامل گیربکس هیوندای آزرا ۳.۳", "پلوس کامل سمت چپ ام‌وی‌ام X22 دنده‌ای", "شیر برقی گیربکس اتوماتیک پژو ۲۰۶ و مگان")
                    else -> listOf("کیت کلاچ والئو سبزرنگ اصلی پری‌دمپر", "پوسته گیربکس پژو ۴۰۵ ایساکو", "دنده برنجی گیربکس BE4 دنا", "کشویی دنده ۱ و ۲ سمند", "بلبرینگ کلاچ اصلی موتور EF7")
                }
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<PartCategory?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Home, 1: Cart, 2: Profile

    // Filter categories based on search input
    val filteredCategories = remember(searchQuery, categories) {
        if (searchQuery.isBlank()) {
            categories
        } else {
            categories.filter { category ->
                category.name.contains(searchQuery, ignoreCase = true) ||
                category.description.contains(searchQuery, ignoreCase = true) ||
                category.parts.any { part -> part.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    // Force RTL local provider to render Persian text correctly
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
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.testTag("dynamic_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = themeConfig.topBarColor
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                Surface(
                    color = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    tonalElevation = 12.dp
                ) {
                    Row(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .fillMaxWidth()
                            .height(76.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Home Tab
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedTab = 0 }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 44.dp, height = 28.dp)
                                    .background(
                                        if (selectedTab == 0) themeConfig.accentColor.copy(alpha = 0.25f) else Color.Transparent,
                                        shape = RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "خانه",
                                    tint = if (selectedTab == 0) themeConfig.accentColor else Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "خانه",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == 0) themeConfig.accentColor else Color.White.copy(alpha = 0.5f)
                            )
                        }

                        // Cart Tab
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTab = 1
                                    Toast.makeText(context, "سبد خرید شما فعلاً خالی است", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 44.dp, height = 28.dp)
                                    .background(
                                        if (selectedTab == 1) themeConfig.accentColor.copy(alpha = 0.25f) else Color.Transparent,
                                        shape = RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "سبد خرید",
                                    tint = if (selectedTab == 1) themeConfig.accentColor else Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "سبد خرید",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == 1) themeConfig.accentColor else Color.White.copy(alpha = 0.5f)
                            )
                        }

                        // Profile Tab
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTab = 2
                                    Toast.makeText(context, "پروفایل به زودی فعال خواهد شد", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 44.dp, height = 28.dp)
                                    .background(
                                        if (selectedTab == 2) themeConfig.accentColor.copy(alpha = 0.25f) else Color.Transparent,
                                        shape = RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "پروفایل",
                                    tint = if (selectedTab == 2) themeConfig.accentColor else Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "پروفایل",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedTab == 2) themeConfig.accentColor else Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = themeConfig.bgColors
                        )
                    )
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Outlined styled Modern Textfield
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 4.dp)
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = {
                                    Text(
                                        text = "جستجوی قطعه، برند یا دسته...",
                                        color = Color.White.copy(alpha = 0.45f),
                                        fontSize = 14.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "جستجو",
                                        tint = Color.White.copy(alpha = 0.45f)
                                    )
                                },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { searchQuery = "" }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "پاک کردن",
                                                tint = Color.White.copy(alpha = 0.8f)
                                            )
                                        }
                                    }
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.White.copy(alpha = 0.04f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.02f),
                                    focusedBorderColor = themeConfig.accentColor.copy(alpha = 0.6f),
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.12f),
                                    cursorColor = themeConfig.accentColor
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("parts_search_input_dynamic")
                            )
                        }
                    }

                    // Content Title
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "دسته بندی های قطعات",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            if (searchQuery.isNotEmpty()) {
                                Text(
                                    text = "نتایج یافت شده: ${filteredCategories.size}",
                                    fontSize = 12.sp,
                                    color = themeConfig.accentColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Handle Empty state
                    if (filteredCategories.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 60.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(Color.White.copy(alpha = 0.04f), CircleShape)
                                        .border(1.dp, Color.White.copy(alpha = 0.08f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "یافت نشد",
                                        tint = themeConfig.accentColor.copy(alpha = 0.8f),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(18.dp))
                                Text(
                                    text = "قطعه یا گروهی یافت نشد!",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "لطفاً عبارت دیگری را جستجو نمایید.",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.5f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(filteredCategories) { category ->
                            DynamicCategoryCard(
                                category = category,
                                accentColor = themeConfig.highlightColor,
                                onClick = { selectedCategory = category }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                // Premium Dynamic Bottom Sheet List Dialouge
                if (selectedCategory != null) {
                    val currentCategory = selectedCategory!!
                    ModalBottomSheet(
                        onDismissRequest = { selectedCategory = null },
                        containerColor = Color(0xFF142033), // Match premium surface background in spec
                        contentColor = Color.White,
                        tonalElevation = 16.dp,
                        modifier = Modifier.testTag("parts_detail_sheet_dynamic")
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
                                    text = currentCategory.name,
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
                                        text = "تعداد: ${currentCategory.parts.size}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = themeConfig.highlightColor,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "لیست قطعات تخصصی اورجینال موجود در فروشگاه همراه:",
                                fontSize = 13.sp,
                                color = Color(0xFFA7B1C2) // Secondary text color
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            currentCategory.parts.forEachIndexed { index, part ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(14.dp))
                                        .border(1.dp, Color(0xFF243447), RoundedCornerShape(14.dp)) // Spec border color
                                        .clickable {
                                            Toast.makeText(context, "$part به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show()
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
                                    Spacer(modifier = Modifier.width(14.dp))
                                    Text(
                                        text = part,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = {
                                            Toast.makeText(context, "$part به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "افزودن به سبد خرید",
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

@Composable
fun CategoryPartIcon(
    categoryId: Int,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = 1.8.dp.toPx()
        val c = tint

        when (categoryId) {
            1 -> { // Engine (موتور)
                // Draw a beautiful custom engine block check engine outline
                val p = Path().apply {
                    moveTo(w * 0.25f, h * 0.40f)
                    lineTo(w * 0.42f, h * 0.40f)
                    lineTo(w * 0.42f, h * 0.28f)
                    lineTo(w * 0.58f, h * 0.28f)
                    lineTo(w * 0.58f, h * 0.40f)
                    lineTo(w * 0.75f, h * 0.40f)
                    lineTo(w * 0.85f, h * 0.50f)
                    lineTo(w * 0.85f, h * 0.72f)
                    lineTo(w * 0.65f, h * 0.72f)
                    lineTo(w * 0.65f, h * 0.82f)
                    lineTo(w * 0.35f, h * 0.82f)
                    lineTo(w * 0.35f, h * 0.72f)
                    lineTo(w * 0.15f, h * 0.72f)
                    lineTo(w * 0.15f, h * 0.50f)
                    close()
                }
                drawPath(p, color = c, style = Stroke(width = strokeWidth))
                // Detail indicators inside
                drawCircle(color = c, radius = 3.5.dp.toPx(), center = Offset(w * 0.35f, h * 0.58f), style = Stroke(strokeWidth))
                drawCircle(color = c, radius = 2.5.dp.toPx(), center = Offset(w * 0.52f, h * 0.62f), style = Stroke(strokeWidth))
            }
            2 -> { // Body Parts (بدنه)
                // Elegant car outline
                val p = Path().apply {
                    moveTo(w * 0.15f, h * 0.58f)
                    quadraticTo(w * 0.15f, h * 0.46f, w * 0.28f, h * 0.44f)
                    lineTo(w * 0.42f, h * 0.34f)
                    lineTo(w * 0.68f, h * 0.34f)
                    lineTo(w * 0.82f, h * 0.46f)
                    quadraticTo(w * 0.88f, h * 0.48f, w * 0.88f, h * 0.58f)
                    lineTo(w * 0.85f, h * 0.68f)
                    lineTo(w * 0.76f, h * 0.68f)
                    // Wheel cut 1
                    quadraticTo(w * 0.72f, h * 0.60f, w * 0.64f, h * 0.68f)
                    lineTo(w * 0.38f, h * 0.68f)
                    // Wheel cut 2
                    quadraticTo(w * 0.34f, h * 0.60f, w * 0.26f, h * 0.68f)
                    lineTo(w * 0.15f, h * 0.68f)
                    close()
                }
                drawPath(p, color = c, style = Stroke(width = strokeWidth))
                // Wheels
                drawCircle(color = c, radius = 4.5.dp.toPx(), center = Offset(w * 0.30f, h * 0.68f), style = Stroke(strokeWidth))
                drawCircle(color = c, radius = 4.5.dp.toPx(), center = Offset(w * 0.68f, h * 0.68f), style = Stroke(strokeWidth))
            }
            3 -> { // Suspension (جلوبندی)
                // Shock absorber shaft
                drawLine(color = c, start = Offset(w * 0.50f, h * 0.18f), end = Offset(w * 0.50f, h * 0.82f), strokeWidth = strokeWidth * 1.5f)
                // Top cap
                drawRoundRect(color = c, topLeft = Offset(w * 0.35f, h * 0.18f), size = Size(w * 0.30f, h * 0.08f), cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()))
                // Coil Spring spiral overlapping zig-zag
                val startY = h * 0.28f
                val endY = h * 0.72f
                val coils = 4
                val step = (endY - startY) / coils
                for (i in 0 until coils) {
                    val y1 = startY + i * step
                    val y2 = y1 + step
                    // Draw outer loop
                    drawLine(color = c, start = Offset(w * 0.30f, y1), end = Offset(w * 0.70f, y1 + step * 0.5f), strokeWidth = strokeWidth * 1.8f)
                    drawLine(color = c, start = Offset(w * 0.70f, y1 + step * 0.5f), end = Offset(w * 0.30f, y2), strokeWidth = strokeWidth * 1.8f)
                }
                // Bottom attachment hole
                drawCircle(color = c, radius = 4.dp.toPx(), center = Offset(w * 0.50f, h * 0.84f), style = Stroke(strokeWidth))
            }
            4 -> { // Electronics (برق و ECU)
                // Battery case
                drawRect(
                    color = c,
                    topLeft = Offset(w * 0.25f, h * 0.34f),
                    size = Size(w * 0.50f, h * 0.46f),
                    style = Stroke(strokeWidth)
                )
                // Left terminal
                drawRect(
                    color = c,
                    topLeft = Offset(w * 0.32f, h * 0.26f),
                    size = Size(w * 0.10f, h * 0.08f)
                )
                // Right terminal
                drawRect(
                    color = c,
                    topLeft = Offset(w * 0.58f, h * 0.26f),
                    size = Size(w * 0.10f, h * 0.08f)
                )
                // Lightning bolt in center
                val boltPath = Path().apply {
                    moveTo(w * 0.54f, h * 0.42f)
                    lineTo(w * 0.42f, h * 0.56f)
                    lineTo(w * 0.49f, h * 0.56f)
                    lineTo(w * 0.46f, h * 0.72f)
                    lineTo(w * 0.58f, h * 0.52f)
                    lineTo(w * 0.51f, h * 0.52f)
                    close()
                }
                drawPath(boltPath, color = c, style = Fill)
            }
            5 -> { // Gearbox (گیربکس)
                // Outer gear cogwheel
                val cx = w / 2f
                val cy = h / 2f
                val r = w * 0.26f
                drawCircle(color = c, radius = r, center = Offset(cx, cy), style = Stroke(strokeWidth))
                drawCircle(color = c, radius = r * 0.45f, center = Offset(cx, cy), style = Stroke(strokeWidth))
                // Teeth
                for (a in 0 until 360 step 60) {
                    val angleRad = Math.toRadians(a.toDouble())
                    val cos = Math.cos(angleRad).toFloat()
                    val sin = Math.sin(angleRad).toFloat()
                    drawLine(
                        color = c,
                        start = Offset(cx + cos * (r - 1.dp.toPx()), cy + sin * (r - 1.dp.toPx())),
                        end = Offset(cx + cos * (r + 4.dp.toPx()), cy + sin * (r + 4.dp.toPx())),
                        strokeWidth = strokeWidth * 2.2f
                    )
                }
                // Central stick diagram
                drawLine(color = c, start = Offset(cx, cy), end = Offset(cx + w * 0.24f, cy - h * 0.24f), strokeWidth = strokeWidth * 1.5f)
                drawCircle(color = c, radius = 3.5.dp.toPx(), center = Offset(cx + w * 0.24f, cy - h * 0.24f), style = Fill)
            }
        }
    }
}

@Composable
fun DynamicCategoryCard(
    category: PartCategory,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Color(0xFF243447), // Elegant border color matching the mockup spec
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B).copy(alpha = 0.2f) // Beautiful slate dark container
        )
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CategoryPartIcon(
                            categoryId = category.id,
                            tint = accentColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = category.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category.description,
                        fontSize = 12.sp,
                        color = Color(0xFFA7B1C2), // Secondary text color
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Arrow Left points left under RTL (meaning to advance deeper)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "جزییات",
                tint = Color.White.copy(alpha = 0.35f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

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
        else -> "قطعات خودرو"
    }
}

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
        else -> "قطعات خودرو"
    }
}

private data class BrandTheme(
    val brandName: String,
    val subtitleText: String,
    val bgColors: List<Color>,
    val accentColor: Color,
    val highlightColor: Color,
    val topBarColor: Color
)
