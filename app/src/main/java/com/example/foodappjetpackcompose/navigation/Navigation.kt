package com.example.foodappjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.foodappjetpackcompose.ui.food.FoodScreen
import com.example.foodappjetpackcompose.ui.food.FoodViewModel
import com.example.foodappjetpackcompose.ui.foodDetail.FoodDetailScreen
import com.example.foodappjetpackcompose.ui.foodDetail.FoodDetailViewModel
import com.example.foodappjetpackcompose.ui.login.LoginScreen
import com.example.foodappjetpackcompose.ui.login.LoginViewModel
import com.example.foodappjetpackcompose.ui.login.UserState
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LoginScreen.rout) {
        composable(route = Screens.LoginScreen.rout) {
            LoginScreeDestination(navController)
        }
        composable(route = Screens.FoodScreen.rout) {
            FoodScreenDestination(navController)
        }
        composable(route = Screens.FoodDetailScreen.rout + "/{foodItemID}",
            arguments = listOf(navArgument("foodItemID") {
                type = NavType.StringType
                nullable = true
                defaultValue = "Vishal"
            })) {
            FoodDetailScreenDestination(it.arguments?.getString("foodItemID"), navController)
        }
    }
//    CompositionLocalProvider(UserState provides userState) {
//        ApplicationSwitcher()
//    }
}

@Composable
fun ApplicationSwitcher() {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        // HomeScreen()
    } else {
        //LoginScreen()
    }
}

@Composable
fun FoodDetailScreenDestination(foodID: String?, navController: NavHostController) {
    val foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
    FoodDetailScreen(foodDetailViewModel.state,
        foodDetailViewModel.effects.receiveAsFlow(), foodID, navController)
}

@Composable
fun LoginScreeDestination(navController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    LoginScreen(
        navController,
        loginViewModel.state,
        loginViewModel.effects.receiveAsFlow(),
        onEventSend = {
            loginViewModel.setEvent(it)
        },
        onNavigationRedirect = {
            if (it) {
                navController.navigate(Screens.FoodScreen.rout)
            }
            Timber.i("DataIS $it")
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FoodScreenDestination(navController: NavHostController) {
    val foodViewModel: FoodViewModel = hiltViewModel()
    FoodScreen(navController,
        state = foodViewModel.state,
        effect = foodViewModel.effects.receiveAsFlow(),
        navigationRequest = {
            Timber.i("DataISFood $it")
            navController.navigate(Screens.FoodDetailScreen.withArgs(it))
        })
}

sealed class Screens(val rout: String) {
    object LoginScreen : Screens("LoginScreen")
    object FoodScreen : Screens("FoodScreen")
    object FoodDetailScreen : Screens("FoodDetailScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(rout)
            args.forEach {
                append("/$it")
            }
        }
    }
}