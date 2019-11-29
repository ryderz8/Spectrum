package com.app.spectrum.adapter

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.spectrum.R
import com.bumptech.glide.Glide

/**
 * Created by amresh on 29/11/2019
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .error(R.mipmap.ic_launcher)
        .placeholder(getCircularProgressDrawable(imageView.context))
        .into(imageView)
}
fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = context.resources.getDimension(R.dimen.dp_1)
    circularProgressDrawable.centerRadius = context.resources.getDimension(R.dimen.dp_10)
    circularProgressDrawable.start()
    return circularProgressDrawable
}