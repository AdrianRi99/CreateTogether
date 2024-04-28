package com.example.createtogether.db.converters

//import androidx.room.TypeConverter
//import com.example.secretcrush.utility.models.LatLng
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class ConvertersSecretCrush {
//
//    @TypeConverter
//    fun toLocationJson(location: LatLng) : String {
//        val gson = Gson()
//        return gson.toJson(location,  object : TypeToken<LatLng>() {}.type)
//    }
//
//    @TypeConverter
//    fun fromLocationJson(json: String): LatLng {
//        val list  = object : TypeToken<LatLng>() {}.type
//        return Gson().fromJson(json, list)
//
//    }
//
//}