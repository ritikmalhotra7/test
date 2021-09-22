package com.example.contactskotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactskotlin.databinding.RecyclerItemsBinding

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var contacts = mutableListOf<Contacts>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = contacts[position].name
        holder.binding.contact.text = contacts[position].contactno

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun addContact(c : Contacts){
         if(!contacts.contains(c)){
             contacts.add(c)
         }else{
             var index = contacts.indexOf(c)
             if(c.isDeleted){
                 contacts.removeAt(index)
             }else{
                contacts[index] = c
             }
         }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : RecyclerItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}