package com.example.ui.screens

fun Long.formatPrice(): String {
    if (this <= 0) return "توافقی"
    return this.toString()
        .reversed()
        .chunked(3)
        .joinToString("٫")
        .reversed()
}
