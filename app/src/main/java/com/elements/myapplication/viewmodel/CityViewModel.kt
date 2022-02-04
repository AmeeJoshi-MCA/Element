package com.elements.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.elements.myapplication.adapter.CityAdapter
import com.elements.myapplication.api.repositories.WeatherRepository
import com.elements.myapplication.db.CityRepository
import com.elements.myapplication.model.CityDataListItem
import com.elements.myapplication.utils.Event
import kotlinx.coroutines.launch

class CityViewModel(application: Context, private val repository: CityRepository) : ViewModel(),
    CityAdapter.ItemListener {

    private var listData = MutableLiveData<ArrayList<CityDataListItem>>()
    private var popularWeather: WeatherRepository? = WeatherRepository.getInstance(application)

    private val statusMessage = MutableLiveData<Event<String>>()
    val showDetailActivity = MutableLiveData<Event<CityDataListItem>>()

    fun getData(): MutableLiveData<ArrayList<CityDataListItem>> {
        listData = popularWeather!!.getMutableLiveData()
        return listData
    }

    fun insertCity(cityDataListItem: CityDataListItem) = viewModelScope.launch {
        val newRowId = repository.insert(cityDataListItem)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    override fun onIemClicked(cityDataListItem: CityDataListItem) {
        showDetailActivity.value = Event(cityDataListItem)
    }

}