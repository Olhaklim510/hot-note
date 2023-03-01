package com.company.note.mvc;

import com.company.exception.ShareException;
import com.company.note.Note;
import com.company.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getListAllNotes(Authentication authentication) {
        ModelAndView result = new ModelAndView("list");
        result.addObject("username",authentication.getName());
        result.addObject("listNotes", noteService
                .findAllAvailableForSpecificUserAndProvideAccessToChanges(authentication.getName(), authentication));
        return result;
    }

    @GetMapping("/delete")
    public ModelAndView deleteNote(Note note, Authentication authentication) {
        if (!authentication.getName().equals(note.getOwner())) {
            throw new ShareException("You do not have permission to edit this note :(");
        }
        ModelAndView result = new ModelAndView("redirect:/note/list");
        noteService.deleteById(note.getId());
        return result;
    }

    @GetMapping("/create")
    public ModelAndView getCreateView() {
        ModelAndView result = new ModelAndView("create");
        result.addObject("createNote", new Note());
        return result;
    }

    @PostMapping("/create")
    public ModelAndView createNote(Note note, Authentication authentication) {
        note.setOwner(authentication.getName());
        noteService.add(note);
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/edit")
    public ModelAndView getEditView(Note note, Authentication authentication) {
        if (!authentication.getName().equals(note.getOwner())) {
            throw new ShareException("You do not have permission to edit this note :(");
        }
        ModelAndView result = new ModelAndView("edit");
        result.addObject("editNote", noteService.getById(note.getId()));
        return result;
    }

    @PostMapping("/edit")
    public ModelAndView editNote(Note note) {
        noteService.update(note);
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/search")
    public ModelAndView searchNote(String pattern, Authentication authentication) {
        ModelAndView result = new ModelAndView("list");
        result.addObject("username",authentication.getName());
        result.addObject("listNotes", noteService.searchNote(authentication.getName(), pattern));
        return result;
    }

    @GetMapping("/share")
    public ModelAndView getShareView(Note note) {

        ModelAndView result = new ModelAndView("share");
        result.addObject("shareNote", noteService.getById(note.getId()));

        return result;
    }
}
