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
import com.example.hexagon.R
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.databinding.FragmentAddPersonBinding
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddPersonFragment : Fragment() {

    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private var selectedImageUri: Uri? = null

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

        // Botão de seleção de foto
        binding.btnSelectPhoto.setOnClickListener {
            openGallery()
        }

        // Botão de salvar a pessoa
        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                val person = Person(
                    name = binding.etName.text.toString(),
                    birthDate = binding.etBirthDate.text.toString(),
                    cpf = binding.etCpf.text.toString(),
                    city = binding.etCity.text.toString(),
                    photo = selectedImageUri?.path ?: "",  // Salva o caminho da imagem no banco de dados
                    isActive = binding.switchActive.isChecked
                )

                viewModel.addPerson(person)
                Toast.makeText(context, "Person added", Toast.LENGTH_SHORT).show()
                clearInputs()
                findNavController().navigate(R.id.action_addPersonFragment_to_listFragment)
            }
        }
    }

    // Abrir a galeria para selecionar uma imagem
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val savedImagePath = saveImageToInternalStorage(imageUri)
                if (savedImagePath != null) {
                    selectedImageUri = Uri.fromFile(File(savedImagePath))
                    binding.ivPhoto.setImageURI(selectedImageUri)
                }
            }
        }
    }

    // Salvar imagem no armazenamento interno
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
        binding.ivPhoto.setImageResource(R.drawable.ic_launcher_foreground)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val GALLERY_REQUEST_CODE = 1001
    }
}
