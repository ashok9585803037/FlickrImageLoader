package com.xome.flickrloader.`interface`

import com.xome.flickrloader.configs.Constants
import com.xome.flickrloader.models.FlickrImageResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APICallInterface {
    @GET("rest/")
    fun getImages(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String,
        @Query("safe_search") safe_search: String,
        @Query("text") text: String,
    ): Call<FlickrImageResponse>

    companion object {
        private var interfaceRef: APICallInterface? = null
        fun getAPICallInstance(): APICallInterface {
            return interfaceRef ?: synchronized(this) {
                interfaceRef ?: Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    ).build().create(APICallInterface::class.java).also { interfaceRef =
                        it }
            }
        }
    }

}