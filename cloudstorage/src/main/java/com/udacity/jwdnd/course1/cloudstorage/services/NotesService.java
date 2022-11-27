package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;


    public NotesService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getAllListNotes(Authentication authentication) {
        String userName = authentication.getName();
        User user = userMapper.getUserByUsername(userName);
        return noteMapper.getAllNotes(user.getUserId());
    }

    public void deleteNote(Integer nodeId){
        noteMapper.deleteNoteByNoteId(nodeId);
    }

    public void addNote(Authentication authen, NoteForm noteForm) {
        Note note = noteMapper.getNoteById(noteForm.getNoteId());
        if(note == null) {
            String username = authen.getName();
            User user = userMapper.getUserByUsername(username);

            note = new Note();
            note.setNoteTitle(noteForm.getNoteTitle());
            note.setNoteDescription(noteForm.getNoteDesc());
            note.setUserId(user.getUserId());

            noteMapper.insertNote(note);
        } else {
            updateNote(authen, noteForm);
        }
    }

    public void updateNote(Authentication authen, NoteForm noteForm) {
        Note note = noteMapper.getNoteById(noteForm.getNoteId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDesc());

        noteMapper.updateNote(note);
    }


}
