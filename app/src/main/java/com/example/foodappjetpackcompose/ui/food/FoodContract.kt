package com.example.foodappjetpackcompose.ui.food

import com.example.foodappjetpackcompose.data.FoodItem

class FoodContract {

    data class State(
        val foodList: List<FoodItem> = listOf(),
        val isLoading: Boolean = false,
    )

    sealed class Effect() {
        object DataWasLoad : Effect()
    }
}