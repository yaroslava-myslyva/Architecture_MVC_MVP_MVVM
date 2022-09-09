package com.example.architecture_mvc_mvp_mvvm.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.architecture_mvc_mvp_mvvm.R
import com.example.architecture_mvc_mvp_mvvm.domain.implementations.AuthRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed class LoginState {
    class DefaultState : LoginState()
    class SendingState : LoginState()
    class LoginSucceededState : LoginState()
    class ErrorState<T>(val message: T) : LoginState()
}

class AuthViewModel {

    private var authRepository = AuthRepositoryImpl()
    val state = MutableLiveData<LoginState>()

    fun login(email: String, password: String) {
        if (!validateEmail(email = email)) {
            state.value = LoginState.ErrorState(message = R.string.error_email_invalid)
            return
        }
        if (!validatePassword(password = password)) {
            state.value = LoginState.ErrorState(message = R.string.error_password_invalid)
            return
        }
        state.value = LoginState.SendingState()
        CoroutineScope(Dispatchers.IO).async {
            val errorMessage = authRepository.login(email = email, password = password).await()

            if (errorMessage.isEmpty()) {
                launch(Dispatchers.Main) {
                    state.value = LoginState.LoginSucceededState()
                }
            } else {
                launch(Dispatchers.Main) {
                    state.value = LoginState.ErrorState(message = errorMessage)
                }
            }
        }
    }

    //Internal logic
    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 8
    }

}
