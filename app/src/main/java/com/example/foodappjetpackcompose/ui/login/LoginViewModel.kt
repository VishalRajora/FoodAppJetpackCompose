package com.example.foodappjetpackcompose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappjetpackcompose.data.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val remoteSource: LoginRepo) : ViewModel() {

    var state by mutableStateOf(
        LoginContract.State(
            status = "",
            isLoading = false
        )
    )
        private set

    var effects = Channel<LoginContract.Effect>(Channel.UNLIMITED)
        private set


    fun setEvent(event: LoginContract.Event) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (event) {
                is LoginContract.Event.LoginData -> {
                    val result = remoteSource.saveData(email = event.email,
                        password = event.password,
                        name = event.name,
                        address = event.address,
                        phone = event.phone)

                    Timber.i("InsideResult $result")

                    if (result.toInt() > -1) {
                        Timber.i("Succcccc")
                        state = state.copy(status = "Success", isLoading = false)
                        effects.send(LoginContract.Effect.DataWasLoaded)
                    } else {
                        state = state.copy(status = "Failed", isLoading = false)
                        effects.send(LoginContract.Effect.DataWasFailed)
                    }
                }
            }

        }
    }
}