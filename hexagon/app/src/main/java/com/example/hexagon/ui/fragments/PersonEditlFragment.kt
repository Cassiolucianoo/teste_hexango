package com.example.hexagon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.databinding.FragmentPersonEditlBinding
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory

class PersonEditFragment : Fragment() {

    private var _binding: FragmentPersonEditlBinding? = null
    private val binding get() = _binding!!

    private lateinit var person: Person
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PersonRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonEditlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            person = PersonEditFragmentArgs.fromBundle(it).person
            setupUI(person)
        }

        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                savePerson()
            }
        }
    }

    private fun setupUI(person: Person) {
        binding.etName.setText(person.name)
        binding.etBirthDate.setText(person.birthDate)
        binding.etCpf.setText(person.cpf)
        binding.etCity.setText(person.city)
        binding.switchActive.isChecked = person.isActive
    }

    private fun validateInputs(): Boolean {
        return when {
            binding.etName.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Nome é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            binding.etBirthDate.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Data de nascimento é obrigatória", Toast.LENGTH_SHORT).show()
                false
            }
            binding.etCpf.text.isNullOrEmpty() -> {
                Toast.makeText(context, "CPF é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun savePerson() {
        val updatedPerson = person.copy(
            name = binding.etName.text.toString(),
            birthDate = binding.etBirthDate.text.toString(),
            cpf = binding.etCpf.text.toString(),
            city = binding.etCity.text.toString(),
            isActive = binding.switchActive.isChecked
        )

        viewModel.updatePerson(updatedPerson)
        Toast.makeText(context, "Pessoa atualizada", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

