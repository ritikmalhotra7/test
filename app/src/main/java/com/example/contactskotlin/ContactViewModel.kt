package com.example.contactskotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class ContactViewModel:ViewModel() {
    private val dbContacts = FirebaseDatabase.getInstance().getReference(NODE_CONTACT)

    private val _result = MutableLiveData<Exception?>()
    val result :MutableLiveData<Exception?> get() = _result

    private val _contact = MutableLiveData<Contacts>()
    val contact :MutableLiveData<Contacts> get() = _contact


    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val  contact = snapshot.getValue(Contacts::class.java)
            contact?.id = snapshot.key
            _contact.value = contact!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val  contact = snapshot.getValue(Contacts::class.java)
            contact?.id = snapshot.key
            _contact.value = contact!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val contact = snapshot.getValue(Contacts ::class.java)
            contact?.id = snapshot.key
            contact?.isDeleted = true
            _contact.value = contact!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    fun getRealTimeUpdate(){
        dbContacts.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbContacts.removeEventListener(childEventListener)

    }
    fun addContact(contact:Contacts){
        contact.id = dbContacts.push().key

        dbContacts.child(contact.id!!).setValue(contact).addOnCompleteListener {
            if(it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    fun deleteContact(contact:Contacts){

        dbContacts.child(contact.id!!).setValue(null).addOnCompleteListener {
            if(it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    fun updateContact(contact: Contacts) {
        dbContacts.child(contact.id!!).setValue(contact).addOnCompleteListener {
            if(it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

}