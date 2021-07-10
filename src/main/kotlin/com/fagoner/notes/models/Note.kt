package com.fagoner.notes.models

import java.time.LocalDateTime

data class Note(
        var id: Int = 0,
        val name: String,
        val description: String,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null
)
