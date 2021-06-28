package com.haroldcalayan.githubrepository.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val networkStatusVisibility = MutableLiveData<Boolean>()
    val showProgressBar = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        throwable.printStackTrace()
        showProgressBar.postValue(false)
    }

    private val exceptionHandlerForBackgroundTask =
        CoroutineExceptionHandler { _, throwable: Throwable ->
            throwable.printStackTrace()
        }

    fun invoke(apiCall: suspend () -> Unit) {
        showProgressBar.postValue(true)
        viewModelScope.launch(exceptionHandler) {
            apiCall.invoke()
            showProgressBar.postValue(false)
        }
    }

    fun invokeAtBackground(apiCall: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandlerForBackgroundTask) { apiCall.invoke() }
    }

    fun apiCallWithoutExceptionHandling(apiCall: suspend () -> Unit) {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            apiCall.invoke()
            showProgressBar.postValue(false)
        }
    }

    fun showNetworkStatus(show: Boolean) = networkStatusVisibility.postValue(show)

}