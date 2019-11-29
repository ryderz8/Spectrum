package com.app.spectrum.remote



object Injection {

    //MuseumRepository could be a singleton
    fun providerRepository():RemoteDataSource{
        return AppRepository()
    }
}