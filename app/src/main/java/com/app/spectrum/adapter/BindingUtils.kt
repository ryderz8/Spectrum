package com.app.spectrum.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.interfaces.BindableAdapter

/**
 * Created by amresh on 29/11/2019
 */
@BindingAdapter("itemData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        if (data != null)
            (recyclerView.adapter as BindableAdapter<T>).setData(data)
    }
}