package com.example.apicalldemo



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicalldemo.MarsApi
import com.example.apicalldemo.detail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {


    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    private val _details = MutableLiveData<detail>()

    var size:Int=0

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    val details: LiveData<detail> = _details
    var index=0;
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */

    init {
        getMarsPhotos(index)

    }
    fun launchDetail(){
        index++;
        if(index<size)
        getMarsPhotos(index)

    }


    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos(index:Int) {
        viewModelScope.launch {
            try {
                size=MarsApi.retrofitService.getPhotos().size
                _details.value = MarsApi.retrofitService.getPhotos()[index]
                _status.value = " User detail : ${_details.value!!.name}"
                Log.i("Success","Done")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}