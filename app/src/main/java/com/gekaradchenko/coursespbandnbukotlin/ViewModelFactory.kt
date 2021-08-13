package com.gekaradchenko.coursespbandnbukotlin

import android.app.Application

import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            return ViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}