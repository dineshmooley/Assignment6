package com.example.assignment6.output

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment6.database.User
import com.example.assignment6.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OutputViewModel(
    val database: UserDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val currData : MutableLiveData<User?> = MutableLiveData()

    init {
        getCurrData()
    }

    private fun getCurrData() {
        uiScope.launch {
            currData.value = getCurrentData()
        }
    }

    private suspend fun getCurrentData(): User? {
        return withContext(Dispatchers.IO) {
            database.getUser()
        }
    }

    fun onClick()   {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.clear()
            }
            currData.value = null
        }
    }
}