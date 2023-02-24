package com.company.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository <Note, String> {

    @Query(value = "SELECT n FROM Note AS n WHERE n.noteType = 'PUBLIC' OR n.owner = :username")
    List<Note> findAllAvailableForSpecificUser(@Param("username") String username);
}
