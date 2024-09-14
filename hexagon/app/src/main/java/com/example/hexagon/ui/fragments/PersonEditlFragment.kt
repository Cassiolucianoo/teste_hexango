package com.example.hexagon.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.databinding.FragmentPersonEditlBinding
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory
import com.example.hexagon.utils.DateInputMask
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PersonEditFragment : Fragment() {

    private var _binding: FragmentPersonEditlBinding? = null
    private val binding get() = _binding!!

    private lateinit var person: Person
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PersonRepository(requireContext()))
    }

    private var selectedImageUri: Uri? = null

    companion object {
        const val GALLERY_REQUEST_CODE = 1001
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

        // Aplicar a máscara de data
        DateInputMask.applyDateMask(binding.etBirthDate)

        binding.btnSelectPhoto.setOnClickListener {
            openGallery()
        }

        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                savePerson()
            }
        }

        // Carregar a imagem existente, se houver
        if (person.photo.isNotEmpty()) {
            val file = File(person.photo)
            if (file.exists()) {
                Glide.with(this)
                    .load(file)
                    .into(binding.ivProfileImage)
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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.ivProfileImage.setImageURI(selectedImageUri) // Atualiza a imagem no ImageView
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        val context = requireContext()
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
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

        val imagePath = selectedImageUri?.let { saveImageToInternalStorage(it) } ?: person.photo

        val updatedPerson = person.copy(
            name = binding.etName.text.toString(),
            birthDate = binding.etBirthDate.text.toString(),
            cpf = binding.etCpf.text.toString(),
            city = binding.etCity.text.toString(),
            photo = imagePath, // Atualiza o caminho da foto
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
