package com.app.spectrum.remote

import android.util.Log
import com.app.spectrum.model.CompanyDataModel
import com.app.spectrum.model.ResponseData
import com.app.spectrum.ui.CompanyFragment.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**ø
 * Created by amresh on 29/11/2019
 */
class AppRepository :RemoteDataSource {

    private var call: Call<List<CompanyDataModel>>? = null

    override fun retrieveMuseums(callback: OperationCallback) {
        call = ApiClient.build()?.company()
        call?.enqueue(object : Callback<List<CompanyDataModel>> {
            override fun onFailure(call: Call<List<CompanyDataModel>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<List<CompanyDataModel>>,
                response: Response<List<CompanyDataModel>>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        Log.v(TAG, "data ${it}")
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}