package com.app.spectrum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.spectrum.model.CompanyDataModel
import com.app.spectrum.model.MemberDataModel
import com.app.spectrum.remote.OperationCallback
import com.app.spectrum.remote.RemoteDataSource

/**
 * Created by amresh on 29/11/2019
 */
class CommonViewModel(private val repository: RemoteDataSource) : ViewModel() {

    val companyData: MutableLiveData<List<CompanyDataModel>> = MutableLiveData()

    val memberData: MutableLiveData<List<MemberDataModel>> = MutableLiveData()

    val isViewLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onMessageError: MutableLiveData<Any> = MutableLiveData()

    val isEmptyList: MutableLiveData<Boolean> = MutableLiveData()


    fun loadCompanyData() {
        isViewLoading.postValue(true)
        repository.retrieveMuseums(object : OperationCallback {
            override fun onError(obj: Any?) {
                isViewLoading.postValue(false)
                onMessageError.postValue(obj)
            }

            override fun onSuccess(obj: Any?) {
                isViewLoading.postValue(false)

                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        isEmptyList.postValue(true)
                    } else {
                        companyData.value = obj as List<CompanyDataModel>
                    }
                }
            }
        })
    }

    fun loadMemberData(companyDataModel: CompanyDataModel) {
        isViewLoading.postValue(true)
        companyDataModel.members.let {
            if (it.isNotEmpty()) {
                isViewLoading.postValue(false)
                memberData.value = it
            } else {
                isEmptyList.postValue(true)
            }

        }
    }

}