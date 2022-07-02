package com.smapps.agenda.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smapps.agenda.R;
import com.smapps.agenda.adapter.NoteAdapter;
import com.smapps.agenda.dialog.NoteDialog;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.service.NoteService;
import com.smapps.agenda.utils.JourLibelleEnum;
import com.smapps.agenda.utils.MarquageEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListeNoteActivity extends AppCompatActivity {

    private JourService jourService;
    private NoteService noteService;
    private NoteAdapter noteAdapter;

    private Jour jour;
    private String jourId;

    private FloatingActionButton boutonAjout;
    private RecyclerView noteRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_note);

        this.jourService = new JourService(this);
        this.noteService = new NoteService(this);

        this.boutonAjout = findViewById(R.id.floating_button);
        this.noteRecycler = findViewById(R.id.note_recycler);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.jourId = bundle.getString("JOUR_ID");
            this.jour = this.jourService.getJourByStrId(this.jourId);
            if (this.jour == null) {
                Date date = (Date) bundle.getSerializable("JOUR_DATE");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                this.jour = new Jour();
                this.jour.setStrId(this.jourId);
                this.jour.setDate(date);
                this.jour.setLibelle(JourLibelleEnum.values()[cal.get(Calendar.DAY_OF_WEEK) - 1]);
                this.jour.setMarquage(MarquageEnum.AUCUN);
                this.jourService.createOrUpdateJour(this.jour);
            }
            this.init();

            if (bundle.getBoolean("OPEN_NOTE_DIALOG")) {
                this.openDialog(null);
            }
        }
    }

    private void init() {
        this.noteAdapter = new NoteAdapter(this);
        List<Note> notes = new ArrayList<>();
        if (this.jour != null && this.jour.getNotes() != null && !this.jour.getNotes().isEmpty()) {
            notes.addAll(this.jour.getNotes());
        }
        this.noteAdapter.setListeNotes(notes);

        this.noteRecycler.setAdapter(noteAdapter);
        this.noteRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.noteRecycler.setHasFixedSize(true);

        this.boutonAjout.setOnClickListener((v) -> this.openDialog(null));
    }

    private void openDialog(Note note) {
        NoteDialog noteDialog = new NoteDialog(this, this.jour, note);
        noteDialog.setCallBack((v) -> {
            this.jour.setNotes(this.noteService.getNoteByJourId(this.jour.getId()));
            this.noteAdapter.setListeNotes((List<Note>) this.jour.getNotes());
        });
        noteDialog.show(getSupportFragmentManager(), "dialog");
    }
}