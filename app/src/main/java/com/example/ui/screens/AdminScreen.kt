package com.example.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.example.data.DataSeeder
import com.example.data.repository.Brand
import com.example.data.repository.CarModel
import com.example.data.repository.Category
import com.example.data.repository.FirebaseRepository
import com.example.data.repository.Part
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val repository = remember { FirebaseRepository() }
    val scope = rememberCoroutineScope()

    var selectedBrand by remember { mutableStateOf<Brand?>(null) }
    var selectedModel by remember { mutableStateOf<CarModel?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var models by remember { mutableStateOf<List<CarModel>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var parts by remember { mutableStateOf<List<Part>>(emptyList()) }

    var isLoading by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf("brands") }

    // Seed dialog state
    var showSeedDialog by remember { mutableStateOf(false) }
    var showCleanDialog by remember { mutableStateOf(false) }
    var isExporting by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isCleanLoading by remember { mutableStateOf(false) }
    var cleanProgress by remember { mutableStateOf("") }
    var cleanDone by remember { mutableStateOf(false) }
    var cleanCount by remember { mutableStateOf(0) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editingBrand by remember { mutableStateOf<Brand?>(null) }
    var editingModel by remember { mutableStateOf<CarModel?>(null) }
    var editingCategory by remember { mutableStateOf<Category?>(null) }
    var editingPart by remember { mutableStateOf<Part?>(null) }
    var isSeedLoading by remember { mutableStateOf(false) }
    var seedProgress by remember { mutableStateOf("") }
    var seedDone by remember { mutableStateOf(false) }
    var seedCount by remember { mutableStateOf(0) }
    var seedTotal by remember { mutableStateOf(0) }
    var seedPercent by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        isLoading = true
        brands = repository.getBrands()
        isLoading = false
    }

    LaunchedEffect(selectedBrand) {
        selectedBrand?.let {
            models = repository.getModels(it.id)
        }
    }

    LaunchedEffect(selectedModel) {
        selectedModel?.let { model ->
            selectedBrand?.let { brand ->
                categories = repository.getCategories(brand.id, model.id)
            }
        }
    }

    LaunchedEffect(selectedCategory) {
        selectedCategory?.let { cat ->
            selectedBrand?.let { brand ->
                selectedModel?.let { model ->
                    parts = repository.getParts(brand.id, model.id, cat.id)
                }
            }
        }
    }

    // Seed dialog
    if (showSeedDialog) {
        AlertDialog(
            onDismissRequest = { if (!isSeedLoading) showSeedDialog = false },
            containerColor = Color(0xFF142033),
            title = {
                Text(
                    text = if (seedDone) "✅ راه‌اندازی تمام شد" else "راه‌اندازی اولیه",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    when {
                        seedDone -> Text(
                            text = "$seedCount قطعه با موفقیت وارد Firebase شد.\nاکنون می‌توانید از اپ استفاده کنید.",
                            color = Color(0xFFA7B1C2),
                            fontSize = 14.sp
                        )
                        isSeedLoading -> {
                            LinearProgressIndicator(
                                progress = { seedPercent },
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFF3B82F6),
                                trackColor = Color(0xFF243447)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = seedProgress.ifEmpty { "در حال وارد کردن..." },
                                    color = Color(0xFFA7B1C2),
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "${(seedPercent * 100).toInt()}%",
                                    color = Color(0xFF3B82F6),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        else -> Text(
                            text = "این عملیات تمام برندها، مدل‌ها، دسته‌بندی‌ها و قطعات پیش‌فرض را وارد Firebase می‌کند.\n\nشامل ۱۳ مدل:\nپراید، تیبا، ساینا، شاهین، کوییک\nپژو ۴۰۵، پارس، سمند، دنا، ۲۰۶\nتویوتا کمری، کیا پراید، هیوندای سوناتا\n\nحدود ۵۰۰ قطعه.",
                            color = Color(0xFFA7B1C2),
                            fontSize = 13.sp
                        )
                    }
                }
            },
            confirmButton = {
                when {
                    seedDone -> Button(
                        onClick = {
                            showSeedDialog = false
                            seedDone = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("بستن", color = Color.White)
                    }
                    !isSeedLoading -> Button(
                        onClick = {
                            isSeedLoading = true
                            seedPercent = 0f
                            seedTotal = 0
                            scope.launch {
                                try {
                                    val count = DataSeeder.forceReseed { progress, current, total ->
                                        seedProgress = progress
                                        seedTotal = total
                                        seedPercent = if (total > 0) current.toFloat() / total else 0f
                                    }
                                    seedCount = count
                                    seedPercent = 1f
                                    seedDone = true
                                    brands = repository.getBrands()
                                } catch (e: Exception) {
                                    seedProgress = "خطا: ${e.message}"
                                    isSeedLoading = false
                                }
                                isSeedLoading = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("شروع راه‌اندازی", color = Color.White)
                    }
                }
            },
            dismissButton = {
                if (!isSeedLoading && !seedDone) {
                    TextButton(onClick = { showSeedDialog = false }) {
                        Text("انصراف", color = Color(0xFFA7B1C2))
                    }
                }
            }
        )
    }

    if (showCleanDialog) {
        AlertDialog(
            onDismissRequest = { if (!isCleanLoading) showCleanDialog = false },
            containerColor = Color(0xFF142033),
            title = {
                Text(
                    text = if (cleanDone) "✅ پاکسازی تمام شد" else "پاکسازی داده‌های قدیمی",
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    when {
                        cleanDone -> Text(
                            "$cleanCount قطعه قدیمی حذف شد.",
                            color = Color(0xFFA7B1C2), fontSize = 14.sp
                        )
                        isCleanLoading -> {
                            CircularProgressIndicator(color = Color(0xFF3B82F6), modifier = Modifier.align(Alignment.CenterHorizontally))
                            Text(cleanProgress, color = Color(0xFFA7B1C2), fontSize = 13.sp)
                        }
                        else -> Text(
                            "این عملیات قطعاتی که با ID رندوم (قدیمی) ذخیره شدن رو حذف می‌کنه و فقط قطعات با ID ثابت باقی می‌مونن.",
                            color = Color(0xFFA7B1C2), fontSize = 13.sp
                        )
                    }
                }
            },
            confirmButton = {
                when {
                    cleanDone -> Button(
                        onClick = { showCleanDialog = false; cleanDone = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) { Text("بستن", color = Color.White) }
                    !isCleanLoading -> Button(
                        onClick = {
                            isCleanLoading = true
                            scope.launch {
                                try {
                                    val count = DataSeeder.cleanLegacyParts { p -> cleanProgress = p }
                                    cleanCount = count
                                    cleanDone = true
                                } catch (e: Exception) {
                                    cleanProgress = "خطا: \${e.message}"
                                }
                                isCleanLoading = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                    ) { Text("شروع پاکسازی", color = Color.White) }
                }
            },
            dismissButton = {
                if (!isCleanLoading && !cleanDone)
                    TextButton(onClick = { showCleanDialog = false }) {
                        Text("انصراف", color = Color(0xFFA7B1C2))
                    }
            }
        )
    }

    if (showEditDialog) {
        EditItemDialog(
            editingPart = editingPart,
            editingCategory = editingCategory,
            editingModel = editingModel,
            editingBrand = editingBrand,
            onDismiss = {
                showEditDialog = false
                editingPart = null; editingCategory = null
                editingModel = null; editingBrand = null
            },
            onSave = { name, extra1, extra2 ->
                scope.launch {
                    when {
                        editingPart != null -> {
                            repository.updatePart(
                                selectedBrand!!.id, selectedModel!!.id, selectedCategory!!.id,
                                editingPart!!.copy(name = name, price = extra1.toLongOrNull() ?: 0, stock = extra2.toIntOrNull() ?: 0)
                            )
                            parts = repository.getParts(selectedBrand!!.id, selectedModel!!.id, selectedCategory!!.id)
                        }
                        editingCategory != null -> {
                            repository.updateCategory(selectedBrand!!.id, selectedModel!!.id, editingCategory!!.copy(name = name))
                            categories = repository.getCategories(selectedBrand!!.id, selectedModel!!.id)
                        }
                        editingModel != null -> {
                            repository.updateModel(selectedBrand!!.id, editingModel!!.copy(name = name, year = extra1))
                            models = repository.getModels(selectedBrand!!.id)
                        }
                        editingBrand != null -> {
                            repository.updateBrand(editingBrand!!.copy(name = name, color = extra1))
                            brands = repository.getBrands()
                        }
                    }
                    showEditDialog = false
                    editingPart = null; editingCategory = null
                    editingModel = null; editingBrand = null
                }
            }
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0B1220), Color(0xFF142033), Color(0xFF0B1220))
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0D1B2A))
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "برگشت",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "پنل مدیریت",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Breadcrumb
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "برندها",
                        color = if (selectedBrand == null) Color(0xFF3B82F6) else Color(0xFFA7B1C2),
                        fontSize = 13.sp,
                        modifier = Modifier.clickable {
                            selectedBrand = null
                            selectedModel = null
                            selectedCategory = null
                            currentSection = "brands"
                        }
                    )
                    selectedBrand?.let { brand ->
                        Text(" / ", color = Color(0xFFA7B1C2), fontSize = 13.sp)
                        Text(
                            text = brand.name,
                            color = if (selectedModel == null) Color(0xFF3B82F6) else Color(0xFFA7B1C2),
                            fontSize = 13.sp,
                            modifier = Modifier.clickable {
                                selectedModel = null
                                selectedCategory = null
                                currentSection = "models"
                            }
                        )
                    }
                    selectedModel?.let { model ->
                        Text(" / ", color = Color(0xFFA7B1C2), fontSize = 13.sp)
                        Text(
                            text = model.name,
                            color = if (selectedCategory == null) Color(0xFF3B82F6) else Color(0xFFA7B1C2),
                            fontSize = 13.sp,
                            modifier = Modifier.clickable {
                                selectedCategory = null
                                currentSection = "categories"
                            }
                        )
                    }
                    selectedCategory?.let { cat ->
                        Text(" / ", color = Color(0xFFA7B1C2), fontSize = 13.sp)
                        Text(
                            text = cat.name,
                            color = Color(0xFF3B82F6),
                            fontSize = 13.sp
                        )
                    }
                }

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF3B82F6))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        when {
                            selectedCategory != null -> {
                                item {
                                    SectionHeader(
                                        title = "قطعات ${selectedCategory!!.name}",
                                        onAdd = { showAddDialog = true; currentSection = "parts" }
                                    )
                                }
                                items(parts) { part ->
                                    AdminItemCard(
                                        title = part.name,
                                        subtitle = "قیمت: ${part.price.formatPrice()} تومان | موجودی: ${part.stock}",
                                        onEdit = { editingPart = part; showEditDialog = true },
                                        onDelete = {
                                            scope.launch {
                                                repository.deletePart(
                                                    selectedBrand!!.id,
                                                    selectedModel!!.id,
                                                    selectedCategory!!.id,
                                                    part.id
                                                )
                                                parts = repository.getParts(
                                                    selectedBrand!!.id,
                                                    selectedModel!!.id,
                                                    selectedCategory!!.id
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            selectedModel != null -> {
                                item {
                                    SectionHeader(
                                        title = "دسته‌بندی‌های ${selectedModel!!.name}",
                                        onAdd = { showAddDialog = true; currentSection = "categories" }
                                    )
                                }
                                items(categories) { category ->
                                    AdminItemCard(
                                        title = category.name,
                                        onClick = { selectedCategory = category },
                                        onEdit = { editingCategory = category; showEditDialog = true },
                                        onDelete = null
                                    )
                                }
                            }
                            selectedBrand != null -> {
                                item {
                                    SectionHeader(
                                        title = "مدل‌های ${selectedBrand!!.name}",
                                        onAdd = { showAddDialog = true; currentSection = "models" }
                                    )
                                }
                                items(models) { model ->
                                    AdminItemCard(
                                        title = model.name,
                                        subtitle = model.year,
                                        onClick = { selectedModel = model },
                                        onEdit = { editingModel = model; showEditDialog = true },
                                        onDelete = null
                                    )
                                }
                            }
                            else -> {
                                item {
                                    SectionHeader(
                                        title = "برندها",
                                        onAdd = { showAddDialog = true; currentSection = "brands" }
                                    )
                                }
                                items(brands) { brand ->
                                    AdminItemCard(
                                        title = brand.name,
                                        onClick = { selectedBrand = brand },
                                        onEdit = { editingBrand = brand; showEditDialog = true },
                                        onDelete = {
                                            scope.launch {
                                                repository.deleteBrand(brand.id)
                                                brands = repository.getBrands()
                                            }
                                        }
                                    )
                                }
                                // دکمه راه‌اندازی اولیه
                                item {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedButton(
                                        onClick = { showCleanDialog = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.5f)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteSweep,
                                            contentDescription = null,
                                            tint = Color(0xFFE53935),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "پاکسازی داده‌های قدیمی",
                                            color = Color(0xFFE53935),
                                            fontSize = 13.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedButton(
                                        onClick = {
                                            isExporting = true
                                            scope.launch {
                                                try {
                                                    val json = repository.exportAllData()
                                                    val file = File(context.cacheDir, "hamrah_backup_${System.currentTimeMillis()}.json")
                                                    file.writeText(json)
                                                    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                                        type = "application/json"
                                                        putExtra(Intent.EXTRA_STREAM, uri)
                                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                    }
                                                    context.startActivity(Intent.createChooser(intent, "ذخیره بکاپ"))
                                                } catch (e: Exception) { }
                                                isExporting = false
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, Color(0xFF28A745).copy(alpha = 0.5f)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        if (isExporting) {
                                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color(0xFF28A745), strokeWidth = 2.dp)
                                        } else {
                                            Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Color(0xFF28A745), modifier = Modifier.size(18.dp))
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("اکسپورت و بکاپ داده‌ها", color = Color(0xFF28A745), fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedButton(
                                        onClick = { showSeedDialog = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.5f)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CloudUpload,
                                            contentDescription = null,
                                            tint = Color(0xFF3B82F6),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "راه‌اندازی اولیه (وارد کردن قطعات پیش‌فرض)",
                                            color = Color(0xFF3B82F6),
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Add Dialog
            if (showAddDialog) {
                AddItemDialog(
                    section = currentSection,
                    onDismiss = { showAddDialog = false },
                    onAdd = { name, extra1, extra2 ->
                        scope.launch {
                            when (currentSection) {
                                "parts" -> {
                                    repository.addPart(
                                        selectedBrand!!.id,
                                        selectedModel!!.id,
                                        selectedCategory!!.id,
                                        Part(
                                            name = name,
                                            price = extra1.toLongOrNull() ?: 0,
                                            stock = extra2.toIntOrNull() ?: 0
                                        )
                                    )
                                    parts = repository.getParts(
                                        selectedBrand!!.id,
                                        selectedModel!!.id,
                                        selectedCategory!!.id
                                    )
                                }
                                "categories" -> {
                                    val catId = name.replace(" ", "_").lowercase()
                                    repository.addCategory(
                                        selectedBrand!!.id,
                                        selectedModel!!.id,
                                        Category(id = catId, name = name, order = categories.size + 1)
                                    )
                                    categories = repository.getCategories(selectedBrand!!.id, selectedModel!!.id)
                                }
                                "models" -> {
                                    val modelId = name.replace(" ", "_").lowercase()
                                    repository.addModel(
                                        selectedBrand!!.id,
                                        CarModel(id = modelId, name = name, year = extra1, order = models.size + 1)
                                    )
                                    models = repository.getModels(selectedBrand!!.id)
                                }
                                "brands" -> {
                                    val brandId = name.replace(" ", "_").lowercase()
                                    repository.addBrand(
                                        Brand(id = brandId, name = name, color = extra1, order = brands.size + 1)
                                    )
                                    brands = repository.getBrands()
                                }
                            }
                            showAddDialog = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        IconButton(
            onClick = onAdd,
            modifier = Modifier
                .background(Color(0xFF3B82F6), RoundedCornerShape(8.dp))
                .size(36.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "افزودن", tint = Color.White)
        }
    }
}

@Composable
fun AdminItemCard(
    title: String,
    subtitle: String = "",
    onClick: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .border(1.dp, Color(0xFF243447), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2D40))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                if (subtitle.isNotEmpty()) {
                    Text(text = subtitle, fontSize = 12.sp, color = Color(0xFFA7B1C2))
                }
            }
            onEdit?.let {
                IconButton(onClick = it) {
                    Icon(Icons.Default.Edit, contentDescription = "ویرایش", tint = Color(0xFF3B82F6).copy(alpha = 0.8f))
                }
            }
            onDelete?.let {
                IconButton(onClick = it) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Red.copy(alpha = 0.7f))
                }
            }
            if (onClick != null) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFFA7B1C2)
                )
            }
        }
    }
}

@Composable
fun AddItemDialog(
    section: String,
    onDismiss: () -> Unit,
    onAdd: (name: String, extra1: String, extra2: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var extra1 by remember { mutableStateOf("") }
    var extra2 by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF142033),
        title = {
            Text(
                text = when (section) {
                    "parts" -> "افزودن قطعه"
                    "categories" -> "افزودن دسته‌بندی"
                    "models" -> "افزودن مدل خودرو"
                    else -> "افزودن برند"
                },
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("نام", color = Color(0xFFA7B1C2)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF3B82F6),
                        unfocusedBorderColor = Color(0xFF243447)
                    )
                )
                when (section) {
                    "parts" -> {
                        OutlinedTextField(
                            value = extra1,
                            onValueChange = { extra1 = it },
                            label = { Text("قیمت (تومان)", color = Color(0xFFA7B1C2)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFF243447)
                            )
                        )
                        OutlinedTextField(
                            value = extra2,
                            onValueChange = { extra2 = it },
                            label = { Text("موجودی", color = Color(0xFFA7B1C2)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFF243447)
                            )
                        )
                    }
                    "models" -> {
                        OutlinedTextField(
                            value = extra1,
                            onValueChange = { extra1 = it },
                            label = { Text("سال تولید (مثلاً ۱۳۸۰ تا کنون)", color = Color(0xFFA7B1C2)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFF243447)
                            )
                        )
                    }
                    "brands" -> {
                        OutlinedTextField(
                            value = extra1,
                            onValueChange = { extra1 = it },
                            label = { Text("رنگ (مثلاً #0D2C54)", color = Color(0xFFA7B1C2)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFF243447)
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(name, extra1, extra2) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
            ) {
                Text("افزودن", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("انصراف", color = Color(0xFFA7B1C2))
            }
        }
    )
}

@Composable
fun EditItemDialog(
    editingPart: Part?,
    editingCategory: Category?,
    editingModel: CarModel?,
    editingBrand: Brand?,
    onDismiss: () -> Unit,
    onSave: (name: String, extra1: String, extra2: String) -> Unit
) {
    var name by remember(editingPart, editingCategory, editingModel, editingBrand) {
        mutableStateOf(
            editingPart?.name ?: editingCategory?.name ?: editingModel?.name ?: editingBrand?.name ?: ""
        )
    }
    var extra1 by remember(editingPart, editingModel, editingBrand) {
        mutableStateOf(
            editingPart?.price?.toString() ?: editingModel?.year ?: editingBrand?.color ?: ""
        )
    }
    var extra2 by remember(editingPart) {
        mutableStateOf(editingPart?.stock?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF142033),
        title = {
            Text(
                text = when {
                    editingPart != null -> "ویرایش قطعه"
                    editingCategory != null -> "ویرایش دسته‌بندی"
                    editingModel != null -> "ویرایش مدل"
                    else -> "ویرایش برند"
                },
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("نام", color = Color(0xFFA7B1C2)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF3B82F6), unfocusedBorderColor = Color(0xFF243447)
                    )
                )
                if (editingPart != null) {
                    OutlinedTextField(
                        value = extra1,
                        onValueChange = { extra1 = it },
                        label = { Text("قیمت (تومان)", color = Color(0xFFA7B1C2)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF3B82F6), unfocusedBorderColor = Color(0xFF243447)
                        )
                    )
                    OutlinedTextField(
                        value = extra2,
                        onValueChange = { extra2 = it },
                        label = { Text("موجودی", color = Color(0xFFA7B1C2)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF3B82F6), unfocusedBorderColor = Color(0xFF243447)
                        )
                    )
                }
                if (editingModel != null) {
                    OutlinedTextField(
                        value = extra1,
                        onValueChange = { extra1 = it },
                        label = { Text("سال تولید", color = Color(0xFFA7B1C2)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF3B82F6), unfocusedBorderColor = Color(0xFF243447)
                        )
                    )
                }
                if (editingBrand != null) {
                    OutlinedTextField(
                        value = extra1,
                        onValueChange = { extra1 = it },
                        label = { Text("رنگ (مثلاً #0D2C54)", color = Color(0xFFA7B1C2)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF3B82F6), unfocusedBorderColor = Color(0xFF243447)
                        )
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, extra1, extra2) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
            ) { Text("ذخیره", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("انصراف", color = Color(0xFFA7B1C2))
            }
        }
    )
}
