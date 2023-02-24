package com.company.note;

import com.company.exception.ContentException;
import com.company.exception.TitleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public synchronized void add(Note note) {
        if(note.getTitle().length() <5 || note.getTitle().length() >100){
            throw new TitleException("The name of the note must be between 5 and 100 characters inclusive.");
        } else if (note.getContent().length() <5 || note.getContent().length() >10000) {
            throw new ContentException("The content of the note must be between 5 and 10,000 characters inclusive.");
        } else {
            UUID uuid = UUID.randomUUID();
            note.setId(uuid.toString());
            noteRepository.save(note);
        }
    }

    public synchronized void deleteById(String id) {
        if (!noteRepository.findById(id).isPresent()) {
            throw new NoSuchElementException("This note doesn't exist");
        }
        noteRepository.deleteById(id);
    }

    public synchronized void update(Note note) {
        if (!noteRepository.findById(note.getId()).isPresent()) {
            throw new NoSuchElementException("This note doesn't exist");
        } else if(note.getTitle().length() <5 || note.getTitle().length() >100){
            throw new TitleException("The name of the note must be between 5 and 100 characters inclusive.");
        } else if (note.getContent().length() <5 || note.getContent().length() >10000) {
            throw new ContentException("The content of the note must be between 5 and 10,000 characters inclusive.");
        } else {
            noteRepository.save(note);
        }
    }

    public synchronized Note getById(String id) {
        return noteRepository.findById(id).orElseThrow(()->new NoSuchElementException("This note doesn't exist"));
    }

    public synchronized List<Note> listAll() {
        return noteRepository.findAll();
    }

    public synchronized List<Note> searchNote(String pattern) {
        return listAll()
                        .stream()
                        .filter(note ->
                                note.getContent().toLowerCase().contains(pattern.toLowerCase())
                                        || note.getTitle().toLowerCase().contains(pattern.toLowerCase()))
                .toList();
    }
    public String getPublicNoteById(String id) {
        String note = null;

        SimpleJpaRepository repository = null;
        if(repository.existsById(id) && getById(id).getPrivacy().equals("PUBLIC")) {
            note = String.valueOf(getById(id));
        }

        return note;
    }
}
