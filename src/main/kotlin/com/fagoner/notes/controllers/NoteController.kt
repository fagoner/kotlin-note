package com.fagoner.notes.controllers

import com.fagoner.notes.exceptions.NoteNotFoundException
import com.fagoner.notes.mappers.NoteMapper
import com.fagoner.notes.models.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/notes")
class NoteController(
        @Autowired
        var noteMapper: NoteMapper) {

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    fun findAll(): Collection<Note> = noteMapper.findAll()

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun insert(@RequestBody note: Note): Int {
        noteMapper.insert(note)
        return note.id
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun findById(@PathVariable id: Int): Note {
        return noteMapper.findById(id) ?: throw NoteNotFoundException()
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun update(
            @PathVariable id: Int,
            @RequestBody note: Note) {
        noteMapper.findById(id) ?: throw NoteNotFoundException()
        noteMapper.update(id, note)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code=HttpStatus.OK)
    fun delete(@PathVariable id: Int) {
        noteMapper.findById(id) ?: throw NoteNotFoundException()
        noteMapper.delete(id)
    }
}

