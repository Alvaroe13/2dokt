package com.alvaro.note_domain.model


data class Note(
    var id: Int,
    var title: String = "",
    var content: String = "",
    var priority :Int,
    var timeStamp : String = ""
)