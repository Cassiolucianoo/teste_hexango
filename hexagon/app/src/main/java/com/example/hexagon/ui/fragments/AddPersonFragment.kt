package com.example.hexagon.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        setupInputMasks()


        binding.btnSelectPhoto.setOnClickListener {
            openGallery()
        }


        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                val newPerson = Person(
                    id = 0,
                    name = binding.etName.text.toString(),
                    birthDate = binding.etBirthDate.text.toString(),
                    cpf = binding.etCpf.text.toString(),
                    city = binding.etCity.text.toString(),
                    isActive = binding.switchActive.isChecked
                )


                viewModel.addPerson(newPerson)

                Toast.makeText(context, "Person added", Toast.LENGTH_SHORT).show()
                clearInputs()
                findNavController().navigate(R.id.action_addPersonFragment_to_listFragment)
            }
        }
    }

    private fun setupInputMasks() {

        binding.etCpf.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.length > 11) {
                        binding.etCpf.setText(it.subSequence(0, 11))
                        binding.etCpf.setSelection(11)
                    }
                }
            }
        })


        binding.etBirthDate.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            var oldText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                val str = s.toString().replace(Regex("[^\\d]"), "")
                val newStr = when {
                    str.length > 8 -> oldText
                    str.length > 4 -> "${str.substring(0, 2)}/${str.substring(2, 4)}/${str.substring(4)}"
                    str.length > 2 -> "${str.substring(0, 2)}/${str.substring(2)}"
                    else -> str
                }

                isUpdating = true
                oldText = newStr
                binding.etBirthDate.setText(newStr)
                binding.etBirthDate.setSelection(newStr.length)
            }
        })
    }

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
