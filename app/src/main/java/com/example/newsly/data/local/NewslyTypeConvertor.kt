package com.example.newsly.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newsly.domain.model.Source


@ProvidedTypeConverter
class NewslyTypeConvertor {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        return source.split(",").let { sourceArray ->
            Source(sourceArray[0], sourceArray[1])
        }
    }
}