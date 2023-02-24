package com.company.note;

import com.company.dto.RegisterDto;
import com.company.note.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    private Object id;


    @GetMapping("/list")
    public ModelAndView getListAllNotes() {
        ModelAndView result = new ModelAndView("list");
        result.addObject("listNotes", noteService.listAll());
        result.addObject("notes", noteService);
        result.addObject("privacy", "PUBLIC");
        result.addObject("username", RegisterDto.getUsername());
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
    public ModelAndView getEditView(@RequestParam("id")String id) {
        ModelAndView result = new ModelAndView("edit");
        Note note = noteService.getById(id);
        result.addObject("editNote", noteService.getById(id));
        result.addObject("note", NoteDTO.fromNote(note));
        result.addObject("username", RegisterDto.getUsername());
        result.addObject("public", note.getPrivacy().equals("PUBLIC"));
        result.addObject("private", note.getPrivacy().equals("PRIVATE"));
        return result;
    }

    @PostMapping("/edit")
    public ModelAndView editNote(Note note) {
        noteService.update(note);
        ModelAndView result = new ModelAndView("notes/create");
        note = noteService.getById((String) id);
        result.addObject("note", note);
        result.addObject("username", RegisterDto.getUsername());
        result.addObject("public", note.getPrivacy().equals("PUBLIC"));
        result.addObject("private", note.getPrivacy().equals("PRIVATE"));
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/search")
    public ModelAndView searchNote(String pattern) {
        ModelAndView result=new ModelAndView("list");
        result.addObject("listNotes", noteService.searchNote(pattern));
        return result;
    }
    @GetMapping("/share/{id}")
    public ModelAndView info(@PathVariable("id") String id) {
        ModelAndView result = new ModelAndView("notes/note");
        String note = noteService.getPublicNoteById(id);
        if(note != null) {
            return result.addObject("note", note);
        } else {
            return result.addObject("message", "This note does not exist!:(");
        }
    }
}
