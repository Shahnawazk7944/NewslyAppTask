package com.example.newsly.di

import android.app.Application
import androidx.room.Room
import com.example.newsly.data.local.NewslyDao
import com.example.newsly.data.local.NewslyDatabase
import com.example.newsly.data.local.NewslyTypeConvertor
import com.example.newsly.data.remote.NewslyApi
import com.example.newsly.data.repository.NewslyRepositoryImpl
import com.example.newsly.domain.repository.NewslyRepository
import com.example.newsly.domain.usecases.BookmarkNews
import com.example.newsly.domain.usecases.DeleteNews
import com.example.newsly.domain.usecases.GetBookmarkedNews
import com.example.newsly.domain.usecases.GetNews
import com.example.newsly.domain.usecases.IsNewsBookmarked
import com.example.newsly.domain.usecases.NewslyUseCases
import com.example.newsly.util.Constants.BASE_URL
import com.example.newsly.util.Constants.NEWSLY_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNewsApi(): NewslyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewslyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewslyApi,
        newsDao: NewslyDao
    ): NewslyRepository = NewslyRepositoryImpl(newsApi, newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewslyRepository,
    ): NewslyUseCases {
        return NewslyUseCases(
            getNews = GetNews(newsRepository),
            bookmarkNews = BookmarkNews(newsRepository),
            deleteNews = DeleteNews(newsRepository),
            getBookmarkedNews = GetBookmarkedNews(newsRepository),
            isNewsBookmarked = IsNewsBookmarked(newsRepository),

        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewslyDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewslyDatabase::class.java,
            name = NEWSLY_DATABASE_NAME
        ).addTypeConverter(NewslyTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewslyDatabase
    ): NewslyDao = newsDatabase.newslyDao

}