package com.example.foodappjetpackcompose.ui.repository

import com.example.foodappjetpackcompose.data.FoodApi
import com.example.foodappjetpackcompose.data.FoodItem
import com.example.foodappjetpackcompose.data.FoodResponse
import com.example.foodappjetpackcompose.data.MealsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(private val foodApi: FoodApi) {

    private var foodListItem: List<FoodItem>? = null

    suspend fun getFoodList(): List<FoodItem> = withContext(Dispatchers.IO) {
        var foodListItem = foodListItem

        if (foodListItem == null) {
            foodListItem = foodApi.getFoodItems().mapListToItems()
            this@FoodRepository.foodListItem = foodListItem
        }

        return@withContext foodListItem
    }


    suspend fun getMealsOfFood(foodID: String) = withContext(Dispatchers.IO) {
        val foodName = getFoodList().first { it.id == foodID }.name
        return@withContext foodApi.getMealsByCategory(foodName).mapMealsToItems()
    }


}

private fun MealsResponse.mapMealsToItems(): List<FoodItem> {
    return this.meals.map { category ->
        FoodItem(
            id = category.id,
            name = category.name,
            thumbnailUrl = category.thumbnailUrl
        )
    }

}

private fun FoodResponse.mapListToItems(): List<FoodItem> {
    return this.categories.map { item ->
        FoodItem(
            id = item.id,
            name = item.name,
            description = item.description,
            thumbnailUrl = item.thumbnailUrl
        )
    }

}
