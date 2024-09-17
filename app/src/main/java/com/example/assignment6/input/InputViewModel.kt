package com.example.assignment6.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment6.database.User
import com.example.assignment6.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputViewModel(private val database: UserDatabaseDao) : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var location = MutableLiveData<String>()

    private var _isFieldsFilled = MutableLiveData<Boolean>()
    val isFieldsFilled: LiveData<Boolean>
        get() = _isFieldsFilled

    private var _navigateToOutput = MutableLiveData<Boolean>()
    val navigateToOutput: LiveData<Boolean>
        get() = _navigateToOutput

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        // Initialize fields with empty strings
        name.value = ""
        email.value = ""
        password.value = ""
        phone.value = ""
        location.value = ""

        // Check fields on initialization
        updateFieldsFilledStatus()
    }

    fun onSubmit() {
        uiScope.launch {
            if (areAllFieldsFilled()) {
                val user = User(
                    name = name.value ?: "",
                    email = email.value ?: "",
                    password = password.value ?: "",
                    phone = phone.value ?: "",
                    address = location.value ?: ""
                )

                insertDatabase(user)
                _navigateToOutput.value = true
            } else {
                Log.i("InputViewModel", "Fields are not completely filled.")
                _isFieldsFilled.value = false
            }
        }
    }

    private suspend fun insertDatabase(user: User) {
        withContext(Dispatchers.IO) {
            database.insert(user)
        }
    }

    fun onNavigationFinished() {
        _navigateToOutput.value = false
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

    fun clearText() {
        name.value = ""
        email.value = ""
        password.value = ""
        phone.value = ""
        location.value = ""
    }

    private fun areAllFieldsFilled(): Boolean {
        return !name.value.isNullOrEmpty() &&
                !email.value.isNullOrEmpty() &&
                !password.value.isNullOrEmpty() &&
                !phone.value.isNullOrEmpty() &&
                !location.value.isNullOrEmpty()
    }

    private fun updateFieldsFilledStatus() {
        _isFieldsFilled.value = areAllFieldsFilled()
    }
}