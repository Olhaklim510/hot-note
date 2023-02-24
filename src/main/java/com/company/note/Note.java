package com.company.note;

import com.company.note.noteEnum.NoteType;
import com.company.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
}
