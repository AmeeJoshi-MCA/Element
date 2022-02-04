package com.elements.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.elements.myapplication.R
import com.elements.myapplication.databinding.ItemCityLayoutBinding
import com.elements.myapplication.model.CityDataListItem

class CityAdapter(
    private var list: MutableList<CityDataListItem>, private val itemListener: ItemListener
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemCityLayoutBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_city_layout, viewGroup, false
        )
        return ViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCityLayoutBinding.model = list[i]
        viewHolder.itemCityLayoutBinding.listener = itemListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var itemCityLayoutBinding: ItemCityLayoutBinding) :
        RecyclerView.ViewHolder(itemCityLayoutBinding.root) {
    }

 interface ItemListener {
        fun onIemClicked(cityDataListItem: CityDataListItem)
 }

}