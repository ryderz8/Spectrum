package com.app.spectrum.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.spectrum.R
import com.bumptech.glide.Glide

/**
 * Created by amresh on 29/11/2019
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context.applicationContext)
        .load(imageUrl)
        .error(R.drawable.ic_launcher_foreground)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}
