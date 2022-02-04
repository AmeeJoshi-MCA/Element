package com.elements.myapplication.view

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.elements.myapplication.model.City
import com.elements.myapplication.model.CityDataListItem
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DetailActivityUITestCase {

    private lateinit var scenario: ActivityScenario<DetailActivity>

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun testDetailActivityWithCityData() {
        // Your test code goes here.
        val cityDataListItem = CityDataListItem(
            id = 1,
            date = "2022-02-01T12:00:00+00:00",
            temp = 72.03,
            tempType = "F",
            tempConvertVal= 11,
            city = City(
                name = "Amsterdam",
                picture = "https://firebasestorage.googleapis.com/v0/b/mobile-assignment-server.appspot.com/o/barcelona.jpg?alt=media&token=78363b9d-3c33-432c-9bf8-f60786153b13"
            )
        )

        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java)
            .putExtra("currentCity", cityDataListItem)
        scenario = launchActivity(intent)

    }

}