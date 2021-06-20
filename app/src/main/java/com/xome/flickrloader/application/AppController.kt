package com.xome.flickrloader.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);

    }
}