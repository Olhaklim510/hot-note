package com.company.note;

import com.company.note.noteEnum.NoteType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;


@Table(name = "note")
@Component
@Entity
@Data
public class Note {
    @Id
    private String id;
    @Column(name = "owner")
    private String owner;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "note_type")
    private NoteType noteType;
    @Transient
    private boolean access;
}
