package com.example.foodappjetpackcompose.ui.foodDetail

import com.example.foodappjetpackcompose.data.FoodItem

class FoodDetailContract {

    data class State(
        val category: FoodItem?,
        val categoryFoodItems: List<FoodItem>,
        val isLoading: Boolean = false,
    )

    sealed class Effect() {
        object DataWasLoad : Effect()
    }
}