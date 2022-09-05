package com.example.foodappjetpackcompose.data

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodApi @Inject constructor(private val service: Service) {

    suspend fun getFoodItems(): FoodResponse = service.getFoodCategories()

    suspend fun getMealsByCategory(categoryId: String): MealsResponse =
        service.getMealsByCategory(categoryId)


    interface Service {
        @GET("categories.php")
        suspend fun getFoodCategories(): FoodResponse

        @GET("filter.php")
        suspend fun getMealsByCategory(@Query("c") categoryId: String): MealsResponse
    }

    companion object {
        const val API_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}