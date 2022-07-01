package com.smapps.agenda.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.smapps.agenda.R;
import com.smapps.agenda.model.Note;

public class NoteComponent extends LinearLayout {

    private TextView titre;
    private TextView detail;

    public NoteComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context);
    }

    public NoteComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context);
    }

    private void initComponent(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.note_component_layout, this);

        this.titre = findViewById(R.id.titre);
        this.detail = findViewById(R.id.detail);
    }

    public void setNote(Note note) {
        this.titre.setText(note.getTitre());
        this.detail.setText(note.getDetail());
    }
}
