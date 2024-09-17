package com.example.assignment6.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assignment6.R
import com.example.assignment6.database.UserDatabase
import com.example.assignment6.databinding.FragmentInputBinding

class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    private lateinit var inputViewModel: InputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val datasource = UserDatabase.getInstance(requireContext()).userDatabaseDao()

        val viewModelFactory = InputViewModelFactory(datasource)
        inputViewModel = ViewModelProvider(this, viewModelFactory)[InputViewModel::class.java]

        binding = FragmentInputBinding.inflate(inflater, container, false)
        binding.viewModel = inputViewModel
        binding.lifecycleOwner = this

        inputViewModel.navigateToOutput.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_inputFragment_to_outputFragment)
                inputViewModel.onNavigationFinished()
            }
        })

        inputViewModel.isFieldsFilled.observe(viewLifecycleOwner, Observer { fieldsFilled ->
            if (!fieldsFilled) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        inputViewModel.clearText()
    }

}