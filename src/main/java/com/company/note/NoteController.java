package com.company.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getListAllNotes() {
        ModelAndView result = new ModelAndView("list");
        result.addObject("listNotes", noteService.listAll());
        return result;
    }

    @GetMapping("/delete")
    public ModelAndView deleteNote(Note note) {
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
    public ModelAndView createNote(Note note) {
            noteService.add(note);
            return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/edit")
    public ModelAndView getEditView(@RequestParam("id") String id) {
        ModelAndView result = new ModelAndView("edit");
        result.addObject("editNote", noteService.getById(id));
        return result;
    }

    @PostMapping("/edit")
    public ModelAndView editNote(Note note) {
        noteService.update(note);
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/search")
    public ModelAndView searchNote(String pattern) {
        ModelAndView result=new ModelAndView("list");
        result.addObject("listNotes", noteService.searchNote(pattern));
        return result;
    }
}
