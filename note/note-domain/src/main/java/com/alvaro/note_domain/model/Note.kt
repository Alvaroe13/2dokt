package com.alvaro.note_domain.model


data class Note(
    var id: Int? = null,
    var title: String,
    var content: String,
    var priority: Int,
    var timeStamp: String
) {

    companion object Factory {
        fun emptyNote(): Note {
            return Note(0, "Empty note", "This is an empty note", 0, "null")
        }

        fun build(
            id: Int?,
            title: String,
            content: String,
            priority: Int,
            timeStamp: String
        ): Note {
            return Note(id, title, content, priority, timeStamp)
        }
    }
}