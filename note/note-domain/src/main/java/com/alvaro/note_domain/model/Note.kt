package com.alvaro.note_domain.model


data class Note(
    var id: String? = null,
    var title: String,
    var content: String,
    var priority: Int,
    var timeStamp: String
) {

    companion object Factory {
        fun emptyNote(): Note {
            return Note(null, "Empty note", "This is an empty note", 0, "null")
        }

        fun build(
            id: String?,
            title: String,
            content: String,
            priority: Int,
            timeStamp: String
        ): Note {
            return Note(id, title, content, priority, timeStamp)
        }
    }
}