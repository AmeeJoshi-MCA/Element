package com.elements.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elements.myapplication.model.CityDataListItem

@Database(entities = [CityDataListItem::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract val cityDAO: CityDAO

    companion object {
        @Volatile
        private var INSTANCE: CityDatabase? = null
        fun getInstance(context: Context): CityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CityDatabase::class.java,
                        "city_data_database"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }
    }
}

