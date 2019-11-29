package com.app.spectrum.remote

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}