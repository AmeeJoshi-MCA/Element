package com.elements.myapplication.db


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elements.myapplication.application.MyApplication
import com.elements.myapplication.model.City
import com.elements.myapplication.model.CityDataListItem
import com.elements.myapplication.viewmodel.CityViewModelFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class CityDatabaseTest : TestCase() {

    // get reference to the Database and it's class

    private lateinit var dao: CityDAO
    private lateinit var repository: CityRepository
    private lateinit var factory: CityViewModelFactory

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        dao = CityDatabase.getInstance(MyApplication.appContext).cityDAO
        repository = CityRepository(dao)
        factory = CityViewModelFactory(repository)
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadLanguage(): Unit = runBlocking {
        val cityDataListItem = CityDataListItem(
            id = 1,
            date = System.currentTimeMillis().toString(),
            temp = 72.03,
            tempType = "F",
            tempConvertVal= 11,
            city = City(
                name = "Amsterdam",
                picture = "https://firebasestorage.googleapis.com/v0/b/mobile-assignment-server.appspot.com/o/barcelona.jpg?alt=media&token=78363b9d-3c33-432c-9bf8-f60786153b13"
            )
        )
        repository.insert(cityDataListItem)
        val city = dao.getAllCityDescOrder()
        assertThat(city.contains(cityDataListItem)).isTrue()

    }

}