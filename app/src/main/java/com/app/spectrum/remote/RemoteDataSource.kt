package com.app.spectrum.remote

/**
 * Created by amresh on 26/11/2019
 */
interface RemoteDataSource {
    fun retrieveMuseums(callback: OperationCallback)
    fun cancel()

}