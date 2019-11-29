package com.app.spectrum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.spectrum.remote.RemoteDataSource

class ViewModelFactory(private val repository:RemoteDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommonViewModel(repository) as T
    }
}