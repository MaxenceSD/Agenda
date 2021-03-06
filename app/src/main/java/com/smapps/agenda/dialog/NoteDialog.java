package com.smapps.agenda.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.smapps.agenda.R;
import com.smapps.agenda.component.HeaderComponent;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.service.NoteService;
import com.smapps.agenda.utils.CallBack;

public class NoteDialog extends DialogFragment {

    private NoteService noteService;

    private EditText titre;
    private EditText detail;
    private TextView annuler;
    private TextView valider;

    private Context context;
    private CallBack callBack;
    private Jour jour;

    public NoteDialog(Context context, Jour jour) {
        this.context = context;
        this.jour = jour;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_dialog_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.titre = view.findViewById(R.id.titre_edit);
        this.detail = view.findViewById(R.id.detail_edit);
        this.annuler = view.findViewById(R.id.annuler);
        this.valider = view.findViewById(R.id.valider);

        this.noteService = new NoteService(context);

        this.init();
    }

    private void init() {
        if (this.getDialog() != null && this.getDialog().getWindow() != null) {
            this.getDialog().setCanceledOnTouchOutside(false);
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        this.annuler.setOnClickListener((v) -> dismiss());
        this.valider.setOnClickListener((v) -> {
            String strTitre = this.titre.getText().toString();
            String strDetail = this.detail.getText().toString();

            if (!TextUtils.isEmpty(strTitre)) {
                Note note = new Note(this.jour, strTitre, strDetail);
                this.noteService.createOrUpdateNote(note);
                this.callBack.execute();
                this.dismiss();
            } else {
                // TODO : animation titre manquant et obligatoire
            }
        });
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
