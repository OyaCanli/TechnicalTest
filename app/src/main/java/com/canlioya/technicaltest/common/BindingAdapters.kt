package com.canlioya.technicaltest.common

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.airbnb.lottie.LottieAnimationView
import com.canlioya.technicaltest.R

/**
 * BindingAdapter for easily shifting the visibility of
 * views with a boolean
 * @param visible
 */
@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.GONE
}

/**
 * Binding adapter for showing or stopping Lottie animation
 * @param showAnim
 */
@BindingAdapter("showAnim")
fun LottieAnimationView.showAnimation(showAnim : Boolean){
    if(showAnim) {
        playAnimation()
    } else {
        cancelAnimation()
    }
}

/**
 * BindingAdapter for loading images with Coil library
 */
@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl
            .toUri()
            .buildUpon()
            .scheme("https")
            .build()
        this.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}