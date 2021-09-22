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

class AddContactFragment : DialogFragment() {
    private var _binding : FragmentAddContactBinding? = null
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
        _binding = FragmentAddContactBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner,{
            val message = if(it == null) {
                getString(R.string.succesfulmessage)
            }else{
                getString(R.string.errormessage)
            }
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
            dismiss()
        })
        binding.save.setOnClickListener {
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
            val contacts = Contacts()
            contacts.name = fullName
            contacts.contactno = contactNumber

            viewModel.addContact(contacts)
        }
    }



}