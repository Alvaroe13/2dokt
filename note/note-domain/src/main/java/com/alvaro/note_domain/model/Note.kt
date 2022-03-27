package com.alvaro.note_domain.model


data class Note(
    var id: Int,
    var title: String,
    var content: String,
    var priority :Int,
    var timeStamp : String
){

    companion object Factory{
        fun emptyNote() : Note {
            return Note( 0, "Empty note","This is an empty note",0,"null")
        }
    }
}