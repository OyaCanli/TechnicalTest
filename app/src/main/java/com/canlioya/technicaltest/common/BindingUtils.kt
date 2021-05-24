@file:JvmName("BindingUtils")

package com.canlioya.technicaltest.common

/**
 * If user's name is null or contains the text null,
 * return an empty string, otherwise add "By" to the beginning
 */
fun addBy(userName: String?): String {
    return if (userName.isNullOrBlank() || userName == "null") "" else "By $userName"
}
