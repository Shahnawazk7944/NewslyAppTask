package com.example.newsly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsly.domain.model.News


@Database(entities = [News::class], version = 1)
@TypeConverters(NewslyTypeConvertor::class)
abstract class NewslyDatabase: RoomDatabase() {

    abstract val newslyDao: NewslyDao

}