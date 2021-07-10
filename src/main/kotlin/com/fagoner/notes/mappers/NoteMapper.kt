package com.fagoner.notes.mappers

import com.fagoner.notes.models.Note
import org.apache.ibatis.annotations.*

@Mapper
interface NoteMapper {

    @Select("SELECT * FROM note_app.note;")
    fun findAll(): Collection<Note>

    @Insert("INSERT INTO note_app.note(name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(note: Note)

    @Select("SELECT * FROM note_app.note WHERE id = #{id}")
    fun findById(id: Int): Note?

    @Update("UPDATE note_app.note SET name = #{note.name}, description = #{note.description} WHERE id = #{id}")
    fun update(id: Int, note: Note)

    @Delete("DELETE FROM note_app.note WHERE id = #{id}")
    fun delete(id: Int)
}