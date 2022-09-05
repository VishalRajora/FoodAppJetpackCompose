package com.example.foodappjetpackcompose.ui.foodDetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappjetpackcompose.ui.food.FoodContract
import com.example.foodappjetpackcompose.ui.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val foodRepo: FoodRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            getFoodMeals()
        }
    }


    var state by mutableStateOf(
        FoodDetailContract.State(
            null, listOf(),
            true
        )
    )
        private set

    var effects = Channel<FoodDetailContract.Effect>(Channel.UNLIMITED)
        private set

    private suspend fun getFoodMeals() {
        val foodID = stateHandle.get<String>("foodItemID")
            ?: throw IllegalStateException("No categoryId was passed to destination.")

        val mealItem = foodRepo.getMealsOfFood(foodID)

        Timber.i("Food $mealItem")

        state = state.copy(category = null, categoryFoodItems = mealItem, isLoading = false)
    }

}