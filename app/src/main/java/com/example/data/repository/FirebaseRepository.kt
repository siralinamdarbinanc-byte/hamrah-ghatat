package com.example.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Brand(
    val id: String = "",
    val name: String = "",
    val color: String = "",
    val order: Int = 0
)

data class CarModel(
    val id: String = "",
    val name: String = "",
    val year: String = "",
    val order: Int = 0
)

data class Category(
    val id: String = "",
    val name: String = "",
    val order: Int = 0
)

data class Part(
    val id: String = "",
    val name: String = "",
    val price: Long = 0,
    val stock: Int = 0,
    val description: String = "",
    val brandId: String = "",
    val modelId: String = "",
    val categoryId: String = ""
)

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Auth
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() = auth.signOut()

    fun isLoggedIn() = auth.currentUser != null

    // Brands
    suspend fun getBrands(): List<Brand> {
        return try {
            db.collection("brands")
                .orderBy("order")
                .get()
                .await()
                .documents
                .map { doc ->
                    Brand(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        color = doc.getString("color") ?: "",
                        order = (doc.getLong("order") ?: 0).toInt()
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addBrand(brand: Brand): Result<Unit> {
        return try {
            db.collection("brands")
                .document(brand.id)
                .set(mapOf(
                    "name" to brand.name,
                    "color" to brand.color,
                    "order" to brand.order
                ))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteBrand(brandId: String): Result<Unit> {
        return try {
            db.collection("brands").document(brandId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Models
    suspend fun getModels(brandId: String): List<CarModel> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .orderBy("order")
                .get()
                .await()
                .documents
                .map { doc ->
                    CarModel(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        year = doc.getString("year") ?: "",
                        order = (doc.getLong("order") ?: 0).toInt()
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addModel(brandId: String, model: CarModel): Result<Unit> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(model.id)
                .set(mapOf(
                    "name" to model.name,
                    "year" to model.year,
                    "order" to model.order
                ))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Categories
    suspend fun getCategories(brandId: String, modelId: String): List<Category> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(modelId)
                .collection("categories")
                .orderBy("order")
                .get()
                .await()
                .documents
                .map { doc ->
                    Category(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        order = (doc.getLong("order") ?: 0).toInt()
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addCategory(brandId: String, modelId: String, category: Category): Result<Unit> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(modelId)
                .collection("categories")
                .document(category.id)
                .set(mapOf(
                    "name" to category.name,
                    "order" to category.order
                ))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Parts
    suspend fun getParts(brandId: String, modelId: String, categoryId: String): List<Part> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(modelId)
                .collection("categories")
                .document(categoryId)
                .collection("parts")
                .get()
                .await()
                .documents
                .map { doc ->
                    Part(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        price = doc.getLong("price") ?: 0,
                        stock = (doc.getLong("stock") ?: 0).toInt(),
                        description = doc.getString("description") ?: "",
                        brandId = brandId,
                        modelId = modelId,
                        categoryId = categoryId
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addPart(brandId: String, modelId: String, categoryId: String, part: Part): Result<Unit> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(modelId)
                .collection("categories")
                .document(categoryId)
                .collection("parts")
                .add(mapOf(
                    "name" to part.name,
                    "price" to part.price,
                    "stock" to part.stock,
                    "description" to part.description
                ))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updateBrand(brand: Brand): Result<Unit> {
        return try {
            db.collection("brands").document(brand.id)
                .update(mapOf("name" to brand.name, "color" to brand.color, "order" to brand.order))
                .await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun updateModel(brandId: String, model: CarModel): Result<Unit> {
        return try {
            db.collection("brands").document(brandId)
                .collection("models").document(model.id)
                .update(mapOf("name" to model.name, "year" to model.year, "order" to model.order))
                .await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun updateCategory(brandId: String, modelId: String, category: Category): Result<Unit> {
        return try {
            db.collection("brands").document(brandId)
                .collection("models").document(modelId)
                .collection("categories").document(category.id)
                .update(mapOf("name" to category.name, "order" to category.order))
                .await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun updatePart(brandId: String, modelId: String, categoryId: String, part: Part): Result<Unit> {
        return try {
            db.collection("brands").document(brandId)
                .collection("models").document(modelId)
                .collection("categories").document(categoryId)
                .collection("parts").document(part.id)
                .update(mapOf("name" to part.name, "price" to part.price, "stock" to part.stock, "description" to part.description))
                .await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun deletePart(brandId: String, modelId: String, categoryId: String, partId: String): Result<Unit> {
        return try {
            db.collection("brands")
                .document(brandId)
                .collection("models")
                .document(modelId)
                .collection("categories")
                .document(categoryId)
                .collection("parts")
                .document(partId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun exportAllData(): String {
        val sb = StringBuilder()
        sb.append("{\n\"brands\": [\n")
        val brands = getBrands()
        brands.forEachIndexed { bi, brand ->
            sb.append("  {\"id\": \"${brand.id}\", \"name\": \"${brand.name}\", \"color\": \"${brand.color}\",\n   \"models\": [\n")
            val models = getModels(brand.id)
            models.forEachIndexed { mi, model ->
                sb.append("    {\"id\": \"${model.id}\", \"name\": \"${model.name}\", \"year\": \"${model.year}\",\n     \"categories\": [\n")
                val cats = getCategories(brand.id, model.id)
                cats.forEachIndexed { ci, cat ->
                    sb.append("      {\"id\": \"${cat.id}\", \"name\": \"${cat.name}\",\n       \"parts\": [\n")
                    val parts = getParts(brand.id, model.id, cat.id)
                    parts.forEachIndexed { pi, part ->
                        sb.append("        {\"id\": \"${part.id}\", \"name\": \"${part.name}\", \"price\": ${part.price}, \"stock\": ${part.stock}}")
                        if (pi < parts.size - 1) sb.append(",")
                        sb.append("\n")
                    }
                    sb.append("       ]}")
                    if (ci < cats.size - 1) sb.append(",")
                    sb.append("\n")
                }
                sb.append("     ]}")
                if (mi < models.size - 1) sb.append(",")
                sb.append("\n")
            }
            sb.append("   ]}")
            if (bi < brands.size - 1) sb.append(",")
            sb.append("\n")
        }
        sb.append("]\n}")
        return sb.toString()
    }

}