package com.xome.flickrloader.views.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.xome.flickrloader.R
import com.xome.flickrloader.configs.Constants
import com.xome.flickrloader.models.Photo
import com.xome.flickrloader.utils.GridSpacingItemDecoration
import com.xome.flickrloader.utils.isNetworkAvailable
import com.xome.flickrloader.utils.toast
import com.xome.flickrloader.viewmodels.HomeViewModel
import com.xome.flickrloader.views.adapter.HomePageAdapter
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var photoList: MutableList<Photo?>? = null
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var homePageAdapter: HomePageAdapter
    val delay = 1000L
    var lastTextTime = 0L
    private val noColumn = 3
    val handler = Handler(Looper.getMainLooper())
    lateinit var inputFinishChecker: Runnable
    var text: String = ""
    val tag = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        initViewModel()
        if (isNetworkAvailable()) {
            text = "kittens"
            progressBar.visibility = View.VISIBLE

            homeViewModel.callImageList(
                Constants.METHOD,
                Constants.APIKEY,
                Constants.FORMAT,
                Constants.NO_JSON_CALL,
                Constants.SEARCH,
                text
            )

        } else {
            progressBar.visibility = View.GONE
            rl_flickerImageView.visibility = View.GONE
            toast("Check your internet connection")

        }
        val spanCount = 3 // 3 columns
        val spacing = 30 // 30px

        val includeEdge = true
        linearLayoutManager = GridLayoutManager(this, noColumn)
        rl_flickerImageView.layoutManager = linearLayoutManager
        rl_flickerImageView.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                spacing,
                includeEdge,
                0
            )
        )
        search_view.setIconifiedByDefault(false)
        search_view.requestFocus()
        search_view.setOnQueryTextFocusChangeListener { v, hasFocus ->
        }
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacks(inputFinishChecker)
                if (newText?.length!! > 0) {
                    lastTextTime = System.currentTimeMillis()
                    text = newText
                    handler.postDelayed(inputFinishChecker, delay)
                } else {
                    text = "kittens"
                    handler.postDelayed(inputFinishChecker, delay)
                }
                return false
            }

        })

        inputFinishChecker = Runnable {
            if (System.currentTimeMillis() > (lastTextTime + delay - 500)) {
                progressBar.visibility = View.VISIBLE
                empty_txt.visibility = View.GONE
                if (isNetworkAvailable()) {
                    homeViewModel.callImageList(
                        Constants.METHOD,
                        Constants.APIKEY,
                        Constants.FORMAT,
                        Constants.NO_JSON_CALL,
                        Constants.SEARCH,
                        text
                    )
                } else {
                    progressBar.visibility = View.GONE
                    toast("Check your internet connection")
                }


            }
        }
    }

    /*initiate the view model here*/
    private fun initViewModel() {
        homeViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                HomeViewModel::class.java
            )

        homeViewModel.getPhotosLiveData().observe(this, { t ->
            if (t?.stat.equals("ok")) {
                photoList?.clear()
                progressBar.visibility = View.GONE
                photoList = t.photos.childData as MutableList<Photo?>?
                if (photoList!!.isNotEmpty() && photoList!!.size > 0) {
                    empty_txt.visibility = View.GONE
                    rl_flickerImageView.visibility = View.VISIBLE
                    setAdapter()
                } else {
                    rl_flickerImageView.visibility = View.GONE
                    empty_txt.visibility = View.VISIBLE
                }
            } else {
                progressBar.visibility = View.GONE
                rl_flickerImageView.visibility = View.GONE
                toast("Internal server error")
            }

        })

        homeViewModel.getFailureLiveData().observe(this, { t ->
            progressBar.visibility = View.GONE
            toast(t?.message!!)
        })
    }

    private fun setAdapter() {
        try {
            homePageAdapter = HomePageAdapter(this, photoList!!)
            rl_flickerImageView.adapter = homePageAdapter
        } catch (e: Exception) {
            Log.v(tag, e.message.toString())
        }
    }
}