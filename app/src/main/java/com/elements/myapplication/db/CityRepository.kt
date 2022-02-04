package com.elements.myapplication.db

import com.elements.myapplication.model.CityDataListItem

class CityRepository(private val dao: CityDAO) {

    val cities = dao.fetchAllData()

    suspend fun insert(city: CityDataListItem): Long {
        return dao.insertCity(city)
    }

    suspend fun update(city: CityDataListItem): Int {
        return dao.updateCity(city)
    }

    suspend fun delete(city: CityDataListItem): Int {
        return dao.deleteCity(city)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}