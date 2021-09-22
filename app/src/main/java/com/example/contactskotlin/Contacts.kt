package com.example.contactskotlin

import com.google.firebase.database.Exclude

data class Contacts(
    @get : Exclude
    var id : String? = null,
    var name :String? = null,
    var contactno :String? = null,
    @get : Exclude
    var isDeleted : Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if(other is Contacts){
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (contactno?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }

}