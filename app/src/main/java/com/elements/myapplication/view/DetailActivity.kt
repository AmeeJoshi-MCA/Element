package com.elements.myapplication.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.elements.myapplication.adapter.CityAdapter
import com.elements.myapplication.adapter.CityDetailAdapter
import com.elements.myapplication.databinding.DetailActivityBinding
import com.elements.myapplication.db.CityDAO
import com.elements.myapplication.db.CityDatabase
import com.elements.myapplication.model.CityDataListItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding
    private var context: Context? = null
    private lateinit var currentCity: CityDataListItem
    private lateinit var dao: CityDAO
    private lateinit var cityDetailAdapter: CityDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        dao = CityDatabase.getInstance(application).cityDAO

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentCity = bundle.getSerializable("currentCity") as CityDataListItem
            binding.apply {
                model = currentCity
            }
        }

        init()

        binding.imageBack.setOnClickListener {
            finish()
        }

    }

    private fun init(){

        val cityLiveData: List<CityDataListItem> = dao.getSelectedCity(currentCity.city.name)
        System.out.println(""+cityLiveData.size)
        cityDetailAdapter = CityDetailAdapter(cityLiveData as MutableList<CityDataListItem>)
        binding.recyclerViewCityTemp.adapter = cityDetailAdapter

    }


}