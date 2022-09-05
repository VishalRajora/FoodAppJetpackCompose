package com.example.foodappjetpackcompose.ui.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappjetpackcompose.data.FoodItem
import com.example.foodappjetpackcompose.ui.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepo: FoodRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            getFoodItem()
        }
    }

    var state by mutableStateOf(
        FoodContract.State(
            foodList = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<FoodContract.Effect>(Channel.UNLIMITED)
        private set

    private suspend fun getFoodItem() {
        val foodList: List<FoodItem> = foodRepo.getFoodList()
        viewModelScope.launch {
            state = state.copy(foodList = foodList, isLoading = false)
            effects.send(FoodContract.Effect.DataWasLoad)
        }

    }
}