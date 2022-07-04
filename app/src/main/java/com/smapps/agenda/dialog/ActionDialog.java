package com.smapps.agenda.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.smapps.agenda.R;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.service.NoteService;
import com.smapps.agenda.utils.CallBack;

import java.util.List;

public class ActionDialog extends DialogFragment {

    private LinearLayout modifier;
    private LinearLayout supprimer;
    private Note note;

    private NoteService noteService;

    private CallBack<Void> update;

    public ActionDialog(Note note) {
        this.note = note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_action_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.modifier = view.findViewById(R.id.modifier);
        this.supprimer = view.findViewById(R.id.supprimer);

        this.init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
    }

    private void init() {
        this.noteService = new NoteService(getContext());

        if (this.getDialog() != null && this.getDialog().getWindow() != null) {
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams wlp = this.getDialog().getWindow().getAttributes();

            wlp.gravity = Gravity.BOTTOM;
            wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            this.getDialog().getWindow().setAttributes(wlp);
        }

        this.modifier.setOnClickListener((v) -> {
            NoteDialog noteDialog = new NoteDialog(getContext(), null, this.note);
            noteDialog.setCallBack((c) -> {});
            noteDialog.setOnUpdate(update);
            noteDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "ACTION_DIALOG");
            dismiss();
        });

        this.supprimer.setOnClickListener((v) -> {
            this.noteService.deleteNote(this.note);
            update.execute(null);
            dismiss();
        });
    }

    public void setOnUpdate(CallBack<Void> onUpdate) {
        this.update = onUpdate;
    }
}