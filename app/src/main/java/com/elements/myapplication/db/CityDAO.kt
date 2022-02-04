package com.elements.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elements.myapplication.model.CityDataListItem

@Dao
interface CityDAO {

    @Insert
    suspend fun insertCity(cityDataListItem: CityDataListItem) : Long

    @Update
    suspend fun updateCity(cityDataListItem: CityDataListItem) : Int

    @Delete
    suspend fun deleteCity(cityDataListItem: CityDataListItem) : Int

    @Query("DELETE FROM city_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM city_data_table group by name")
    fun fetchAllData(): LiveData<MutableList<CityDataListItem>>

    @Query("SELECT * FROM city_data_table ORDER BY name DESC")
    suspend fun getAllCityDescOrder(): List<CityDataListItem>


    @Query("Select * from city_data_table where name LIKE :nameCity ORDER BY date")
    fun getSelectedCity(nameCity: String?): List<CityDataListItem>

}