package com.company.note;

import com.company.note.noteEnum.NoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private NoteService noteService;
    private String id=UUID.randomUUID().toString();

    @WithMockUser(value = "spring")
    @Test
    public void TestWhenCreateNewNote_thenCreated() throws Exception {
        this.mvc.perform(get("/note/create"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    public void whenDeleteNote_thenOk() throws Exception {
        this.mvc.perform(get("/note/list")
                        .param("id", id))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    public void TestWhenUpdateNote_thenUpdated() throws Exception {
        this.mvc.perform(get("/note/edit")
                        .param("id", id))
                .andExpect(status().is(400));
    }

    @WithMockUser(value = "spring")
    @Test
    public void TestWhenSearchNote_thenOK() throws Exception {
        this.mvc.perform(get("/note/search")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("pattern", id))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void TestWhenGetListAllNotes_thenOK() throws Exception {
        this.mvc.perform(get("/note/list"))
                .andExpect(status().isOk());
    }
}