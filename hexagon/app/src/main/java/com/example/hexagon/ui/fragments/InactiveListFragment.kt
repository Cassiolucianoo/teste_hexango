package com.example.hexagon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.databinding.FragmentListBinding
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory
import com.example.hexagon.ui.main.PersonAdapter

class InactiveListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PersonRepository(requireContext())
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val adapter = PersonAdapter { person ->

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.inactivePersons.observe(viewLifecycleOwner) { persons ->
            persons?.let {
                adapter.submitList(it)
            }
        }

        viewModel.getInactivePersons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
