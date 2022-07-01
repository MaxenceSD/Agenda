package com.smapps.agenda.db.dao;

import static com.smapps.agenda.db.util.Constantes.JOUR_ID;
import static com.smapps.agenda.db.util.Constantes.NOTE_ID;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.smapps.agenda.db.ApplicationDatabase;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;

import java.util.ArrayList;
import java.util.Collection;

public class NoteDaoImpl {

    private static final String TAG = NoteDaoImpl.class.getSimpleName();

    private final ApplicationDatabase applicationDatabase;

    private Dao<Note, String> getNoteDao() {
        return applicationDatabase.getNoteDao();
    }

    public NoteDaoImpl(Context context) {
        this.applicationDatabase = ApplicationDatabase.getInstance(context);
    }

    public Note getNoteById(Long id) {
        Note note = null;
        try {
            note = getNoteDao().queryBuilder().where().eq(NOTE_ID, id).queryForFirst();
        } catch (Exception e) {
            Log.e(TAG, "getNoteById: ", e);
        }
        return note;
    }

    public void createOrUpdateNote(Note note) {
        try {
            Note noteBdd = null;
            if (note.getId() != null) {
                noteBdd = getNoteById(note.getId());
            }

            if (noteBdd == null) {
                getNoteDao().create(note);
            } else {
                getNoteDao().update(note);
            }
        } catch (Exception e) {
            Log.e(TAG, "createOrUpdateNote: ", e);
        }
    }

    public void deleteNote(Note note) {
        try {
            getNoteDao().delete(note);
        } catch (Exception e) {
            Log.e(TAG, "createOrUpdateNote: ", e);
        }
    }

    public Collection<Note> getNoteByJourId(Long id) {
        Collection<Note> notes = new ArrayList<>();
        try {
            notes = getNoteDao().queryBuilder().where().eq(JOUR_ID, id).query();
        } catch (Exception e) {
            Log.e(TAG, "getNoteById: ", e);
        }
        return notes;
    }
}
