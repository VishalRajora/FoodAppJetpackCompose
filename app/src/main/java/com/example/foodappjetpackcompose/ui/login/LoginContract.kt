package com.example.foodappjetpackcompose.ui.login

class LoginContract {
    data class State(
        val status: String = "",
        val isLoading: Boolean = false,
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
        object DataWasFailed : Effect()
    }

    sealed class Event {
        data class LoginData(
            val name: String = "",
            val email: String = "",
            val password: String = "",
            val address: String = "",
            val phone: String = "",
        ) : Event()
    }
}