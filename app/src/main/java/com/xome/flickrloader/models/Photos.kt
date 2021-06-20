package com.xome.flickrloader.models


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("page")
    var page: Int,
    @SerializedName("pages")
    var pages: Int,
    @SerializedName("perpage")
    var perpage: Int,
    @SerializedName("photo")
//    var photo: List<Photo>,
    val childData: List<Photo?>? = null,
    @SerializedName("total")
    var total: Int
)