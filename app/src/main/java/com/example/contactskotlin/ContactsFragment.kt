package com.example.contactskotlin

import android.app.AlertDialog
import android.media.MediaRouter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.contactskotlin.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    private var _binding : FragmentContactsBinding? = null
    private val binding get()  = _binding!!
    private val adapter = ContactsAdapter()
    lateinit var viewModel : ContactViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        return binding.root
    }

    private var simpleSwipeCallback = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            var currentContact = adapter.contacts[position]
            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateContactFragment(currentContact).show(childFragmentManager,"")
                }
                ItemTouchHelper.LEFT -> {
                   /* viewModel.deleteContact(currentContact)
                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()*/
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure you want to delete!")
                        it.setPositiveButton("YES"){dialog,which->
                            viewModel.deleteContact(currentContact)
                            binding.recyclerView.adapter?.notifyItemRemoved(position)
                        }
                        it.setNegativeButton("NO"){dialog,which ->
                            dialog.cancel()
                        }
                    }.create().show()
                }
            }
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            AddContactFragment().show(childFragmentManager,"")
        }
        viewModel.contact.observe(viewLifecycleOwner,{
            adapter.addContact(it)
        })
        viewModel.getRealTimeUpdate()

        val itemTouchHelper = ItemTouchHelper(simpleSwipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}