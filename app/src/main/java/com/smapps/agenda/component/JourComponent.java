package com.smapps.agenda.component;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.smapps.agenda.R;
import com.smapps.agenda.activity.ListeNoteActivity;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.utils.MarquageEnum;
import com.smapps.agenda.utils.MoisLibelleEnum;

import java.util.Calendar;

public class JourComponent extends ConstraintLayout {

    private JourService jourService;
    private Jour jour;

    private boolean selected = false;

    private ConstraintLayout constraintLayout;
    private TextView numeroJour;
    private TextView libelleJour;
    private TextView libelleMois;
    private TextView numeroAnnee;
    private ImageView note;
    private View markerRouge;
    private View markerMarron;

    public JourComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context);
    }

    public JourComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context);
    }

    private void initComponent(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.jour_component_layout, this);

        this.jourService = new JourService(context);

        this.constraintLayout = findViewById(R.id.constraint);
        this.numeroJour = findViewById(R.id.numero_jour);
        this.libelleJour = findViewById(R.id.jour_libelle);
        this.libelleMois = findViewById(R.id.mois_libelle);
        this.numeroAnnee = findViewById(R.id.annee_libelle);
        this.note = findViewById(R.id.note);
        this.markerRouge = findViewById(R.id.marker_rouge);
        this.markerMarron = findViewById(R.id.marker_marron);
    }

    public void setJour(Jour jour) {
        this.jour = jour;
        this.initValues();
        this.initIhm();
    }

    public void setJourAnimated(Jour jour, Long delay) {
        final Animator fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0.5f);
        fadeOut.setDuration(100);
        fadeOut.setStartDelay(delay * 10);

        final Animator fadeIn = ObjectAnimator.ofFloat(this, View.ALPHA, 0.5f, 1f);
        fadeIn.setDuration(200);
        fadeIn.setStartDelay(20);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                fadeOut,
                fadeIn
        );
        animatorSet.start();

        new Handler(Looper.getMainLooper()).postDelayed(
                () -> this.setJour(jour),
                100 + (delay * 10)
        );
    }

    public Jour getJour() {
        return this.jour;
    }

    private void initValues() {
        this.numeroJour.setText(jour.getStrId().substring(0, 2));
        this.libelleJour.setText(jour.getLibelle().toString());
        this.libelleMois.setText(MoisLibelleEnum.values()[Integer.parseInt(jour.getStrId().substring(2, 4))].toString());
        this.numeroAnnee.setText(jour.getStrId().substring(4));
        this.note.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), ListeNoteActivity.class);
            intent.putExtra("JOUR_ID", jour.getStrId());
            getContext().startActivity(intent);
        });
    }

    private void initIhm() {
        Calendar dateActuelle = Calendar.getInstance();
        Calendar dateJour = Calendar.getInstance();
        dateJour.setTime(jour.getDate());

        setJourActuel(dateActuelle.get(Calendar.DAY_OF_MONTH) == dateJour.get(Calendar.DAY_OF_MONTH)
                && dateActuelle.get(Calendar.MONTH) == dateJour.get(Calendar.MONTH)
                && dateActuelle.get(Calendar.YEAR) == dateJour.get(Calendar.YEAR));

        if (MarquageEnum.DEMI.equals(this.jour.getMarquage())) {
            this.markerMarron.setAlpha(1);
            this.markerRouge.setAlpha(0);
        } else if (MarquageEnum.COMPLET.equals(this.jour.getMarquage())) {
            this.markerMarron.setAlpha(0);
            this.markerRouge.setAlpha(1);
        } else {
            this.markerMarron.setAlpha(0);
            this.markerRouge.setAlpha(0);
        }

        if (this.jour.getNotes() == null || this.jour.getNotes().isEmpty()) {
            this.note.setVisibility(View.GONE);
        } else {
            this.note.setVisibility(View.VISIBLE);
        }
    }

    public void setJourActuel(boolean jourActuel) {
        if (jourActuel) {
            this.numeroJour.setTextColor(getResources().getColor(R.color.rouge_1, null));
            this.libelleMois.setTextColor(getResources().getColor(R.color.rouge_2, null));
            this.numeroAnnee.setTextColor(getResources().getColor(R.color.rouge_2, null));
            this.libelleJour.setTextColor(getResources().getColor(R.color.rouge_3, null));
            this.note.setColorFilter(getResources().getColor(R.color.rouge_1, null));
        } else {
            this.numeroJour.setTextColor(getResources().getColor(R.color.gris_6, null));
            this.libelleMois.setTextColor(getResources().getColor(R.color.gris_9, null));
            this.numeroAnnee.setTextColor(getResources().getColor(R.color.gris_9, null));
            this.libelleJour.setTextColor(getResources().getColor(R.color.gris_c, null));
            this.note.setColorFilter(getResources().getColor(R.color.gris_6, null));
        }
    }

    public void onLongPress() {
        this.jour.setMarquage(this.jour.getMarquage().avancerEtape());
        this.saveJour();

        if (MarquageEnum.DEMI.equals(this.jour.getMarquage())) {
            afficherMarker(this.markerMarron, 0L);
            cacherMarker(this.markerRouge, 0L);
        } else if (MarquageEnum.COMPLET.equals(this.jour.getMarquage())) {
            cacherMarker(this.markerMarron, 0L);
            afficherMarker(this.markerRouge, 100L);
        } else {
            cacherMarker(this.markerMarron, 0L);
            cacherMarker(this.markerRouge, 0L);
        }
    }

    private void afficherMarker(View view, Long delay) {
        view.setAlpha(1);
        view.setX(view.getRight());
        view.animate().translationX(0).setDuration(250).setStartDelay(delay);
    }

    private void cacherMarker(View view, Long delay) {
        view.animate()
                .alpha(0)
                .translationX(view.getRight())
                .setDuration(500)
                .setStartDelay(delay);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void select() {
        if (!this.selected) {
            TransitionDrawable transition = (TransitionDrawable) constraintLayout.getBackground();
            transition.startTransition(250);
        }

        this.numeroJour.setTextColor(getResources().getColor(R.color.blanc, null));
        this.libelleMois.setTextColor(getResources().getColor(R.color.blanc, null));
        this.numeroAnnee.setTextColor(getResources().getColor(R.color.blanc, null));
        this.libelleJour.setTextColor(getResources().getColor(R.color.blanc, null));
        this.note.setColorFilter(getResources().getColor(R.color.blanc, null));

        this.selected = true;
    }

    public void unselect() {
        if (this.selected) {
            TransitionDrawable transition = (TransitionDrawable) constraintLayout.getBackground();
            transition.reverseTransition(250);
        }

        this.selected = false;

        this.initIhm();
    }

    private void saveJour() {
        this.jourService.createOrUpdateJour(this.jour);
    }
}
