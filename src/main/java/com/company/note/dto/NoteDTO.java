package com.company.note.dto;

import com.company.note.Note;
import com.company.note.noteEnum.NoteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDTO {
    private String id;
    private String owner;
    private String title;
    private String content;
    private NoteType noteType;

    public static NoteDTO fromNote(Note note){
        NoteDTO result = new NoteDTO();
        result.setId(note.getId());
        result.setTitle(note.getTitle());
        result.setContent(note.getContent());
        result.setNoteType(note.getNoteType());
        result.setOwner(note.getOwner());
        return result;
    }

    public static Note fromDTO(NoteDTO noteDTO){
        Note note=new Note();
        note.setId(noteDTO.getId());
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setNoteType(noteDTO.getNoteType());
        note.setOwner(noteDTO.getOwner());
        return note;
    }
}
