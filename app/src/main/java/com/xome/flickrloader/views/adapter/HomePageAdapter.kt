package com.xome.flickrloader.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xome.flickrloader.R
import com.xome.flickrloader.models.Photo
import kotlinx.android.synthetic.main.homepage_row.view.*


class HomePageAdapter  (private val context: Context,
private var myCourseList: List<Photo?>?
) : RecyclerView.Adapter<HomePageAdapter.CourseViewHolder>() {
    private var view: View? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePageAdapter.CourseViewHolder {

        view = LayoutInflater.from(context).inflate(
            R.layout.homepage_row,
            parent,
            false
        )
        return CourseViewHolder(
            view!!
        )

    }

    override fun getItemCount(): Int {
        return myCourseList?.size!!
    }

    override fun onBindViewHolder(holder: HomePageAdapter.CourseViewHolder, position: Int) {
        val imageUrlList = myCourseList?.get(position)
        val imageUrl="http://farm"+imageUrlList!!.farm+".static.flickr.com/"+imageUrlList.server+"/"+imageUrlList.id+"_"+imageUrlList.secret+".jpg"
        holder.imageView.setImageURI(imageUrl)
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
      var imageView = itemView.iv_imageView!!

    }

}