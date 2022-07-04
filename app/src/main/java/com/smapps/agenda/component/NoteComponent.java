package com.smapps.agenda.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smapps.agenda.R;
import com.smapps.agenda.dialog.ActionDialog;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.utils.CallBack;

public class NoteComponent extends LinearLayout {

    private TextView titre;
    private TextView detail;
    private ImageView option;

    private Note note;
    private CallBack<Void> update;

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
        this.option = findViewById(R.id.option);

        this.option.setOnClickListener((v) -> {
            ActionDialog actionDialog = new ActionDialog(this.note);
            actionDialog.setOnUpdate(update);
            actionDialog.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), "ACTION_DIALOG");
        });
    }

    public void setNote(Note note) {
        this.note = note;
        this.titre.setText(note.getTitre());
        this.detail.setText(note.getDetail());
    }

    public void setOnUpdate(CallBack<Void> onUpdate) {
        this.update = onUpdate;
    }
}
