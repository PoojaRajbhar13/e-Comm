package com.example.myecomartapp.data.local.converter

import androidx.room.TypeConverter
import com.example.myecomartapp.domain.remote.Dimensions
import com.example.myecomartapp.domain.remote.Meta
import com.example.myecomartapp.domain.remote.Review
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            try {
                json.decodeFromString(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun fromDimensions(value: Dimensions?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toDimensions(value: String?): Dimensions? {
        return value?.let {
            try {
                json.decodeFromString(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    @TypeConverter
    fun fromMeta(value: Meta?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toMeta(value: String?): Meta? {
        return value?.let {
            try {
                json.decodeFromString(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    @TypeConverter
    fun fromReviewList(value: List<Review>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toReviewList(value: String?): List<Review>? {
        return value?.let {
            try {
                json.decodeFromString(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
