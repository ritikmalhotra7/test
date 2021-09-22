package com.example.contactskotlin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contactskotlin.databinding.FragmentAddContactBinding
import com.example.contactskotlin.databinding.FragmentUpdateContactBinding

class UpdateContactFragment(private val contact : Contacts) : DialogFragment() {
    private var _binding : FragmentUpdateContactBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateContactBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.name.setText(contact.name)
        binding.contact.setText(contact.contactno)

        binding.update.setOnClickListener {
            val fullName = binding.name.text.toString().trim()
            val contactNumber = binding.contact.text.toString().trim()

            if(fullName.isEmpty()){
                binding.name.error = "This field is required"
                return@setOnClickListener
            }

            if(contactNumber.isEmpty()){
                binding.contact.error = "This field is required"
                return@setOnClickListener
            }
            contact.name = fullName
            contact.contactno = contactNumber

            viewModel.updateContact(contact)
            dismiss()
            Toast.makeText(requireContext(),"Contact Updated",Toast.LENGTH_SHORT).show()

        }
    }



}