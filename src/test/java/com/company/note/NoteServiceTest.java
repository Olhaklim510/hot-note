package com.company.note;

import com.company.note.noteEnum.NoteType;
import com.company.user.Role;
import com.company.user.UserEntity;
import com.company.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@SpringBootTest
class NoteServiceTest {
    @Autowired
    NoteService noteService;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        UserEntity userAdd = new UserEntity();
        userAdd.setUsername("user1256");
        userAdd.setPassword("password");

        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        userAdd.setRoles(List.of(role));
        userRepository.save(userAdd);
    }

    @AfterEach
    public void afterEach() {
        noteRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void ThatAddHandledCorrectly() {
        Note noteAdd = new Note();
        noteAdd.setTitle("TestNoteAdd");
        noteAdd.setContent("TestNoteContentAdd");
        noteAdd.setNoteType(NoteType.PRIVATE);
        noteAdd.setOwner("user1256");
        noteService.add(noteAdd);

        Assertions.assertEquals(noteAdd, noteService.findAllAvailableForSpecificUser("user1256").get(0));
    }

    @Test
    void ThatDeleteByIdHandledCorrectly() {
        Note noteDelete = new Note();
        noteDelete.setTitle("TestNoteDelete");
        noteDelete.setContent("TestNoteContentDelete");
        noteDelete.setNoteType(NoteType.PRIVATE);
        noteDelete.setOwner("user1256");
        noteService.add(noteDelete);
        noteService.deleteById(noteDelete.getId());

        Assertions.assertThrows(NoSuchElementException.class, () -> noteService.getById(noteDelete.getId()));
    }

    @Test
    void ThatUpdateHandledCorrectly() {
        Note noteForUpdate = new Note();
        noteForUpdate.setNoteType(NoteType.PRIVATE);
        noteForUpdate.setTitle("TestNoteUpdate");
        noteForUpdate.setContent("TestNoteContentUpdate");
        noteForUpdate.setOwner("user1256");
        noteService.add(noteForUpdate);
        String id = noteForUpdate.getId();
        Note noteUpdate = new Note();
        noteUpdate.setId(id);
        noteUpdate.setNoteType(NoteType.PUBLIC);
        noteUpdate.setTitle("TestNoteUpdateNew");
        noteUpdate.setContent("TestNoteContentUpdateNew");
        noteUpdate.setOwner("user1256");
        noteService.update(noteUpdate);

        Assertions.assertEquals(noteUpdate, noteService.getById(id));
    }

    @Test
    void ThatGetByIdHandledCorrectly() {
        Note noteGetById = new Note();
        noteGetById.setNoteType(NoteType.PRIVATE);
        noteGetById.setTitle("TestNoteGetById");
        noteGetById.setContent("TestNoteContentGetById");
        noteGetById.setOwner("user1256");
        noteService.add(noteGetById);
        String id = noteGetById.getId();

        Assertions.assertEquals(noteGetById, noteService.getById(id));

    }

    @Test
    void ThatListAllHandledCorrectly() {

        Note note1FromList = new Note();
        note1FromList.setNoteType(NoteType.PRIVATE);
        note1FromList.setTitle("TestNote1FromList");
        note1FromList.setContent("TestNote1FromListContent");
        note1FromList.setOwner("user1256");
        noteService.add(note1FromList);

        Note note2FromList = new Note();
        note2FromList.setNoteType(NoteType.PRIVATE);
        note2FromList.setTitle("TestNote2FromList");
        note2FromList.setContent("TestNote2FromListContent");
        note2FromList.setOwner("user1256");
        noteService.add(note2FromList);

        Note note3FromList = new Note();
        note3FromList.setNoteType(NoteType.PRIVATE);
        note3FromList.setTitle("TestNote3FromList");
        note3FromList.setContent("TestNote3FromListContent");
        note3FromList.setOwner("user1256");
        noteService.add(note3FromList);

        Assertions.assertEquals(3, noteService.findAllAvailableForSpecificUser("user1256").size());
    }

    @Test
    void ThatSearchHandledCorrectly() {
        Note note1FromList = new Note();
        note1FromList.setNoteType(NoteType.PRIVATE);
        note1FromList.setTitle("TestNote1FromList");
        note1FromList.setContent("TestNote1FromListContent");
        note1FromList.setOwner("user1256");
        noteService.add(note1FromList);

        String pattern = "Test";
        String username = "user1256";

        Assertions.assertEquals(note1FromList, noteService.searchNote(username, pattern).get(0));
    }
}