package com.example.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object DataSeeder {

    private val db = FirebaseFirestore.getInstance()

    suspend fun seedIfEmpty() {
        val brands = db.collection("brands").get().await()
        if (brands.isEmpty) {
            seedAll()
        }
    }

    private suspend fun seedAll() {
        seedIranKhodro()
        seedSaipa()
        seedOtherCars()
    }

    private suspend fun seedIranKhodro() {
        val brandId = "iran_khodro"
        db.collection("brands").document(brandId).set(
            mapOf("name" to "ایران‌خودرو", "color" to "#0D2C54", "order" to 1)
        ).await()

        val models = listOf(
            mapOf("id" to "peugeot_pars", "name" to "پژو پارس", "year" to "۱۳۸۰ تا کنون", "order" to 1),
            mapOf("id" to "samand", "name" to "سمند", "year" to "۱۳۸۱ تا کنون", "order" to 2),
            mapOf("id" to "dena", "name" to "دنا", "year" to "۱۳۹۲ تا کنون", "order" to 3),
            mapOf("id" to "rana", "name" to "رانا", "year" to "۱۳۹۲ تا کنون", "order" to 4),
            mapOf("id" to "peugeot_206", "name" to "پژو ۲۰۶", "year" to "۱۳۸۰ تا کنون", "order" to 5),
            mapOf("id" to "peugeot_405", "name" to "پژو ۴۰۵", "year" to "۱۳۷۲ تا کنون", "order" to 6),
            mapOf("id" to "tara", "name" to "تارا", "year" to "۱۴۰۰ تا کنون", "order" to 7),
            mapOf("id" to "tondar", "name" to "تندر ۹۰", "year" to "۱۳۸۵ تا کنون", "order" to 8),
            mapOf("id" to "xantia", "name" to "زانتیا", "year" to "۱۳۷۵ تا کنون", "order" to 9),
            mapOf("id" to "haima", "name" to "هایما S5", "year" to "۱۳۹۵ تا کنون", "order" to 10),
        )

        val categories = listOf(
            mapOf("id" to "engine", "name" to "موتور", "order" to 1),
            mapOf("id" to "body", "name" to "بدنه", "order" to 2),
            mapOf("id" to "suspension", "name" to "جلوبندی و تعلیق", "order" to 3),
            mapOf("id" to "electrical", "name" to "برق و ECU", "order" to 4),
            mapOf("id" to "gearbox", "name" to "گیربکس", "order" to 5),
        )

        for (model in models) {
            val modelId = model["id"] as String
            db.collection("brands").document(brandId)
                .collection("models").document(modelId)
                .set(mapOf("name" to model["name"], "year" to model["year"], "order" to model["order"]))
                .await()

            for (cat in categories) {
                val catId = cat["id"] as String
                db.collection("brands").document(brandId)
                    .collection("models").document(modelId)
                    .collection("categories").document(catId)
                    .set(mapOf("name" to cat["name"], "order" to cat["order"]))
                    .await()
            }
        }
    }

    private suspend fun seedSaipa() {
        val brandId = "saipa"
        db.collection("brands").document(brandId).set(
            mapOf("name" to "سایپا", "color" to "#C96A1A", "order" to 2)
        ).await()

        val models = listOf(
            mapOf("id" to "pride", "name" to "پراید", "year" to "۱۳۶۹ تا کنون", "order" to 1),
            mapOf("id" to "tiba", "name" to "تیبا", "year" to "۱۳۸۸ تا کنون", "order" to 2),
            mapOf("id" to "saina", "name" to "ساینا", "year" to "۱۳۹۳ تا کنون", "order" to 3),
            mapOf("id" to "shahin", "name" to "شاهین", "year" to "۱۳۹۹ تا کنون", "order" to 4),
            mapOf("id" to "quick", "name" to "کوییک", "year" to "۱۳۹۷ تا کنون", "order" to 5),
            mapOf("id" to "atlas", "name" to "اطلس", "year" to "۱۳۹۷ تا کنون", "order" to 6),
        )

        val categories = listOf(
            mapOf("id" to "engine", "name" to "موتور", "order" to 1),
            mapOf("id" to "body", "name" to "بدنه", "order" to 2),
            mapOf("id" to "suspension", "name" to "جلوبندی و تعلیق", "order" to 3),
            mapOf("id" to "electrical", "name" to "برق و ECU", "order" to 4),
            mapOf("id" to "gearbox", "name" to "گیربکس", "order" to 5),
        )

        for (model in models) {
            val modelId = model["id"] as String
            db.collection("brands").document(brandId)
                .collection("models").document(modelId)
                .set(mapOf("name" to model["name"], "year" to model["year"], "order" to model["order"]))
                .await()

            for (cat in categories) {
                val catId = cat["id"] as String
                db.collection("brands").document(brandId)
                    .collection("models").document(modelId)
                    .collection("categories").document(catId)
                    .set(mapOf("name" to cat["name"], "order" to cat["order"]))
                    .await()
            }
        }
    }

    private suspend fun seedOtherCars() {
        val brandId = "other_cars"
        db.collection("brands").document(brandId).set(
            mapOf("name" to "سایر خودروها", "color" to "#1F4D3A", "order" to 3)
        ).await()

        val models = listOf(
            mapOf("id" to "other", "name" to "سایر مدل‌ها", "year" to "", "order" to 1),
        )

        val categories = listOf(
            mapOf("id" to "engine", "name" to "موتور", "order" to 1),
            mapOf("id" to "body", "name" to "بدنه", "order" to 2),
            mapOf("id" to "suspension", "name" to "جلوبندی و تعلیق", "order" to 3),
            mapOf("id" to "electrical", "name" to "برق و ECU", "order" to 4),
            mapOf("id" to "gearbox", "name" to "گیربکس", "order" to 5),
        )

        for (model in models) {
            val modelId = model["id"] as String
            db.collection("brands").document(brandId)
                .collection("models").document(modelId)
                .set(mapOf("name" to model["name"], "year" to model["year"], "order" to model["order"]))
                .await()

            for (cat in categories) {
                val catId = cat["id"] as String
                db.collection("brands").document(brandId)
                    .collection("models").document(modelId)
                    .collection("categories").document(catId)
                    .set(mapOf("name" to cat["name"], "order" to cat["order"]))
                    .await()
            }
        }
    }
}
