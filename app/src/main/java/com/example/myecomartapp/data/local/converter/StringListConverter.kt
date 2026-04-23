package com.example.myecomartapp.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class StringListConverter {

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String{
        return json.encodeToString(value)

    }


    @TypeConverter
    fun toStringList(value: String): List<String>{
        try{
            return json.decodeFromString(value)
        }catch(e: Exception){
            return emptyList()
        }

    }
}