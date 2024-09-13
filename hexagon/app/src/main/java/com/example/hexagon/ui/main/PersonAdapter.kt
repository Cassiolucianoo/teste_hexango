package com.example.hexagon.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hexagon.data.model.Person
import com.example.hexagon.databinding.PersonItemBinding
import com.example.hexagon.utils.DateUtils.calculateAge

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var personList = emptyList<Person>()

    inner class PersonViewHolder(private val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.tvName.text = person.name
            binding.tvAge.text = calculateAge(person.birthDate).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int = personList.size

    fun submitList(list: List<Person>) {
        personList = list
        notifyDataSetChanged()
    }

}
