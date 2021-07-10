package com.fagoner.notes

import com.fagoner.notes.models.Note
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import com.fasterxml.jackson.databind.ObjectMapper

import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.assertj.core.api.Assertions.assertThat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val jacksonObjectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerKotlinModule()

    @Test
    fun `When get notes should return a list`() {
        val mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val noteList: List<Note> = jacksonObjectMapper.readValue(mvcResult.response.contentAsString)

        assertThat(noteList).isNotNull
    }

    @Test
    fun `Given a new note is added When FindById Should Return the note`() {
        val newId = createNote()

        val pathWithId = "/api/notes/$newId"
        val mvcResultGet = this.mockMvc.perform(
                MockMvcRequestBuilders.get(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val expectedNote: Note = jacksonObjectMapper.readValue(mvcResultGet.response.contentAsString)
        assertThat(expectedNote).isNotNull
    }

    @Test
    fun `Given an invalid id When findById should expect status Not Found`() {
        val id = 0
        val pathWithId = "/api/notes/$id"

        mockMvc.perform(MockMvcRequestBuilders.get(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()
    }

    @Test
    fun `Given existing note When update and find Id Should Return the note`() {
        val newId = createNote()
        val pathWithId = "/api/notes/$newId"

        val noteToUpdate = Note(
                name = "Update_Note_${Date().time}",
                description = "Update_Description_${Date().time}")

        val contentToUpdate = jacksonObjectMapper.writeValueAsString(noteToUpdate)

        this.mockMvc.perform(
                MockMvcRequestBuilders.put(pathWithId)
                        .content(contentToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val mvcResultGet = this.mockMvc.perform(
                MockMvcRequestBuilders.get(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val expectedNote: Note = jacksonObjectMapper.readValue(mvcResultGet.response.contentAsString)
        assertThat(expectedNote.name).isEqualTo(noteToUpdate.name)
        assertThat(expectedNote.description).isEqualTo(noteToUpdate.description)
    }

    @Test
    fun `Given no existing note When update status should return not foun status`() {
        val id = 0
        val pathWithId = "/api/notes/$id"

        val noteToUpdate = Note(
                name = "Update_Note_${Date().time}",
                description = "Update_Description_${Date().time}")

        val contentToUpdate = jacksonObjectMapper.writeValueAsString(noteToUpdate)

        this.mockMvc.perform(
                MockMvcRequestBuilders.put(pathWithId)
                        .content(contentToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()
    }

    @Test
    fun `Given existing note When delete should return ok status`() {
        val id = createNote()
        val pathWithId = "/api/notes/$id"

        this.mockMvc.perform(MockMvcRequestBuilders.delete(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        this.mockMvc.perform(MockMvcRequestBuilders.get(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()
    }

    @Test
    fun `Given no existing note When delete should return ok status`() {
        val id = 0
        val pathWithId = "/api/notes/$id"

        this.mockMvc.perform(MockMvcRequestBuilders.delete(pathWithId))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()
    }

    private fun createNote(): Int {
        val name = "Note_${Date().time}"
        val description = "Description_${Date().time}"
        val note = Note(id = 0, name = name, description = description, createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())

        val content = jacksonObjectMapper.writeValueAsString(note)

        val mvcResultPost = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/notes")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

        return jacksonObjectMapper.readValue(mvcResultPost.response.contentAsString)
    }

}