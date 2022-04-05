package com.gonzalezcs.covidnewmodule.di

import android.content.Context
//import androidx.room.Room
import au.com.carsales.basemodule.context
//import com.gonzalezcs.covidnewmodule.data.AppDatabase
import com.gonzalezcs.covidnewmodule.data.network.CovidDateClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    private val base_url = "https://covid-19-statistics.p.rapidapi.com/"


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okhttpClient = OkHttpClient()
        return Retrofit.Builder().baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCovidDateClient(retrofit: Retrofit): CovidDateClient {
        return retrofit.create(CovidDateClient::class.java)
    }

    /*@Singleton
    @Provides
    fun provideDatabaseInstance(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "calendar-database"
        ).build()
    }*/


}