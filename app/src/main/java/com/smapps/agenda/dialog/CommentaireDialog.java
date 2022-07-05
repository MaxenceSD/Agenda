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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.smapps.agenda.R;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.service.NoteService;
import com.smapps.agenda.utils.CallBack;

public class CommentaireDialog extends DialogFragment {

    private final Context context;
    private final Jour jour;
    private final JourService jourService;

    private EditText commentaire;
    private TextView annuler;
    private TextView valider;
    private CallBack<Void> callBack;


    public CommentaireDialog(Context context, Jour jour) {
        this.context = context;
        this.jour = jour;
        this.jourService = new JourService(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_commentaire_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.commentaire = view.findViewById(R.id.commentaire_edit);
        this.annuler = view.findViewById(R.id.annuler);
        this.valider = view.findViewById(R.id.valider);

        this.init();
    }

    private void init() {
        if (this.getDialog() != null && this.getDialog().getWindow() != null) {
            this.getDialog().setCanceledOnTouchOutside(false);
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (this.jour != null && !TextUtils.isEmpty(this.jour.getCommentaire())) {
            this.commentaire.setText(this.jour.getCommentaire());
        }

        this.annuler.setOnClickListener((v) -> {
            this.dismiss();
        });

        this.valider.setOnClickListener((v) -> {
            if (!TextUtils.isEmpty(this.commentaire.getText().toString())) {
                this.jour.setCommentaire(this.commentaire.getText().toString());
                this.jourService.createOrUpdateJour(this.jour);
                callBack.execute(null);
                this.dismiss();
            }
        });
    }

    public void setCallback(CallBack<Void> callBack) {
        this.callBack = callBack;
    }
}
