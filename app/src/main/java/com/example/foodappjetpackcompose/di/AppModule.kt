package com.example.foodappjetpackcompose.di

import android.app.Application
import androidx.room.Room
import com.example.foodappjetpackcompose.data.FoodApi
import com.example.foodappjetpackcompose.data.FoodApi.Companion.API_URL
import com.example.foodappjetpackcompose.database.AuthDatabase
import com.example.foodappjetpackcompose.database.MyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): AuthDatabase {
        return Room.databaseBuilder(
            application,
            AuthDatabase::class.java,
            "MyDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(authDatabase: AuthDatabase): MyDao {
        return authDatabase.authDao()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodMenuApiService(
        retrofit: Retrofit,
    ): FoodApi.Service {
        return retrofit.create(FoodApi.Service::class.java)
    }

}