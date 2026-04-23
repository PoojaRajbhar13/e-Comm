package com.example.myecomartapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myecomartapp.data.local.converter.Converters
import com.example.myecomartapp.data.local.dao.CommonDao
import com.example.myecomartapp.domain.remote.Product


@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract val favouriteDao: CommonDao
}
