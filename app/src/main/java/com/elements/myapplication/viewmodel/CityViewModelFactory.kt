package com.elements.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elements.myapplication.application.MyApplication
import com.elements.myapplication.db.CityRepository
import java.lang.IllegalArgumentException

class CityViewModelFactory(
    private val repository: CityRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val context = MyApplication.appContext

        if(modelClass.isAssignableFrom(CityViewModel::class.java)){
            return CityViewModel(context,repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }


}