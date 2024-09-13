package com.example.hexagon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hexagon.R
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.databinding.FragmentAddPersonBinding
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory

class AddPersonFragment : Fragment() {

    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PersonRepository(requireContext())
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = viewModels<MainViewModel> { viewModelFactory }.value

        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                val person = Person(
                    name = binding.etName.text.toString(),
                    birthDate = binding.etBirthDate.text.toString(),
                    cpf = binding.etCpf.text.toString(),
                    city = binding.etCity.text.toString(),
                    photo = "photo_path",
                    isActive = binding.switchActive.isChecked
                )

                viewModel.addPerson(person)
                Toast.makeText(context, "Person added", Toast.LENGTH_SHORT).show()
                clearInputs()
                findNavController().navigate(R.id.action_addPersonFragment_to_listFragment)
            }
        }
    }

    private fun validateInputs(): Boolean {
        return when {
            binding.etName.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
                false
            }
            binding.etBirthDate.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Birth date is required", Toast.LENGTH_SHORT).show()
                false
            }
            binding.etCpf.text.isNullOrEmpty() -> {
                Toast.makeText(context, "CPF is required", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun clearInputs() {
        binding.etName.text.clear()
        binding.etBirthDate.text.clear()
        binding.etCpf.text.clear()
        binding.etCity.text.clear()
        binding.switchActive.isChecked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
