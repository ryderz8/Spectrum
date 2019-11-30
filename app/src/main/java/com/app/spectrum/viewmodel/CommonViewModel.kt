package com.app.spectrum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.spectrum.model.CompanyDataModel
import com.app.spectrum.model.MemberDataModel
import com.app.spectrum.remote.OperationCallback
import com.app.spectrum.remote.RemoteDataSource

/**
 * Created by amresh on 29/11/2019
 */
class CommonViewModel(private val repository: RemoteDataSource) : ViewModel(){

    //private val _companyData = MutableLiveData<List<CompanyDataModel>>().apply { value = emptyList() }
   // val companyData: LiveData<List<CompanyDataModel>> = _companyData
    val companyData :MutableLiveData<List<CompanyDataModel>> = MutableLiveData()
    val memberData : MutableLiveData<List<MemberDataModel>> = MutableLiveData()

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError= MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList= MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadCompanyData(){
        _isViewLoading.postValue(true)
        repository.retrieveMuseums(object: OperationCallback {
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( obj)
            }

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)

                if(obj!=null && obj is List<*>){
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        companyData.value= obj as List<CompanyDataModel>
                    }
                }
            }
        })
    }

    fun loadMemberData(companyDataModel: CompanyDataModel){
        _isViewLoading.postValue(true)
        companyDataModel.members.let {
            if(it.isNotEmpty()){
                _isViewLoading.postValue(false)
                memberData.value = it
            }else{
                _isEmptyList.postValue(true)
            }

        }
    }

}