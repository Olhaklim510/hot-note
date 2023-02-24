package com.company.note;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Table(name = "note")
@Component
@Entity
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    public String privacy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrivacy() {return privacy;}

    public void setPrivacy(String privacy) {this.privacy = privacy;}

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", privacy='" + privacy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(title, note.title) && Objects.equals(content, note.content)
                && Objects.equals(privacy,note.privacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, privacy);
    }
}
