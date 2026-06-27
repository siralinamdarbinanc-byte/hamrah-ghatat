package com.example.data

import com.example.data.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object DataSeeder {

    private val db = FirebaseFirestore.getInstance()

    // ساختار داده داخلی
    private data class PartData(val name: String, val price: Long, val stock: Int)
    private data class CategoryData(val id: String, val name: String, val order: Int, val parts: List<PartData>)
    private data class ModelData(val id: String, val name: String, val year: String, val order: Int, val categories: List<CategoryData>)
    private data class BrandData(val id: String, val name: String, val color: String, val order: Int, val models: List<ModelData>)

    private val BRANDS = listOf(
        BrandData("iran_khodro", "ایران‌خودرو", "#0D2C54", 1, listOf(
            ModelData("peugeot_405", "پژو ۴۰۵", "۱۳۷۲ تا کنون", 1, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر پژو ۴۰۵ استاندارد ایساکو", 0, 15),
                    PartData("رینگ موتور پژو ۴۰۵ STD عظام", 0, 8),
                    PartData("پیستون کامل پژو ۴۰۵ STD ایساکو", 0, 5),
                    PartData("تسمه تایم پژو ۴۰۵ پاورگریپ", 0, 20),
                    PartData("سفت‌کن تسمه تایم پژو ۴۰۵", 0, 12),
                    PartData("فیلتر روغن پژو ۴۰۵ ایساکو", 0, 50),
                    PartData("اویل پمپ پژو ۴۰۵ ایساکو", 0, 6),
                    PartData("واتر پمپ پژو ۴۰۵ ایساکو", 0, 10),
                    PartData("ترموستات پژو ۴۰۵", 0, 25),
                    PartData("درپوش رادیاتور پژو ۴۰۵", 0, 30),
                    PartData("شیلنگ رادیاتور بالا پژو ۴۰۵", 0, 20),
                    PartData("شیلنگ رادیاتور پایین پژو ۴۰۵", 0, 20),
                    PartData("میل سوپاپ پژو ۴۰۵ ایساکو", 0, 3),
                    PartData("سوپاپ دود پژو ۴۰۵ ایساکو", 0, 30),
                    PartData("سوپاپ هوا پژو ۴۰۵ ایساکو", 0, 30),
                    PartData("اسبک پژو ۴۰۵ ایساکو", 0, 40),
                    PartData("یاتاقان ثابت پژو ۴۰۵ STD", 0, 10),
                    PartData("یاتاقان متحرک پژو ۴۰۵ STD", 0, 10),
                    PartData("واشر منیفولد دود پژو ۴۰۵", 0, 25),
                    PartData("درپوش سرسیلندر پژو ۴۰۵ ایساکو", 0, 8),
                    PartData("پمپ بنزین پژو ۴۰۵ داخل باک", 0, 7),
                    PartData("فیلتر بنزین پژو ۴۰۵", 0, 35),
                    PartData("انژکتور پژو ۴۰۵ زیمنس", 0, 5),
                    PartData("دریچه گاز پژو ۴۰۵", 0, 4),
                    PartData("فیلتر هوا پژو ۴۰۵", 0, 28)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو پژو ۴۰۵ عظام", 0, 8),
                    PartData("کمک فنر عقب پژو ۴۰۵ عظام", 0, 8),
                    PartData("فنر جلو پژو ۴۰۵", 0, 10),
                    PartData("فنر عقب پژو ۴۰۵", 0, 10),
                    PartData("سیبک فرمان پژو ۴۰۵ امیرنیا", 0, 15),
                    PartData("طبق کامل راست پژو ۴۰۵ ایساکو", 0, 5),
                    PartData("طبق کامل چپ پژو ۴۰۵ ایساکو", 0, 5),
                    PartData("بوش طبق بزرگ پژو ۴۰۵", 0, 30),
                    PartData("بوش طبق کوچک پژو ۴۰۵", 0, 30),
                    PartData("لنت ترمز جلو پژو ۴۰۵ تیتان", 0, 20),
                    PartData("لنت ترمز عقب پژو ۴۰۵ تیتان", 0, 20),
                    PartData("دیسک ترمز جلو پژو ۴۰۵ ایساکو", 0, 8),
                    PartData("کاسه چرخ عقب پژو ۴۰۵ ایساکو", 0, 6),
                    PartData("پلوس کامل راست پژو ۴۰۵ ایساکو", 0, 4),
                    PartData("پلوس کامل چپ پژو ۴۰۵ ایساکو", 0, 4),
                    PartData("گردگیر پلوس پژو ۴۰۵ داخلی", 0, 20),
                    PartData("گردگیر پلوس پژو ۴۰۵ خارجی", 0, 20),
                    PartData("بلبرینگ چرخ جلو پژو ۴۰۵", 0, 12),
                    PartData("بلبرینگ چرخ عقب پژو ۴۰۵", 0, 12),
                    PartData("کالیبر ترمز جلو پژو ۴۰۵ ایساکو", 0, 5),
                    PartData("سیلندر چرخ عقب پژو ۴۰۵", 0, 10),
                    PartData("شیر فرمان پژو ۴۰۵ ایساکو", 0, 3),
                    PartData("تسمه فرمان پژو ۴۰۵", 0, 15),
                    PartData("میل فرمان کامل پژو ۴۰۵", 0, 4)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("ایسیو زیمنس پژو ۴۰۵ کروز", 0, 2),
                    PartData("کویل دوبل پژو ۴۰۵ TU5", 0, 6),
                    PartData("شمع پژو ۴۰۵ اکیوم NGK", 0, 40),
                    PartData("دینام پژو ۴۰۵ ۷۰ آمپر ایساکو", 0, 3),
                    PartData("استارت پژو ۴۰۵ ایساکو", 0, 4),
                    PartData("سنسور اکسیژن پژو ۴۰۵ زیمنس", 0, 5),
                    PartData("سنسور دما آب پژو ۴۰۵", 0, 15),
                    PartData("سنسور میل لنگ پژو ۴۰۵", 0, 8),
                    PartData("رله بوش پژو ۴۰۵", 0, 20),
                    PartData("چراغ جلو پژو ۴۰۵ راست", 0, 4),
                    PartData("چراغ جلو پژو ۴۰۵ چپ", 0, 4),
                    PartData("چراغ عقب پژو ۴۰۵ راست", 0, 5),
                    PartData("چراغ عقب پژو ۴۰۵ چپ", 0, 5),
                    PartData("باتری پژو ۴۰۵ ۵۵ آمپر صبا", 0, 5)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ پژو ۴۰۵ والئو", 0, 6),
                    PartData("صفحه کلاچ پژو ۴۰۵ والئو", 0, 6),
                    PartData("بلبرینگ کلاچ پژو ۴۰۵", 0, 12),
                    PartData("سیم کلاچ پژو ۴۰۵ ایساکو", 0, 20),
                    PartData("روغن گیربکس پژو ۴۰۵ ۱ لیتر", 0, 30),
                    PartData("دنده برنجی گیربکس پژو ۴۰۵", 0, 8)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو پژو ۴۰۵ مهرکام", 0, 4),
                    PartData("سپر عقب پژو ۴۰۵ مهرکام", 0, 4),
                    PartData("گلگیر جلو راست پژو ۴۰۵", 0, 3),
                    PartData("گلگیر جلو چپ پژو ۴۰۵", 0, 3),
                    PartData("آینه بغل راست پژو ۴۰۵", 0, 5),
                    PartData("آینه بغل چپ پژو ۴۰۵", 0, 5),
                    PartData("شیشه جلو پژو ۴۰۵", 0, 2),
                    PartData("دستگیره درب پژو ۴۰۵ بیرونی", 0, 10),
                    PartData("لولای کاپوت پژو ۴۰۵", 0, 8)
                ))
            )),
            ModelData("peugeot_pars", "پژو پارس", "۱۳۸۰ تا کنون", 2, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر پارس XU7 ایساکو", 0, 12),
                    PartData("رینگ موتور پارس XU7 STD عظام", 0, 8),
                    PartData("پیستون کامل پارس XU7 STD", 0, 5),
                    PartData("تسمه تایم پارس XU7 پاورگریپ", 0, 18),
                    PartData("فیلتر روغن پارس ایساکو", 0, 50),
                    PartData("واتر پمپ پارس XU7 ایساکو", 0, 8),
                    PartData("ترموستات پارس XU7", 0, 20),
                    PartData("اویل پمپ پارس XU7 ایساکو", 0, 5),
                    PartData("پمپ بنزین پارس داخل باک", 0, 6),
                    PartData("انژکتور پارس زیمنس", 0, 4),
                    PartData("دریچه گاز پارس XU7", 0, 3),
                    PartData("فیلتر بنزین پارس", 0, 30),
                    PartData("یاتاقان ثابت پارس XU7 STD", 0, 8),
                    PartData("یاتاقان متحرک پارس XU7 STD", 0, 8),
                    PartData("سوپاپ دود پارس XU7 ایساکو", 0, 25),
                    PartData("سوپاپ هوا پارس XU7 ایساکو", 0, 25),
                    PartData("میل سوپاپ پارس XU7 ایساکو", 0, 3),
                    PartData("واشر منیفولد دود پارس", 0, 20),
                    PartData("درپوش سرسیلندر پارس ایساکو", 0, 7)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو پارس عظام", 0, 6),
                    PartData("کمک فنر عقب پارس عظام", 0, 6),
                    PartData("سیبک فرمان پارس امیرنیا", 0, 12),
                    PartData("طبق کامل راست پارس ایساکو", 0, 4),
                    PartData("طبق کامل چپ پارس ایساکو", 0, 4),
                    PartData("لنت ترمز جلو پارس تیتان", 0, 18),
                    PartData("لنت ترمز عقب پارس تیتان", 0, 18),
                    PartData("دیسک ترمز جلو پارس ایساکو", 0, 6),
                    PartData("پلوس کامل راست پارس ایساکو", 0, 3),
                    PartData("پلوس کامل چپ پارس ایساکو", 0, 3),
                    PartData("بلبرینگ چرخ جلو پارس", 0, 10),
                    PartData("شیر فرمان پارس ایساکو", 0, 2),
                    PartData("بوش طبق بزرگ پارس", 0, 25),
                    PartData("گردگیر پلوس پارس خارجی", 0, 18),
                    PartData("گردگیر پلوس پارس داخلی", 0, 18)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("ایسیو زیمنس پارس کروز", 0, 2),
                    PartData("کویل دوبل پارس XU7", 0, 5),
                    PartData("شمع پارس NGK اکیوم", 0, 35),
                    PartData("دینام پارس ۷۰ آمپر ایساکو", 0, 3),
                    PartData("سنسور اکسیژن پارس زیمنس", 0, 4),
                    PartData("سنسور دما آب پارس", 0, 12),
                    PartData("چراغ جلو پارس راست", 0, 3),
                    PartData("چراغ جلو پارس چپ", 0, 3)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ پارس والئو", 0, 5),
                    PartData("صفحه کلاچ پارس والئو", 0, 5),
                    PartData("بلبرینگ کلاچ پارس", 0, 10),
                    PartData("سیم کلاچ پارس ایساکو", 0, 18),
                    PartData("دنده برنجی گیربکس پارس", 0, 6)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو پارس مهرکام", 0, 3),
                    PartData("سپر عقب پارس مهرکام", 0, 3),
                    PartData("گلگیر جلو راست پارس", 0, 3),
                    PartData("گلگیر جلو چپ پارس", 0, 3),
                    PartData("آینه بغل راست پارس", 0, 4),
                    PartData("آینه بغل چپ پارس", 0, 4)
                ))
            )),
            ModelData("samand", "سمند", "۱۳۸۱ تا کنون", 3, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر سمند EF7 ایساکو", 0, 10),
                    PartData("رینگ موتور سمند EF7 STD", 0, 6),
                    PartData("پیستون کامل سمند EF7 STD ایساکو", 0, 4),
                    PartData("تسمه تایم سمند پاورگریپ", 0, 15),
                    PartData("فیلتر روغن سمند ایساکو", 0, 45),
                    PartData("واتر پمپ سمند EF7 ایساکو", 0, 7),
                    PartData("اویل پمپ سمند EF7 ایساکو", 0, 4),
                    PartData("پمپ بنزین سمند داخل باک", 0, 5),
                    PartData("انژکتور سمند زیمنس", 0, 4),
                    PartData("دریچه گاز سمند EF7", 0, 3),
                    PartData("یاتاقان ثابت سمند EF7 STD", 0, 7),
                    PartData("فیلتر هوا سمند EF7", 0, 25)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو سمند عظام", 0, 6),
                    PartData("کمک فنر عقب سمند عظام", 0, 6),
                    PartData("سیبک فرمان سمند امیرنیا", 0, 10),
                    PartData("طبق کامل راست سمند ایساکو", 0, 4),
                    PartData("لنت ترمز جلو سمند تیتان", 0, 15),
                    PartData("لنت ترمز عقب سمند تیتان", 0, 15),
                    PartData("دیسک ترمز جلو سمند ایساکو", 0, 5),
                    PartData("پلوس کامل راست سمند ۲۲ خار", 0, 3),
                    PartData("پلوس کامل چپ سمند ۲۲ خار", 0, 3),
                    PartData("بلبرینگ چرخ جلو سمند", 0, 8),
                    PartData("شیر فرمان سمند ایساکو", 0, 2)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("ایسیو سمند EF7 مگنتی مارلی", 0, 2),
                    PartData("شمع سمند EF7 NGK", 0, 30),
                    PartData("دینام سمند ۹۰ آمپر ایساکو", 0, 2),
                    PartData("سنسور اکسیژن سمند زیمنس", 0, 3),
                    PartData("چراغ جلو سمند راست", 0, 3),
                    PartData("چراغ جلو سمند چپ", 0, 3)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ سمند والئو", 0, 4),
                    PartData("صفحه کلاچ سمند والئو", 0, 4),
                    PartData("بلبرینگ کلاچ سمند", 0, 8),
                    PartData("کشویی دنده ۱ و ۲ سمند", 0, 6)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو سمند مهرکام", 0, 3),
                    PartData("سپر عقب سمند مهرکام", 0, 3),
                    PartData("گلگیر جلو راست سمند", 0, 2),
                    PartData("گلگیر جلو چپ سمند", 0, 2)
                ))
            )),
            ModelData("dena", "دنا", "۱۳۹۲ تا کنون", 4, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر دنا EF7 ایساکو", 0, 8),
                    PartData("تسمه تایم دنا پاورگریپ", 0, 12),
                    PartData("فیلتر روغن دنا ایساکو", 0, 40),
                    PartData("واتر پمپ دنا EF7 ایساکو", 0, 6),
                    PartData("پمپ بنزین دنا داخل باک", 0, 4),
                    PartData("انژکتور دنا مگنتی مارلی", 0, 3),
                    PartData("دریچه گاز دنا EF7", 0, 3),
                    PartData("فیلتر هوا دنا EF7", 0, 20),
                    PartData("یاتاقان ثابت دنا EF7 STD", 0, 6)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو دنا عظام", 0, 4),
                    PartData("کمک فنر عقب دنا عظام", 0, 4),
                    PartData("لنت ترمز جلو دنا تیتان", 0, 12),
                    PartData("لنت ترمز عقب دنا تیتان", 0, 12),
                    PartData("دیسک ترمز جلو دنا ایساکو", 0, 4),
                    PartData("پلوس کامل راست دنا ایساکو", 0, 3),
                    PartData("بلبرینگ چرخ جلو دنا", 0, 6)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("ایسیو دنا پلاس کروز کنترل", 0, 1),
                    PartData("چراغ جلو دنا پلاس LED راست", 0, 2),
                    PartData("چراغ جلو دنا پلاس LED چپ", 0, 2),
                    PartData("شمع دنا EF7 NGK", 0, 25),
                    PartData("سنسور اکسیژن دنا زیمنس", 0, 3)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ دنا والئو", 0, 4),
                    PartData("صفحه کلاچ دنا والئو", 0, 4),
                    PartData("بلبرینگ کلاچ دنا", 0, 7)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو دنا مهرکام", 0, 2),
                    PartData("سپر عقب دنا مهرکام", 0, 2),
                    PartData("آینه بغل راست دنا برقی", 0, 3),
                    PartData("آینه بغل چپ دنا برقی", 0, 3)
                ))
            )),
            ModelData("peugeot_206", "پژو ۲۰۶", "۱۳۸۰ تا کنون", 5, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر ۲۰۶ TU5 ایساکو", 0, 10),
                    PartData("تسمه تایم ۲۰۶ TU5 پاورگریپ", 0, 18),
                    PartData("فیلتر روغن ۲۰۶ ایساکو", 0, 55),
                    PartData("واتر پمپ ۲۰۶ TU5 ایساکو", 0, 8),
                    PartData("اویل پمپ ۲۰۶ TU5 ایساکو", 0, 5),
                    PartData("پمپ بنزین ۲۰۶ داخل باک", 0, 6),
                    PartData("انژکتور ۲۰۶ زیمنس", 0, 4),
                    PartData("رینگ موتور ۲۰۶ TU5 STD عظام", 0, 7),
                    PartData("پیستون کامل ۲۰۶ TU5 STD", 0, 4),
                    PartData("یاتاقان ثابت ۲۰۶ TU5 STD", 0, 8),
                    PartData("فیلتر هوا ۲۰۶", 0, 30)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو ۲۰۶ عظام", 0, 7),
                    PartData("کمک فنر عقب ۲۰۶ عظام", 0, 7),
                    PartData("سیبک فرمان ۲۰۶ امیرنیا", 0, 14),
                    PartData("طبق کامل راست ۲۰۶ ایساکو", 0, 4),
                    PartData("طبق کامل چپ ۲۰۶ ایساکو", 0, 4),
                    PartData("لنت ترمز جلو ۲۰۶ تیتان", 0, 18),
                    PartData("لنت ترمز عقب ۲۰۶ تیتان", 0, 18),
                    PartData("دیسک ترمز جلو ۲۰۶ ایساکو", 0, 6),
                    PartData("پلوس کامل راست ۲۰۶ ایساکو", 0, 3),
                    PartData("پلوس کامل چپ ۲۰۶ ایساکو", 0, 3),
                    PartData("بلبرینگ چرخ جلو ۲۰۶", 0, 10),
                    PartData("گردگیر پلوس ۲۰۶ خارجی", 0, 20)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("ایسیو ۲۰۶ TU5 زیمنس", 0, 2),
                    PartData("کویل دوبل ۲۰۶ TU5", 0, 5),
                    PartData("شمع ۲۰۶ TU5 NGK", 0, 35),
                    PartData("دینام ۲۰۶ ۷۰ آمپر ایساکو", 0, 3),
                    PartData("سنسور اکسیژن ۲۰۶ زیمنس", 0, 4),
                    PartData("چراغ جلو ۲۰۶ راست", 0, 4),
                    PartData("چراغ جلو ۲۰۶ چپ", 0, 4)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ ۲۰۶ والئو", 0, 5),
                    PartData("صفحه کلاچ ۲۰۶ والئو", 0, 5),
                    PartData("بلبرینگ کلاچ ۲۰۶", 0, 10),
                    PartData("سیم کلاچ ۲۰۶ ایساکو", 0, 18)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو ۲۰۶ مهرکام", 0, 3),
                    PartData("سپر عقب ۲۰۶ مهرکام", 0, 3),
                    PartData("گلگیر جلو راست ۲۰۶", 0, 3),
                    PartData("گلگیر جلو چپ ۲۰۶", 0, 3),
                    PartData("آینه بغل راست ۲۰۶", 0, 5),
                    PartData("آینه بغل چپ ۲۰۶", 0, 5)
                ))
            ))
        )),
        BrandData("saipa", "سایپا", "#C96A1A", 2, listOf(
            ModelData("pride", "پراید", "۱۳۶۹ تا کنون", 1, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر پراید استاندارد سایپایدک", 0, 20),
                    PartData("رینگ موتور پراید STD گلدن", 0, 10),
                    PartData("پیستون کامل پراید STD گلدن", 0, 6),
                    PartData("تسمه تایم پراید دانگیل اصل", 0, 25),
                    PartData("سفت‌کن تسمه تایم پراید", 0, 15),
                    PartData("فیلتر روغن پراید سایپایدک", 0, 60),
                    PartData("اویل پمپ پراید سایپایدک", 0, 8),
                    PartData("واتر پمپ پراید سایپایدک", 0, 12),
                    PartData("ترموستات پراید", 0, 30),
                    PartData("پمپ بنزین پراید داخل باک یورو۴", 0, 8),
                    PartData("انژکتور پراید یورو۴ زیمنس", 0, 6),
                    PartData("دریچه گاز پراید یورو۴", 0, 4),
                    PartData("ایسیو کروز پراید یورو۴", 0, 2),
                    PartData("فیلتر بنزین پراید یورو۴", 0, 40),
                    PartData("فیلتر هوا پراید یورو۴", 0, 35),
                    PartData("یاتاقان ثابت پراید STD گلدن", 0, 12),
                    PartData("یاتاقان متحرک پراید STD گلدن", 0, 12),
                    PartData("سوپاپ دود پراید سایپایدک", 0, 35),
                    PartData("سوپاپ هوا پراید سایپایدک", 0, 35),
                    PartData("اسبک پراید سایپایدک", 0, 50),
                    PartData("میل سوپاپ پراید سایپایدک", 0, 4),
                    PartData("واشر منیفولد دود پراید", 0, 30),
                    PartData("درپوش سرسیلندر پراید سایپایدک", 0, 10),
                    PartData("شیلنگ رادیاتور بالا پراید", 0, 25),
                    PartData("شیلنگ رادیاتور پایین پراید", 0, 25)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو پراید عظام", 0, 10),
                    PartData("کمک فنر عقب پراید عظام", 0, 10),
                    PartData("فنر جلو پراید", 0, 12),
                    PartData("فنر عقب پراید", 0, 12),
                    PartData("سیبک فرمان پراید لاهیجان", 0, 18),
                    PartData("طبق کامل راست پراید سایپایدک", 0, 6),
                    PartData("طبق کامل چپ پراید سایپایدک", 0, 6),
                    PartData("بوش طبق بزرگ پراید", 0, 35),
                    PartData("بوش طبق کوچک پراید", 0, 35),
                    PartData("لنت ترمز جلو پراید تیتان", 0, 25),
                    PartData("لنت ترمز عقب پراید تیتان", 0, 25),
                    PartData("دیسک ترمز جلو پراید سایپایدک", 0, 10),
                    PartData("کاسه چرخ عقب پراید سایپایدک", 0, 8),
                    PartData("پلوس کامل راست پراید سایپایدک", 0, 5),
                    PartData("پلوس کامل چپ پراید سایپایدک", 0, 5),
                    PartData("گردگیر پلوس پراید خارجی", 0, 25),
                    PartData("گردگیر پلوس پراید داخلی", 0, 25),
                    PartData("بلبرینگ چرخ جلو پراید", 0, 14),
                    PartData("بلبرینگ چرخ عقب پراید", 0, 14),
                    PartData("کالیبر ترمز جلو پراید سایپایدک", 0, 6),
                    PartData("سیلندر چرخ عقب پراید", 0, 12),
                    PartData("جعبه فرمان هیدرولیک پراید سایپایدک", 0, 3),
                    PartData("تسمه فرمان پراید", 0, 18)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("دینام پراید ۹۰ آمپر عظام", 0, 4),
                    PartData("استارت پراید مگنتی", 0, 4),
                    PartData("شمع پراید یورو۴ NGK", 0, 40),
                    PartData("کویل پراید یورو۴ زیمنس", 0, 7),
                    PartData("سنسور اکسیژن پراید یورو۴ زیمنس", 0, 5),
                    PartData("سنسور دما آب پراید", 0, 18),
                    PartData("سنسور میل لنگ پراید", 0, 8),
                    PartData("رله بوش پراید یورو۴", 0, 22),
                    PartData("چراغ جلو پراید ۱۳۲ راست", 0, 5),
                    PartData("چراغ جلو پراید ۱۳۲ چپ", 0, 5),
                    PartData("چراغ عقب پراید ۱۳۲ راست", 0, 6),
                    PartData("چراغ عقب پراید ۱۳۲ چپ", 0, 6),
                    PartData("باتری پراید ۴۵ آمپر صبا", 0, 6)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ پراید سکو کره", 0, 8),
                    PartData("صفحه کلاچ پراید سکو کره", 0, 8),
                    PartData("بلبرینگ کلاچ پراید", 0, 15),
                    PartData("سیم کلاچ پراید سایپایدک", 0, 25),
                    PartData("دنده برنجی ۵ پراید پایا", 0, 10),
                    PartData("روغن گیربکس پراید ۱ لیتر", 0, 35)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو پراید ۱۳۲ مهرکام", 0, 5),
                    PartData("سپر عقب پراید ۱۳۲ مهرکام", 0, 5),
                    PartData("گلگیر جلو راست پراید", 0, 4),
                    PartData("گلگیر جلو چپ پراید", 0, 4),
                    PartData("آینه بغل راست پراید", 0, 6),
                    PartData("آینه بغل چپ پراید", 0, 6),
                    PartData("شیشه جلو پراید", 0, 3),
                    PartData("دستگیره درب پراید بیرونی", 0, 12),
                    PartData("کاپوت پراید سایپایدک", 0, 2)
                ))
            )),
            ModelData("tiba", "تیبا", "۱۳۸۸ تا کنون", 2, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر تیبا سایپایدک", 0, 15),
                    PartData("تسمه تایم تیبا دانگیل", 0, 20),
                    PartData("فیلتر روغن تیبا سایپایدک", 0, 55),
                    PartData("واتر پمپ تیبا سایپایدک", 0, 10),
                    PartData("پمپ بنزین تیبا داخل باک", 0, 7),
                    PartData("انژکتور تیبا یورو۴ زیمنس", 0, 5),
                    PartData("ایسیو تیبا یورو۴ کروز", 0, 2),
                    PartData("فیلتر هوا تیبا", 0, 30),
                    PartData("رینگ موتور تیبا STD گلدن", 0, 8),
                    PartData("یاتاقان ثابت تیبا STD", 0, 10)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو تیبا عظام", 0, 8),
                    PartData("کمک فنر عقب تیبا عظام", 0, 8),
                    PartData("سیبک فرمان تیبا لاهیجان", 0, 15),
                    PartData("لنت ترمز جلو تیبا تیتان", 0, 20),
                    PartData("لنت ترمز عقب تیبا تیتان", 0, 20),
                    PartData("دیسک ترمز جلو تیبا سایپایدک", 0, 8),
                    PartData("پلوس کامل راست تیبا سایپایدک", 0, 4),
                    PartData("پلوس کامل چپ تیبا سایپایدک", 0, 4),
                    PartData("بلبرینگ چرخ جلو تیبا", 0, 10),
                    PartData("طبق کامل راست تیبا سایپایدک", 0, 5)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("شمع تیبا یورو۴ NGK", 0, 35),
                    PartData("دینام تیبا ۷۰ آمپر عظام", 0, 3),
                    PartData("سنسور اکسیژن تیبا زیمنس", 0, 4),
                    PartData("چراغ جلو تیبا راست", 0, 4),
                    PartData("چراغ جلو تیبا چپ", 0, 4)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ تیبا سکو", 0, 6),
                    PartData("صفحه کلاچ تیبا سکو", 0, 6),
                    PartData("بلبرینگ کلاچ تیبا کره", 0, 12),
                    PartData("سیم کلاچ تیبا سایپایدک", 0, 20)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو تیبا مهرکام", 0, 4),
                    PartData("سپر عقب تیبا مهرکام", 0, 4),
                    PartData("گلگیر جلو راست تیبا", 0, 3),
                    PartData("گلگیر جلو چپ تیبا", 0, 3),
                    PartData("آینه بغل راست تیبا", 0, 5),
                    PartData("آینه بغل چپ تیبا", 0, 5)
                ))
            )),
            ModelData("saina", "ساینا", "۱۳۹۳ تا کنون", 3, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر ساینا اورجینال سایپایدک", 0, 12),
                    PartData("تسمه تایم ساینا دانگیل", 0, 18),
                    PartData("فیلتر روغن ساینا سایپایدک", 0, 50),
                    PartData("واتر پمپ ساینا سایپایدک", 0, 8),
                    PartData("پمپ بنزین ساینا یورو۵ داخل باک", 0, 6),
                    PartData("انژکتور ساینا یورو۵", 0, 4),
                    PartData("ایسیو ساینا یورو۵ کروز", 0, 2),
                    PartData("فیلتر هوا ساینا", 0, 25)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو ساینا عظام", 0, 7),
                    PartData("کمک فنر عقب ساینا عظام", 0, 7),
                    PartData("لنت ترمز جلو ساینا تیتان", 0, 18),
                    PartData("دیسک ترمز جلو ساینا سایپایدک", 0, 7),
                    PartData("پلوس کامل راست ساینا سایپایدک", 0, 4),
                    PartData("سیبک فرمان ساینا لاهیجان", 0, 13)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("شمع ساینا یورو۵ NGK", 0, 30),
                    PartData("سنسور اکسیژن ساینا یورو۵", 0, 4),
                    PartData("چراغ جلو ساینا راست", 0, 4),
                    PartData("چراغ جلو ساینا چپ", 0, 4),
                    PartData("دینام ساینا ۷۰ آمپر", 0, 3)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ ساینا سکو", 0, 5),
                    PartData("صفحه کلاچ ساینا سکو", 0, 5),
                    PartData("بلبرینگ کلاچ ساینا", 0, 10)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو ساینا اصلی", 0, 3),
                    PartData("سپر عقب ساینا اصلی", 0, 3),
                    PartData("گلگیر جلو راست ساینا", 0, 3),
                    PartData("آینه بغل راست ساینا", 0, 4),
                    PartData("آینه بغل چپ ساینا", 0, 4)
                ))
            )),
            ModelData("shahin", "شاهین", "۱۳۹۹ تا کنون", 4, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر شاهین M15 سایپایدک", 0, 10),
                    PartData("فیلتر روغن شاهین سایپایدک", 0, 45),
                    PartData("واتر پمپ شاهین M15", 0, 7),
                    PartData("پمپ بنزین شاهین داخل باک", 0, 5),
                    PartData("فیلتر هوا شاهین M15", 0, 22),
                    PartData("تسمه تایم شاهین M15", 0, 15)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو شاهین عظام", 0, 6),
                    PartData("کمک فنر عقب شاهین عظام", 0, 6),
                    PartData("لنت ترمز جلو شاهین تیتان", 0, 15),
                    PartData("دیسک ترمز جلو شاهین سایپایدک", 0, 6),
                    PartData("پلوس کامل راست شاهین", 0, 3)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("شمع شاهین M15 NGK", 0, 28),
                    PartData("چراغ جلو شاهین راست", 0, 3),
                    PartData("چراغ جلو شاهین چپ", 0, 3),
                    PartData("سنسور اکسیژن شاهین", 0, 3)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("کیت کلاچ شاهین والئو ترک", 0, 3),
                    PartData("بلبرینگ کلاچ شاهین", 0, 8)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو شاهین اصلی", 0, 3),
                    PartData("سپر عقب شاهین اصلی", 0, 3),
                    PartData("آینه بغل راست شاهین برقی", 0, 3),
                    PartData("آینه بغل چپ شاهین برقی", 0, 3)
                ))
            )),
            ModelData("quick", "کوییک", "۱۳۹۷ تا کنون", 5, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("واشر سرسیلندر کوییک سایپایدک", 0, 12),
                    PartData("فیلتر روغن کوییک سایپایدک", 0, 48),
                    PartData("واتر پمپ کوییک سایپایدک", 0, 8),
                    PartData("تسمه تایم کوییک دانگیل", 0, 18),
                    PartData("پمپ بنزین کوییک داخل باک", 0, 6),
                    PartData("انژکتور کوییک زیمنس", 0, 4),
                    PartData("فیلتر هوا کوییک", 0, 28)
                )),
                CategoryData("suspension", "جلوبندی و تعلیق", 2, listOf(
                    PartData("کمک فنر جلو کوییک عظام", 0, 7),
                    PartData("کمک فنر عقب کوییک عظام", 0, 7),
                    PartData("لنت ترمز جلو کوییک تیتان", 0, 18),
                    PartData("دیسک ترمز جلو کوییک سایپایدک", 0, 7),
                    PartData("جعبه فرمان کوییک دنده‌ای سایپایدک", 0, 3)
                )),
                CategoryData("electrical", "برق و ECU", 3, listOf(
                    PartData("کویل دوبل کوییک زیمنس", 0, 5),
                    PartData("شمع کوییک NGK", 0, 32),
                    PartData("چراغ جلو کوییک اس کروز راست", 0, 4),
                    PartData("چراغ جلو کوییک اس کروز چپ", 0, 4)
                )),
                CategoryData("gearbox", "گیربکس", 4, listOf(
                    PartData("دیسک کلاچ کوییک سکو", 0, 6),
                    PartData("صفحه کلاچ کوییک سکو", 0, 6),
                    PartData("بلبرینگ کلاچ کوییک", 0, 10)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو کوییک اصلی", 0, 3),
                    PartData("سپر عقب کوییک اصلی", 0, 3),
                    PartData("آینه بغل راست کوییک", 0, 4),
                    PartData("آینه بغل چپ کوییک", 0, 4)
                ))
            ))
        )),
        BrandData("other_cars", "سایر خودروها", "#28A745", 3, listOf(
            ModelData("toyota_camry", "تویوتا کمری", "۱۳۷۰ تا کنون", 1, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن کمری", 0, 0),
                    PartData("فیلتر هوا کمری", 0, 0),
                    PartData("فیلتر سوخت کمری", 0, 0),
                    PartData("شمع کمری", 0, 0),
                    PartData("تسمه تایم کمری", 0, 0),
                    PartData("تسمه دینام کمری", 0, 0),
                    PartData("واتر پمپ کمری", 0, 0),
                    PartData("ترموستات کمری", 0, 0),
                    PartData("واشر سرسیلندر کمری", 0, 0),
                    PartData("واشر منیفولد کمری", 0, 0),
                    PartData("کاسه نمد کمری", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو کمری", 0, 0),
                    PartData("لنت ترمز عقب کمری", 0, 0),
                    PartData("دیسک ترمز جلو کمری", 0, 0),
                    PartData("دیسک ترمز عقب کمری", 0, 0),
                    PartData("کاسه چرخ کمری", 0, 0),
                    PartData("سیلندر ترمز کمری", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 3, listOf(
                    PartData("سیبک فرمان کمری", 0, 0),
                    PartData("سیبک بالا کمری", 0, 0),
                    PartData("کمک فنر جلو کمری", 0, 0),
                    PartData("کمک فنر عقب کمری", 0, 0),
                    PartData("لاستیک گردگیر کمری", 0, 0),
                    PartData("طبق کمری", 0, 0)
                )),
                CategoryData("electrical", "برق", 4, listOf(
                    PartData("باتری کمری", 0, 0),
                    PartData("دینام کمری", 0, 0),
                    PartData("استارت کمری", 0, 0),
                    PartData("سنسور اکسیژن کمری", 0, 0),
                    PartData("سنسور میل لنگ کمری", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو کمری", 0, 0),
                    PartData("سپر عقب کمری", 0, 0),
                    PartData("چراغ جلو کمری", 0, 0),
                    PartData("چراغ عقب کمری", 0, 0),
                    PartData("شیشه جلو کمری", 0, 0),
                    PartData("آینه بغل کمری", 0, 0)
                )),
                CategoryData("cooling", "خنک‌کاری", 6, listOf(
                    PartData("رادیاتور کمری", 0, 0),
                    PartData("فن رادیاتور کمری", 0, 0),
                    PartData("شلنگ رادیاتور کمری", 0, 0)
                ))
            )),
            ModelData("kia_pride", "کیا پراید", "۱۳۶۸ تا ۱۳۸۰", 2, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن کیا پراید", 0, 0),
                    PartData("فیلتر هوا کیا پراید", 0, 0),
                    PartData("فیلتر سوخت کیا پراید", 0, 0),
                    PartData("شمع کیا پراید", 0, 0),
                    PartData("تسمه تایم کیا پراید", 0, 0),
                    PartData("تسمه دینام کیا پراید", 0, 0),
                    PartData("واتر پمپ کیا پراید", 0, 0),
                    PartData("واشر سرسیلندر کیا پراید", 0, 0),
                    PartData("واشر منیفولد کیا پراید", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو کیا پراید", 0, 0),
                    PartData("لنت ترمز عقب کیا پراید", 0, 0),
                    PartData("کاسه چرخ کیا پراید", 0, 0),
                    PartData("سیلندر ترمز کیا پراید", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 3, listOf(
                    PartData("سیبک کیا پراید", 0, 0),
                    PartData("کمک فنر جلو کیا پراید", 0, 0),
                    PartData("کمک فنر عقب کیا پراید", 0, 0),
                    PartData("طبق کیا پراید", 0, 0)
                )),
                CategoryData("body", "بدنه", 4, listOf(
                    PartData("سپر جلو کیا پراید", 0, 0),
                    PartData("سپر عقب کیا پراید", 0, 0),
                    PartData("چراغ جلو کیا پراید", 0, 0),
                    PartData("شیشه جلو کیا پراید", 0, 0),
                    PartData("آینه بغل کیا پراید", 0, 0)
                ))
            )),
            ModelData("hyundai_sonata", "هیوندای سوناتا", "۱۳۷۵ تا کنون", 3, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن سوناتا", 0, 0),
                    PartData("فیلتر هوا سوناتا", 0, 0),
                    PartData("فیلتر سوخت سوناتا", 0, 0),
                    PartData("شمع سوناتا NGK", 0, 0),
                    PartData("تسمه تایم سوناتا", 0, 0),
                    PartData("واتر پمپ سوناتا", 0, 0),
                    PartData("ترموستات سوناتا", 0, 0),
                    PartData("واشر سرسیلندر سوناتا", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو سوناتا", 0, 0),
                    PartData("لنت ترمز عقب سوناتا", 0, 0),
                    PartData("دیسک ترمز جلو سوناتا", 0, 0),
                    PartData("دیسک ترمز عقب سوناتا", 0, 0)
                )),
                CategoryData("electrical", "برق", 3, listOf(
                    PartData("باتری سوناتا ۷۴ آمپر", 0, 0),
                    PartData("دینام سوناتا", 0, 0),
                    PartData("استارت سوناتا", 0, 0),
                    PartData("سنسور اکسیژن سوناتا", 0, 0),
                    PartData("سنسور دور موتور سوناتا", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 4, listOf(
                    PartData("کمک فنر جلو سوناتا", 0, 0),
                    PartData("کمک فنر عقب سوناتا", 0, 0),
                    PartData("سیبک فرمان سوناتا", 0, 0),
                    PartData("طبق سوناتا", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو سوناتا", 0, 0),
                    PartData("سپر عقب سوناتا", 0, 0),
                    PartData("چراغ جلو سوناتا", 0, 0),
                    PartData("شیشه جلو سوناتا", 0, 0),
                    PartData("آینه بغل سوناتا", 0, 0)
                ))
            )),
            ModelData("kia_cerato", "کیا سراتو", "۱۳۸۹ تا کنون", 4, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن سراتو", 0, 0),
                    PartData("فیلتر هوا سراتو", 0, 0),
                    PartData("فیلتر سوخت سراتو", 0, 0),
                    PartData("شمع سراتو NGK", 0, 0),
                    PartData("تسمه دینام سراتو", 0, 0),
                    PartData("تسمه تایم سراتو", 0, 0),
                    PartData("واتر پمپ سراتو", 0, 0),
                    PartData("ترموستات سراتو", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو سراتو", 0, 0),
                    PartData("لنت ترمز عقب سراتو", 0, 0),
                    PartData("دیسک ترمز جلو سراتو", 0, 0),
                    PartData("دیسک ترمز عقب سراتو", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 3, listOf(
                    PartData("کمک فنر جلو سراتو", 0, 0),
                    PartData("کمک فنر عقب سراتو", 0, 0),
                    PartData("سیبک سراتو", 0, 0),
                    PartData("طبق سراتو", 0, 0)
                )),
                CategoryData("electrical", "برق", 4, listOf(
                    PartData("باتری سراتو", 0, 0),
                    PartData("دینام سراتو", 0, 0),
                    PartData("استارت سراتو", 0, 0),
                    PartData("سنسور کوبش سراتو", 0, 0)
                )),
                CategoryData("cooling", "خنک‌کاری", 5, listOf(
                    PartData("رادیاتور سراتو", 0, 0),
                    PartData("فن رادیاتور سراتو", 0, 0)
                ))
            )),
            ModelData("hyundai_tucson", "هیوندای توسان", "۱۳۸۹ تا کنون", 5, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن توسان", 0, 0),
                    PartData("فیلتر هوا توسان", 0, 0),
                    PartData("فیلتر سوخت توسان", 0, 0),
                    PartData("شمع توسان NGK", 0, 0),
                    PartData("واتر پمپ توسان", 0, 0),
                    PartData("ترموستات توسان", 0, 0),
                    PartData("تسمه تایم توسان", 0, 0),
                    PartData("تسمه دینام توسان", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو توسان", 0, 0),
                    PartData("لنت ترمز عقب توسان", 0, 0),
                    PartData("دیسک ترمز جلو توسان", 0, 0),
                    PartData("دیسک ترمز عقب توسان", 0, 0)
                )),
                CategoryData("electrical", "برق", 3, listOf(
                    PartData("باتری توسان ۷۴ آمپر", 0, 0),
                    PartData("دینام توسان", 0, 0),
                    PartData("استارت توسان", 0, 0),
                    PartData("سنسور اکسیژن توسان", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 4, listOf(
                    PartData("کمک فنر جلو توسان", 0, 0),
                    PartData("کمک فنر عقب توسان", 0, 0),
                    PartData("سیبک فرمان توسان", 0, 0),
                    PartData("طبق توسان", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو توسان", 0, 0),
                    PartData("سپر عقب توسان", 0, 0),
                    PartData("چراغ جلو توسان", 0, 0),
                    PartData("چراغ عقب توسان", 0, 0),
                    PartData("شیشه جلو توسان", 0, 0)
                ))
            )),
            ModelData("toyota_corolla", "تویوتا کرولا", "۱۳۸۷ تا کنون", 6, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن کرولا", 0, 0),
                    PartData("فیلتر هوا کرولا", 0, 0),
                    PartData("فیلتر سوخت کرولا", 0, 0),
                    PartData("شمع کرولا NGK", 0, 0),
                    PartData("تسمه تایم کرولا", 0, 0),
                    PartData("واتر پمپ کرولا", 0, 0),
                    PartData("ترموستات کرولا", 0, 0),
                    PartData("واشر سرسیلندر کرولا", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو کرولا", 0, 0),
                    PartData("لنت ترمز عقب کرولا", 0, 0),
                    PartData("دیسک ترمز جلو کرولا", 0, 0),
                    PartData("دیسک ترمز عقب کرولا", 0, 0)
                )),
                CategoryData("body", "بدنه", 3, listOf(
                    PartData("سپر جلو کرولا", 0, 0),
                    PartData("سپر عقب کرولا", 0, 0),
                    PartData("چراغ جلو کرولا", 0, 0),
                    PartData("شیشه جلو کرولا", 0, 0),
                    PartData("آینه بغل کرولا", 0, 0)
                )),
                CategoryData("electrical", "برق", 4, listOf(
                    PartData("باتری کرولا", 0, 0),
                    PartData("دینام کرولا", 0, 0),
                    PartData("استارت کرولا", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 5, listOf(
                    PartData("کمک فنر جلو کرولا", 0, 0),
                    PartData("کمک فنر عقب کرولا", 0, 0),
                    PartData("سیبک فرمان کرولا", 0, 0),
                    PartData("طبق کرولا", 0, 0)
                ))
            )),
            ModelData("nissan_sunny", "نیسان سانی", "۱۳۹۰ تا کنون", 7, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن سانی", 0, 0),
                    PartData("فیلتر هوا سانی", 0, 0),
                    PartData("فیلتر سوخت سانی", 0, 0),
                    PartData("شمع سانی", 0, 0),
                    PartData("تسمه تایم سانی", 0, 0),
                    PartData("واتر پمپ سانی", 0, 0),
                    PartData("ترموستات سانی", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو سانی", 0, 0),
                    PartData("لنت ترمز عقب سانی", 0, 0),
                    PartData("کاسه چرخ سانی", 0, 0),
                    PartData("سیلندر ترمز سانی", 0, 0)
                )),
                CategoryData("electrical", "برق", 3, listOf(
                    PartData("استارت سانی", 0, 0),
                    PartData("دینام سانی", 0, 0),
                    PartData("باتری سانی", 0, 0),
                    PartData("سنسور اکسیژن سانی", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 4, listOf(
                    PartData("کمک فنر جلو سانی", 0, 0),
                    PartData("کمک فنر عقب سانی", 0, 0),
                    PartData("سیبک فرمان سانی", 0, 0),
                    PartData("طبق سانی", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو سانی", 0, 0),
                    PartData("سپر عقب سانی", 0, 0),
                    PartData("چراغ جلو سانی", 0, 0),
                    PartData("شیشه جلو سانی", 0, 0),
                    PartData("آینه بغل سانی", 0, 0)
                ))
            )),
            ModelData("mitsubishi_lancer", "میتسوبیشی لنسر", "۱۳۸۴ تا کنون", 8, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن لنسر", 0, 0),
                    PartData("فیلتر هوا لنسر", 0, 0),
                    PartData("فیلتر سوخت لنسر", 0, 0),
                    PartData("شمع لنسر NGK", 0, 0),
                    PartData("تسمه تایم لنسر", 0, 0),
                    PartData("واتر پمپ لنسر", 0, 0),
                    PartData("ترموستات لنسر", 0, 0),
                    PartData("واشر سرسیلندر لنسر", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو لنسر", 0, 0),
                    PartData("لنت ترمز عقب لنسر", 0, 0),
                    PartData("دیسک ترمز جلو لنسر", 0, 0),
                    PartData("دیسک ترمز عقب لنسر", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 3, listOf(
                    PartData("کمک فنر جلو لنسر", 0, 0),
                    PartData("کمک فنر عقب لنسر", 0, 0),
                    PartData("سیبک لنسر", 0, 0),
                    PartData("طبق لنسر", 0, 0)
                )),
                CategoryData("electrical", "برق", 4, listOf(
                    PartData("باتری لنسر", 0, 0),
                    PartData("دینام لنسر", 0, 0),
                    PartData("استارت لنسر", 0, 0),
                    PartData("سنسور اکسیژن لنسر", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو لنسر", 0, 0),
                    PartData("سپر عقب لنسر", 0, 0),
                    PartData("چراغ جلو لنسر", 0, 0),
                    PartData("شیشه جلو لنسر", 0, 0),
                    PartData("آینه بغل لنسر", 0, 0)
                ))
            )),
            ModelData("chery_tiggo5", "چری تیگو ۵", "۱۳۹۵ تا کنون", 9, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن تیگو ۵", 0, 0),
                    PartData("فیلتر هوا تیگو ۵", 0, 0),
                    PartData("فیلتر سوخت تیگو ۵", 0, 0),
                    PartData("شمع تیگو ۵", 0, 0),
                    PartData("تسمه تایم تیگو ۵", 0, 0),
                    PartData("واتر پمپ تیگو ۵", 0, 0),
                    PartData("ترموستات تیگو ۵", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو تیگو ۵", 0, 0),
                    PartData("لنت ترمز عقب تیگو ۵", 0, 0),
                    PartData("دیسک ترمز جلو تیگو ۵", 0, 0),
                    PartData("دیسک ترمز عقب تیگو ۵", 0, 0)
                )),
                CategoryData("body", "بدنه", 3, listOf(
                    PartData("سپر جلو تیگو ۵", 0, 0),
                    PartData("سپر عقب تیگو ۵", 0, 0),
                    PartData("چراغ جلو تیگو ۵", 0, 0),
                    PartData("چراغ عقب تیگو ۵", 0, 0),
                    PartData("شیشه جلو تیگو ۵", 0, 0)
                )),
                CategoryData("electrical", "برق", 4, listOf(
                    PartData("باتری تیگو ۵", 0, 0),
                    PartData("دینام تیگو ۵", 0, 0),
                    PartData("استارت تیگو ۵", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 5, listOf(
                    PartData("کمک فنر جلو تیگو ۵", 0, 0),
                    PartData("کمک فنر عقب تیگو ۵", 0, 0),
                    PartData("سیبک فرمان تیگو ۵", 0, 0),
                    PartData("طبق تیگو ۵", 0, 0)
                ))
            )),
            ModelData("geely_emgrand7", "جیلی امگرند ۷", "۱۳۹۳ تا کنون", 10, listOf(
                CategoryData("engine", "موتور", 1, listOf(
                    PartData("فیلتر روغن امگرند ۷", 0, 0),
                    PartData("فیلتر هوا امگرند ۷", 0, 0),
                    PartData("فیلتر سوخت امگرند ۷", 0, 0),
                    PartData("شمع امگرند ۷", 0, 0),
                    PartData("تسمه تایم امگرند ۷", 0, 0),
                    PartData("واتر پمپ امگرند ۷", 0, 0),
                    PartData("ترموستات امگرند ۷", 0, 0)
                )),
                CategoryData("brakes", "ترمز", 2, listOf(
                    PartData("لنت ترمز جلو امگرند ۷", 0, 0),
                    PartData("لنت ترمز عقب امگرند ۷", 0, 0),
                    PartData("دیسک ترمز جلو امگرند ۷", 0, 0),
                    PartData("دیسک ترمز عقب امگرند ۷", 0, 0)
                )),
                CategoryData("electrical", "برق", 3, listOf(
                    PartData("باتری امگرند ۷ ۶۰ آمپر", 0, 0),
                    PartData("دینام امگرند ۷", 0, 0),
                    PartData("استارت امگرند ۷", 0, 0)
                )),
                CategoryData("suspension", "جلوبندی", 4, listOf(
                    PartData("کمک فنر جلو امگرند ۷", 0, 0),
                    PartData("کمک فنر عقب امگرند ۷", 0, 0),
                    PartData("سیبک فرمان امگرند ۷", 0, 0),
                    PartData("طبق امگرند ۷", 0, 0)
                )),
                CategoryData("body", "بدنه", 5, listOf(
                    PartData("سپر جلو امگرند ۷", 0, 0),
                    PartData("سپر عقب امگرند ۷", 0, 0),
                    PartData("چراغ جلو امگرند ۷", 0, 0),
                    PartData("شیشه جلو امگرند ۷", 0, 0),
                    PartData("آینه بغل امگرند ۷", 0, 0)
                ))
            ))
        ))
    )

    // بررسی اینکه آیا داده قبلاً وارد شده
    suspend fun isAlreadySeeded(): Boolean {
        return try {
            val snap = db.collection("brands").limit(1).get().await()
            !snap.isEmpty
        } catch (e: Exception) {
            false
        }
    }

    // seed کردن همه داده‌ها به Firebase
    suspend fun seedAll(onProgress: (String) -> Unit = {}): Int {
        var totalParts = 0
        for (brand in BRANDS) {
            onProgress("در حال وارد کردن ${brand.name}...")
            db.collection("brands").document(brand.id).set(
                mapOf("name" to brand.name, "color" to brand.color, "order" to brand.order)
            ).await()

            for (model in brand.models) {
                onProgress("${brand.name} / ${model.name}")
                db.collection("brands").document(brand.id)
                    .collection("models").document(model.id)
                    .set(mapOf("name" to model.name, "year" to model.year, "order" to model.order))
                    .await()

                for (cat in model.categories) {
                    db.collection("brands").document(brand.id)
                        .collection("models").document(model.id)
                        .collection("categories").document(cat.id)
                        .set(mapOf("name" to cat.name, "order" to cat.order))
                        .await()

                    for (part in cat.parts) {
                        db.collection("brands").document(brand.id)
                            .collection("models").document(model.id)
                            .collection("categories").document(cat.id)
                            .collection("parts").add(
                                mapOf(
                                    "name" to part.name,
                                    "price" to part.price,
                                    "stock" to part.stock,
                                    "description" to ""
                                )
                            ).await()
                        totalParts++
                    }
                }
            }
        }
        return totalParts
    }



    // حذف قطعات قدیمی (ID رندوم Firestore = دقیقاً ۲۰ کاراکتر لاتین)
    suspend fun cleanLegacyParts(onProgress: (String) -> Unit = {}): Int {
        var deleted = 0
        val randomIdRegex = Regex("^[a-zA-Z0-9]{20}$")
        for (brand in BRANDS) {
            for (model in brand.models) {
                for (cat in model.categories) {
                    onProgress("بررسی ${brand.name} / ${model.name} / ${cat.name}")
                    val partsRef = db.collection("brands").document(brand.id)
                        .collection("models").document(model.id)
                        .collection("categories").document(cat.id)
                        .collection("parts")
                    val snap = partsRef.get().await()
                    for (doc in snap.documents) {
                        if (randomIdRegex.matches(doc.id)) {
                            doc.reference.delete().await()
                            deleted++
                        }
                    }
                }
            }
        }
        return deleted
    }

    // پاک کردن و seed مجدد با progress درصدی
    suspend fun forceReseed(onProgress: (String, Int, Int) -> Unit = { _, _, _ -> }): Int {
        val total = BRANDS.sumOf { b -> b.models.sumOf { m -> m.categories.sumOf { c -> c.parts.size } } }
        var current = 0

        for (brand in BRANDS) {
            onProgress("وارد کردن ${brand.name}...", current, total)
            db.collection("brands").document(brand.id)
                .set(mapOf("name" to brand.name, "color" to brand.color, "order" to brand.order))
                .await()
            for (model in brand.models) {
                db.collection("brands").document(brand.id)
                    .collection("models").document(model.id)
                    .set(mapOf("name" to model.name, "year" to model.year, "order" to model.order))
                    .await()
                for (cat in model.categories) {
                    db.collection("brands").document(brand.id)
                        .collection("models").document(model.id)
                        .collection("categories").document(cat.id)
                        .set(mapOf("name" to cat.name, "order" to cat.order))
                        .await()
                    for (part in cat.parts) {
                        // ID ثابت از روی اسم قطعه — جلوگیری از تکراری شدن
                        val partId = part.name.replace(" ", "_")
                            .replace("/", "-")
                            .take(60)
                        db.collection("brands").document(brand.id)
                            .collection("models").document(model.id)
                            .collection("categories").document(cat.id)
                            .collection("parts").document(partId)
                            .set(mapOf("name" to part.name, "price" to part.price, "stock" to part.stock, "description" to ""))
                            .await()
                        current++
                        onProgress("${brand.name} / ${model.name}", current, total)
                    }
                }
            }
        }
        return current
    }

        // seed فقط اگه خالی باشه (برای SplashScreen)
    suspend fun seedIfEmpty() {
        if (!isAlreadySeeded()) {
            seedAll()
        }
    }
}
