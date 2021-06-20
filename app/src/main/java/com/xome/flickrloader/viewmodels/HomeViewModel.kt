package com.xome.flickrloader.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xome.flickrloader.`interface`.APICallInterface
import com.xome.flickrloader.models.ErrorResponse
import com.xome.flickrloader.models.FlickrImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var flickrImageResponseLiveData = MutableLiveData<FlickrImageResponse>()

    private var failureLiveData = MutableLiveData<ErrorResponse>()

    fun callImageList(
        method: String,
        apikey: String,
        format: String,
        noJsonCall: String,
        search: String,
        text: String
    ) {
        val callObj = APICallInterface.getAPICallInstance().getImages(
            method, apikey, format, noJsonCall, search, text

        )
        callObj.enqueue(object : Callback<FlickrImageResponse> {
            override fun onFailure(call: Call<FlickrImageResponse>, t: Throwable) {
                failureLiveData.postValue(ErrorResponse(false, t.message))
            }

            override fun onResponse(
                call: Call<FlickrImageResponse>,
                response: Response<FlickrImageResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    flickrImageResponseLiveData.postValue(result!!)
                } else {
                    failureLiveData.postValue(ErrorResponse(false, response.message()))
                }
            }
        })
    }

    fun getPhotosLiveData(): LiveData<FlickrImageResponse> {
        return flickrImageResponseLiveData
    }

    fun getFailureLiveData(): LiveData<ErrorResponse> {
        return failureLiveData
    }
}