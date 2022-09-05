package com.example.foodappjetpackcompose.ui.food

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.foodappjetpackcompose.data.FoodItem
import com.example.foodappjetpackcompose.navigation.Screens
import com.example.foodappjetpackcompose.ui.theme.FoodAppJetpackComposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun FoodScreen(
    navController: NavHostController,
    state: FoodContract.State,
    effect: Flow<FoodContract.Effect>,
    navigationRequest: (foodID: String) -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()


    Scaffold(scaffoldState = scaffoldState) {
        Box {
            FoodItemMain(foodItems = state.foodList, navController, navigationRequest)
        }
        if (state.isLoading)
            LoadingBar()
    }

    val context = LocalContext.current
    LaunchedEffect(effect) {
        effect.onEach {
            if (it is FoodContract.Effect.DataWasLoad)
                Timber.i("Inside")
            Toast.makeText(context, "Data has been loaded", Toast.LENGTH_SHORT).show()
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Food categories are loaded.",
                duration = SnackbarDuration.Long
            )
        }.collect()
    }

}

@Composable
fun FoodItemMain(
    foodItems: List<FoodItem>,
    navController: NavHostController,
    navigationRequest: (foodID: String) -> Unit,
) {
    LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
        items(foodItems) { item ->
            FoodItemRow(foodItem = item,
                navController = navController,
                navigationRequest = navigationRequest)
        }
    }
}

//
@Composable
fun FoodItemRow(
    foodItem: FoodItem,
    navController: NavHostController,
    iconTransFormationBuilder: ImageRequest.Builder.() -> Unit = {},
    navigationRequest: (foodID: String) -> Unit = {},
) {
    Card(shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                16.dp
            )
            .clickable {
                navigationRequest(foodItem.id)
                //navController.navigate(Screens.FoodDetailScreen.withArgs(foodItem.id))
            }) {
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                FoodItemThumbnail(foodItem.thumbnailUrl, iconTransFormationBuilder)
            }
            FoodItemDetail(foodItem)
        }
    }
}

@Composable
fun FoodItemDetail(foodItem: FoodItem) {
    Column(modifier = Modifier
        .padding(
            start = 8.dp,
            end = 8.dp,
            top = 8.dp,
            bottom = 8.dp)
        .fillMaxWidth(0.80f)) {
        Text(text = foodItem.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
        Text(
            text = foodItem.description,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            maxLines = 4
        )

    }
}

@Composable
fun FoodItemThumbnail(
    thumbnailUrl: String,
    iconTransFormationBuilder: ImageRequest.Builder.() -> Unit,
) {
    Card(modifier = Modifier
        .size(60.dp)
        .padding(5.dp),
        shape = CircleShape,
        elevation = 2.dp) {
        Image(painter = rememberImagePainter(data = thumbnailUrl,
            builder = iconTransFormationBuilder),
            contentDescription = "For food1 item",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
    }
}


@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewee() {
    FoodAppJetpackComposeTheme {
        //FoodScreen(navController)
    }
}
