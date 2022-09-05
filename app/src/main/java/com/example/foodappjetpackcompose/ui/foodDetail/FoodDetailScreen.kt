package com.example.foodappjetpackcompose.ui.foodDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.transform.CircleCropTransformation
import com.example.foodappjetpackcompose.ui.food.FoodItemRow
import com.example.foodappjetpackcompose.ui.food.LoadingBar
import com.example.foodappjetpackcompose.ui.theme.FoodAppJetpackComposeTheme
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
fun FoodDetailScreen(
    state: FoodDetailContract.State,
    receiveAsFlow: Flow<FoodDetailContract.Effect>,
    foodID: String?,
    navController: NavHostController,
) {
    Timber.i("CallFoodDetail ${state.categoryFoodItems}")

    val scaffoldState: ScaffoldState = rememberScaffoldState()


    Scaffold(scaffoldState = scaffoldState) {
        Box {
            UI(state, navController)
        }
        if (state.isLoading)
            LoadingBar()
    }


}

@Composable
fun UI(foodDetail: FoodDetailContract.State, navController: NavHostController) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(
            foodDetail.categoryFoodItems
        ) { item ->
            Timber.i("ItemsAre $item")
            FoodItemRow(
                foodItem = item,
                navController = navController,
                iconTransFormationBuilder = {
                    transformations(
                        CircleCropTransformation()
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewee() {
    FoodAppJetpackComposeTheme {
        //UI("")
    }
}