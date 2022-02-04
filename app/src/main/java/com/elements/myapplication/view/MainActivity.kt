package com.elements.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.elements.myapplication.R
import com.elements.myapplication.adapter.CityAdapter
import com.elements.myapplication.databinding.ActivityMainBinding
import com.elements.myapplication.db.CityDAO
import com.elements.myapplication.db.CityDatabase
import com.elements.myapplication.db.CityRepository
import com.elements.myapplication.model.CityDataListItem
import com.elements.myapplication.utils.CheckValidation
import com.elements.myapplication.viewmodel.CityViewModel
import com.elements.myapplication.viewmodel.CityViewModelFactory
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {

    private lateinit var cityViewModel: CityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var listCity: MutableList<CityDataListItem>
    private lateinit var listCityFilter: MutableList<CityDataListItem>

    // Room DB
    private lateinit var dao: CityDAO
    private lateinit var repository: CityRepository
    private lateinit var factory: CityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        // Set custom action bar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar_layout)

        dao = CityDatabase.getInstance(application).cityDAO
        repository = CityRepository(dao)
        factory = CityViewModelFactory(repository)

        init()

    }

    private fun init() {

        cityViewModel = ViewModelProvider(this, factory)[CityViewModel::class.java]
        binding.apply {
            viewModel = cityViewModel
        }
        binding.lifecycleOwner = this

        listCity = mutableListOf()
        listCityFilter= mutableListOf()

        cityAdapter = CityAdapter(listCity,cityViewModel)

        setCityData()

        binding.swipeContainer.setOnRefreshListener {
            setCityData()
        }

        cityViewModel.showDetailActivity.observe(this) {
            it.getContentIfNotHandled()?.let { cityitem ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("currentCity", cityitem)
                startActivity(intent)
            }
        }
    }

    /*
      1 If internet available then api will be called and list will be shown.
      2 If there is no internet then city list will be shown from database.
    */

    private fun setCityData() {
        binding.swipeContainer.isRefreshing = true
        binding.progressBar.visibility = View.VISIBLE

        val divider = DividerItemDecoration(
            binding.recyclerViewCity.getContext(),
            DividerItemDecoration.VERTICAL
        )
        divider.setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.line_separater)!!)
        binding.recyclerViewCity.addItemDecoration(divider)

        if (CheckValidation.isConnected(this)) {
            cityViewModel.getData().observe(this) { t ->
                listCity.clear()

                t?.let { listCity.addAll(it) }

                convertTemperatureToCelsius()

                cityViewModel.clearAll()
                for (city in listCity) {
                    cityViewModel.insertCity(city)
                }

                val cityFilterData: LiveData<MutableList<CityDataListItem>> = repository.cities

                cityFilterData.observe(this) { cityList ->
                    listCity.clear()
                    listCity.addAll(cityList)
                    binding.recyclerViewCity.adapter = cityAdapter
                    binding.progressBar.visibility = View.GONE

                }
            }
        } else {
            // Internet connection is not available
            val cityFilterData: LiveData<MutableList<CityDataListItem>> = repository.cities
            cityFilterData.observe(this) { cityList -> //Update your UI here from DB.
                listCity.clear()
                listCity.addAll(cityList)
                binding.recyclerViewCity.adapter = cityAdapter
                binding.progressBar.visibility = View.GONE

            }
        }



        binding.swipeContainer.isRefreshing = false

    }

    private fun convertTemperatureToCelsius(){
        for(city in listCity){
            if (city.tempType == "F") {
                val Celsius = ((city.temp - 32) / 1.8).roundToLong()
                city.tempConvertVal=Celsius
            } else if(city.tempType == "K"){
                val Celsius = (city.temp - 273.15).roundToLong()
                city.tempConvertVal=Celsius
            }else{
                city.tempConvertVal=city.temp.toLong()
            }

        }
        System.out.println("size is " + listCity.size)
    }

}