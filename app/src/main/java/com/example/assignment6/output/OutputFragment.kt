package com.example.assignment6.output

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.window.application
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.assignment6.R
import com.example.assignment6.database.UserDatabase
import com.example.assignment6.databinding.FragmentOutputBinding

//import com.example.assignment6.databinding.FragmentOutputBinding

class OutputFragment : Fragment() {
    private lateinit var binding: FragmentOutputBinding
    private lateinit var viewModel: OutputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_output, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(requireContext()).userDatabaseDao()
        val viewModelFactory = OutputViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OutputViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //return null
        return binding.root
    }
}