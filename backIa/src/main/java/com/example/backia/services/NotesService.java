package com.example.backia.services;

import com.example.backia.models.Notes;
import com.example.backia.models.User;
import com.example.backia.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NotesRepository notesRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Notes save(Notes notes){
        return notesRepository.save(notes);
    }
    public List<Notes> getNotesByUserId(User utilisateurID) {
        return notesRepository.findByUser(utilisateurID);
    }
}
