package com.example.foodappjetpackcompose.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.foodappjetpackcompose.R
import com.example.foodappjetpackcompose.navigation.Screens
import com.example.foodappjetpackcompose.ui.food.LoadingBar
import com.example.foodappjetpackcompose.ui.theme.FoodAppJetpackComposeTheme
import kotlinx.coroutines.flow.Flow
import timber.log.Timber


@Composable
fun LoginScreen(
    navController: NavHostController,
    state: LoginContract.State,
    effect: Flow<LoginContract.Effect>,
    onEventSend: (LoginContract.Event) -> Unit,
    onNavigationRedirect: (itemID: Boolean) -> Unit,
) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    Scaffold(scaffoldState = scaffoldState) {
        if (state.isLoading)
            LoadingBar()
    }

    LaunchedEffect(effect) {
        effect.collect { effects ->
            if (effects is LoginContract.Effect.DataWasLoaded) {
                Timber.i("SuccessFFFF")
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                onNavigationRedirect(true)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Success",
                    duration = SnackbarDuration.Long
                )
            }
            if (effects is LoginContract.Effect.DataWasFailed) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                Timber.i("FailedFFFF")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Failed to login",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    LoginComposable(navController, loginViewModel, scaffoldState, onEventSend, onNavigationRedirect)

}

@Composable
fun LoginComposable(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    scaffoldState: ScaffoldState,
    onEventSend: (LoginContract.Event) -> Unit,
    onNavigationRedirect: (itemID: Boolean) -> Unit,
) {
    val emailValue = remember {
        mutableStateOf("")
    }
    val nameValue = remember {
        mutableStateOf("")
    }
    val addressValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }

    val phoneValue = remember {
        mutableStateOf("")
    }

    val passwordVisiblity = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(8.68f)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(10.dp)
    ) {
        Text(
            text = "SignUp", style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = TextUnit.Unspecified,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = nameValue.value,
                onValueChange = {
                    nameValue.value = it
                },
                label = {
                    Text(text = "Name")
                },
                placeholder = {
                    Text(text = "Enter Your Name")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(1f),
                leadingIcon = {
                    IconButton(onClick = { })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_person_24),
                            contentDescription = "",
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))

            //this is for email
            OutlinedTextField(
                value = emailValue.value,
                onValueChange = {
                    emailValue.value = it
                },
                label = {
                    Text(text = "Email Address")
                },
                placeholder = {
                    Text(text = "Enter Your Email")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(1f),
                leadingIcon = {
                    IconButton(onClick = { })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_email_24),
                            contentDescription = "",
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            OutlinedTextField(
                value = passwordValue.value,

                onValueChange = {
                    passwordValue.value = it
                },
                label = {
                    Text(text = "Password ")
                },
                placeholder = {
                    Text(text = "Enter Your Password")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(1f),
                trailingIcon = {
                    IconButton(onClick = { passwordVisiblity.value = !passwordVisiblity.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                            contentDescription = "",
                            tint = if (passwordVisiblity.value) Color.Black else Color.Gray
                        )
                    }
                },
                visualTransformation = if (passwordVisiblity.value) VisualTransformation.None else PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = addressValue.value,
                onValueChange = {
                    addressValue.value = it
                },
                label = {
                    Text(text = "Address")
                },
                placeholder = {
                    Text(text = "Enter Your Full Address")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(1f),
                leadingIcon = {
                    IconButton(onClick = { })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_home_work_24),
                            contentDescription = "",
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.padding(5.dp))

            OutlinedTextField(
                value = phoneValue.value,
                onValueChange = {
                    phoneValue.value = it
                },
                label = {
                    Text(text = "Phone")
                },
                placeholder = {
                    Text(text = "Enter Your Phone")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(1f),
                leadingIcon = {
                    IconButton(onClick = { })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_local_phone_24),
                            contentDescription = "",
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.padding(15.dp))
            //login button
            Button(
                onClick = {
                    onEventSend(
                        LoginContract.Event.LoginData(
                            name = nameValue.value.toString(),
                            email = emailValue.value.toString(),
                            password = passwordValue.value.toString(),
                            address = addressValue.value.toString(),
                            phone = phoneValue.value.toString()
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(45.dp)
            ) {
                Text(text = "Sign In", fontSize = 15.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewee() {
    FoodAppJetpackComposeTheme {
        // LoginComposable()
    }
}

