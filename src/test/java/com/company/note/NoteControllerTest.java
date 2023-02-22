package com.company.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private NoteService noteService;

    @BeforeEach
    public void createNoteTest() {
        Note note = new Note();
        note.setTitle("TestNoteTitle");
        note.setContent("TestNoteContent");
        noteService.add(note);
    }

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
                        .param("id", "Test"))
                .andExpect(status().isOk());
    }

//    @WithMockUser(value = "spring")
//    @Test
//    public void TestWhenUpdateNote_thenUpdated() throws Exception {
//        this.mvc.perform(get("/note/edit")
//                        .param("id", "1"))
//                .andExpect(status().isOk());
//    }

    @WithMockUser(value = "spring")
    @Test
    public void TestWhenSearchNote_thenOK() throws Exception {
        this.mvc.perform(get("/note/search")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("pattern", "Test"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void TestWhenGetListAllNotes_thenOK() throws Exception {
        this.mvc.perform(get("/note/list"))
                .andExpect(status().isOk());
    }
}