package com.smapps.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smapps.agenda.R;
import com.smapps.agenda.component.NoteComponent;
import com.smapps.agenda.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private NoteComponent noteGauche;
        private NoteComponent noteDroite;

        public NoteViewHolder(View itemView, NoteComponent noteGauche, NoteComponent noteDroite) {
            super(itemView);
            this.noteGauche = noteGauche;
            this.noteDroite = noteDroite;
        }
    }

    private final Context context;
    private List<Note> notes;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.note_adapter_layout, parent, false);

        NoteComponent noteGauche = view.findViewById(R.id.note_gauche);
        NoteComponent noteDroite = view.findViewById(R.id.note_droite);

        return new NoteViewHolder(view, noteGauche, noteDroite);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder viewHolder, int position) {
        int positionGauche = 2 * position;
        int positionDroite = positionGauche + 1;

        Note note = this.notes.get(positionGauche);

        viewHolder.noteGauche.setNote(note);

        if (this.notes.size() > positionDroite) {
            viewHolder.noteDroite.setVisibility(View.VISIBLE);
            viewHolder.noteDroite.setNote(this.notes.get(positionDroite));
        } else {
            viewHolder.noteDroite.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (this.notes.size() / 2) + (this.notes.size() % 2);
    }

    public void setListeNotes(List<Note> notes) {
        this.notes = new ArrayList<>();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }
}
