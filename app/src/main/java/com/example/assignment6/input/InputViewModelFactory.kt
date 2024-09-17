package com.example.assignment6.input

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment6.database.UserDatabaseDao

class InputViewModelFactory(
    private val datasource : UserDatabaseDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InputViewModel::class.java)) {
            return InputViewModel(datasource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}