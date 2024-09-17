package com.example.assignment6.output

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.assignment6.database.UserDatabaseDao
import com.example.assignment6.input.InputViewModel

class OutputViewModelFactory(
    private val datasource : UserDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OutputViewModel::class.java)) {
            return OutputViewModel(datasource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}