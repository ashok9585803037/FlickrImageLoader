package com.xome.flickrloader.models


import com.google.gson.annotations.SerializedName

data class FlickrImageResponse(
    @SerializedName("photos")
    var photos: Photos,
    @SerializedName("stat")
    var stat: String
)