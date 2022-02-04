package com.elements.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.elements.myapplication.R
import com.elements.myapplication.databinding.ItemCityDetailLayoutBinding
import com.elements.myapplication.model.CityDataListItem

class CityDetailAdapter(
    private var list: MutableList<CityDataListItem>
) : RecyclerView.Adapter<CityDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemCityDetailLayoutBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_city_detail_layout, viewGroup, false
        )
        return ViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCityDetailLayoutBinding.model = list[i]

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var itemCityDetailLayoutBinding: ItemCityDetailLayoutBinding) :
        RecyclerView.ViewHolder(itemCityDetailLayoutBinding.root) {
    }


}