package com.elements.myapplication.api.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.elements.myapplication.api.ApiClient
import com.elements.myapplication.api.ApiService
import com.elements.myapplication.model.CityDataList
import com.elements.myapplication.model.CityDataListItem
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(app: Context){

    private var instanceApi: ApiService

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }

    companion object{
        private var weatherRepository: WeatherRepository?=null
        @Synchronized
        fun getInstance(app: Context): WeatherRepository? {
            if (weatherRepository == null) {
                weatherRepository = WeatherRepository(app)
            }
            return weatherRepository
        }
    }

    fun getMutableLiveData(): MutableLiveData<ArrayList<CityDataListItem>> {

        val mutableLiveData = MutableLiveData<ArrayList<CityDataListItem>>()

        instanceApi.getWeatherDetail().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseBody = response.body()?.string()
                try {
                    val usersResponse = Gson().fromJson(responseBody.toString(), CityDataList::class.java)
                    usersResponse?.sortBy { it.city.name}
                    usersResponse.let { mutableLiveData.value = it as ArrayList<CityDataListItem> }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        return mutableLiveData
    }

}