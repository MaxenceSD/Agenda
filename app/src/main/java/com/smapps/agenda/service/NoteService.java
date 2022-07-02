package com.smapps.agenda.service;

import android.content.Context;

import com.smapps.agenda.db.dao.NoteDaoImpl;
import com.smapps.agenda.model.Note;

import java.util.Collection;

public class NoteService {

    private final NoteDaoImpl noteDao;

    public NoteService(Context context) {
        this.noteDao = new NoteDaoImpl(context);
    }

    /***********************************************************************************************
     * Méthodes relatives aux interactions avec la BDD
     **********************************************************************************************/

    public Note getNoteById(Long id) {
        return this.noteDao.getNoteById(id);
    }

    public void createOrUpdateNote(Note note) {
        this.noteDao.createOrUpdateNote(note);
    }

    public void deleteNote(Note note) {
        this.noteDao.deleteNote(note);
    }

    public Collection<Note> getNoteByJourId(Long id) {
        return this.noteDao.getNoteByJourId(id);
    }

    /***********************************************************************************************
     * Méthodes relatives aux interactions l'objet Jour
     **********************************************************************************************/


}
