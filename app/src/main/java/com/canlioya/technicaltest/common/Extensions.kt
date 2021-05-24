package com.canlioya.technicaltest.common

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Extension function for easily creating custom snacks
 * @param text is the only obligatory parameter.
 * @param length is short by default, can be customized if needed.
 * @param backgroundColor is for customizing background color
 * @param actionText is the text for the action button, if any.
 * This parameter is obligatory if an action is provided.
 * @param action is a function to execute when the action button is clicked
 * Providing an action without and actionText @throws IllegalArgumentException
 * to warn the developer
 */
fun View.showSnack(@StringRes text: Int,
                   length : Int = Snackbar.LENGTH_SHORT,
                   @ColorRes backgroundColor : Int? = null,
                   @StringRes actionText : Int? = null,
                   action : (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, text, length)
    backgroundColor?.let {
        snackBar.view.setBackgroundColor(resources.getColor(it))
    }
    if(action != null) {
        if(actionText == null) {
            throw IllegalArgumentException("If you put a snack action, you should also provide a action text")
        } else {
            snackBar.setAction(actionText) {
                action
            }
        }
    }
    snackBar.show()
}


private var toast : Toast? = null

/**
 * Extension function for easily creating
 * a short toast
 * @param message
 */
fun Context.shortToast(message: String) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}

/**
 * Extension function for easily creating
 * a short toast
 * @param message
 */
fun Context.shortToast(@StringRes message: Int) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}

/**
 * Extension function for easily creating
 * a long toast
 * @param message
 */
fun Context.longToast(message: String) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast?.show()
}

/**
 * Extension function for easily creating
 * a long toast
 * @param message
 */
fun Context.longToast(@StringRes message: Int) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast?.show()
}

