package com.example.esigram.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.usecase.auth.AuthUseCases
import com.example.esigram.domains.usecase.user.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCases: AuthUseCases,
    private val userUseCases: UserUseCases,
    private val context: Context
) : ViewModel() {
    val sessionManager = SessionManager(context)

    private val _user = MutableStateFlow(authUseCases.getCurrentUserUseCase())
    val user = _user.asStateFlow()

    private val _finishedAuth = MutableStateFlow(false)
    val finishedAuth = _finishedAuth.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _pageState = MutableStateFlow("Login")
    val pageState = _pageState.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _onboardingStatus = MutableStateFlow(false)
    val onboardingStatus = _onboardingStatus.asStateFlow()

    init {
        viewModelScope.launch {
            val status = fetchOnboardingStatus()
            _onboardingStatus.value = status
            _loading.value = false
        }
    }

    fun refreshUser() {
        _user.value = authUseCases.getCurrentUserUseCase()
    }

    fun isUserLoggedIn(): Boolean {
        return authUseCases.getCurrentUserUseCase() != null
    }

    fun changeFinishedAuth(value: Boolean) {
        _finishedAuth.value = value
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onPageStateChange(newState: String) {
        _pageState.value = newState
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val result = authUseCases.loginUseCase(email, pass)

                if (result.isSuccess) {
                    refreshUser()
                    _onboardingStatus.value = fetchOnboardingStatus()
                    changeFinishedAuth(true)
                } else {
                    Toast.makeText(
                        context,
                        result.exceptionOrNull()?.message ?: "An error has occurred, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    e.message ?: "An error has occurred, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun register(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val result = authUseCases.registerUseCase(email, pass)

                if (result.isSuccess) {
                    refreshUser()
                    changeFinishedAuth(true)
                } else {
                    Toast.makeText(
                        context,
                        result.exceptionOrNull()?.message ?: "An error has occurred, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    e.message ?: "An error has occurred, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            sessionManager.clearSession()
            authUseCases.signOutUseCase()
            resetStateFlow()
            refreshUser()
        }
    }

    fun resetStateFlow() {
        _email.value = ""
        _password.value = ""
        _pageState.value = "Login"
        _finishedAuth.value = false
    }

    suspend fun fetchOnboardingStatus(): Boolean {
        val statusResponse = userUseCases.getOnboardingStatus()
        if (statusResponse.isSuccessful) {
            val isCompleted = statusResponse.body()?.success == true
            return isCompleted
        }
        return false
    }

    fun saveUserSession() {
        viewModelScope.launch {
            val userResponse = userUseCases.getMeCase()

            if (userResponse.isSuccessful) {
                val userData = userResponse.body()?.data
                userData?.let {
                    sessionManager.saveUserSession(
                        id = userData.id,
                        username = userData.username,
                        email = userData.email,
                        description = userData.description,
                        profilePictureUrl = userData.profilePicture?.signedUrl
                    )
                }
            }
        }
    }

}