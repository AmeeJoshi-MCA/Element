package com.elements.myapplication.model

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.lang.Exception
import java.time.format.DateTimeFormatter
import kotlin.math.roundToLong

@Entity(tableName = "city_data_table")
data class CityDataListItem(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "city_id")
    var id: Int,

    @Embedded
    @Expose
    @SerializedName("city")
    val city: City,

    @Expose
    @ColumnInfo(name = "date")
    val date: String,

    @Expose
    @ColumnInfo(name = "temp")
    val temp: Double,

    @Expose
    @ColumnInfo(name = "temp_type")
    val tempType: String,

    @Expose
    @ColumnInfo(name = "temp_in_Celsius")
    var tempConvertVal: Long

): Serializable

data class City(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "picture")
    val picture: String

):Serializable

@BindingAdapter("app:picture", "app:progress")
fun setPicture(image: ImageView, imageUrl: String?, progress: ProgressBar) {
    progress.visibility = View.VISIBLE
    Picasso.get().load(imageUrl)
        .into(image, object : Callback {
            override fun onSuccess() {
                progress.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
                progress.visibility = View.GONE
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("app:date")
fun setDate(textview: TextView, releaseDate: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val date = formatter.parse(releaseDate)
    val desiredFormat = DateTimeFormatter.ofPattern("dd-MMM-yy").format(date)
    textview.text = desiredFormat.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("app:time")
fun setDateTime(textview: TextView, releaseDate: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    val date = formatter.parse(releaseDate)
    val desiredFormat = DateTimeFormatter.ofPattern("hh:mm a").format(date)
    textview.text = desiredFormat.toString()
}

@BindingAdapter("temp")
fun setTemperature(textview: TextView, temp: Double) {
        val textVal= "$temp \u2103"
        textview.text = textVal
}


