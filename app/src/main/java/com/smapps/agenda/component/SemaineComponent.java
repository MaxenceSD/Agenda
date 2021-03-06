package com.smapps.agenda.component;

import android.app.appsearch.GetSchemaResponse;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import com.smapps.agenda.R;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.utils.GestureEventEnum;
import com.smapps.agenda.utils.JourLibelleEnum;
import com.smapps.agenda.utils.MarquageEnum;
import com.smapps.agenda.utils.MoisLibelleEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SemaineComponent extends ConstraintLayout {

    private JourComponent selectedComponent;
    private JourService jourService;

    private HeaderComponent headerTitre;
    private HeaderComponent headerModification;

    private JourComponent[] semaine;

    public SemaineComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context);
    }

    public SemaineComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context);
    }

    private void initComponent(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.semaine_component_layout, this);

        this.jourService = new JourService(context);

        this.headerTitre = findViewById(R.id.header_titre);
        this.headerModification = findViewById(R.id.header_jour_modification);

        this.semaine = new JourComponent[7];
        this.semaine[0] = findViewById(R.id.lundi);
        this.semaine[1] = findViewById(R.id.mardi);
        this.semaine[2] = findViewById(R.id.mercredi);
        this.semaine[3] = findViewById(R.id.jeudi);
        this.semaine[4] = findViewById(R.id.vendredi);
        this.semaine[5] = findViewById(R.id.samedi);
        this.semaine[6] = findViewById(R.id.dimanche);
    }

    public void setSemaine(List<Jour> semaine) {
        for (int i = 0; i < this.semaine.length; i++) {
            this.semaine[i].setJour(semaine.get(i));
        }
    }

    public void setSemaineFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int jourDeLaSemaine = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (jourDeLaSemaine == 0) {
            jourDeLaSemaine = 7;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -(jourDeLaSemaine - 1));

        for (int i = 0; i < 7; i++) {
            String jourId = this.jourService.buildIdFromDate(calendar);

            Jour jourBdd = this.jourService.getJourByStrId(jourId);

            if (jourBdd != null) {
                this.semaine[i].setJour(jourBdd);
            } else {
                Jour jour = new Jour();
                jour.setStrId(jourId);
                jour.setDate(calendar.getTime());
                jour.setLibelle(JourLibelleEnum.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                jour.setMarquage(MarquageEnum.AUCUN);
                this.semaine[i].setJour(jour);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void setSemaineToPrevious(Date date) {
        this.unselectAll();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int jourDeLaSemaine = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (jourDeLaSemaine == 0) {
            jourDeLaSemaine = 7;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -(jourDeLaSemaine - 1));

        for (int i = 0; i < 7; i++) {
            String jourId = this.jourService.buildIdFromDate(calendar);

            Jour jourBdd = this.jourService.getJourByStrId(jourId);

            if (jourBdd != null) {
                this.semaine[i].setJourAnimated(jourBdd, (long) i);
            } else {
                Jour jour = new Jour();
                jour.setStrId(jourId);
                jour.setDate(calendar.getTime());
                jour.setLibelle(JourLibelleEnum.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                jour.setMarquage(MarquageEnum.AUCUN);
                this.semaine[i].setJourAnimated(jour, (long)i);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void setSemaineToNext(Date date) {
        this.unselectAll();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int jourDeLaSemaine = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (jourDeLaSemaine == 0) {
            jourDeLaSemaine = 7;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -(jourDeLaSemaine - 1));

        List<Jour> jours = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            String jourId = this.jourService.buildIdFromDate(calendar);

            Jour jourBdd = this.jourService.getJourByStrId(jourId);

            if (jourBdd != null) {
                jours.add(jourBdd);
            } else {
                Jour jour = new Jour();
                jour.setStrId(jourId);
                jour.setDate(calendar.getTime());
                jour.setLibelle(JourLibelleEnum.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                jour.setMarquage(MarquageEnum.AUCUN);
                jours.add(jour);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        for (int i = jours.size() - 1; i >= 0; i--) {
            this.semaine[i].setJourAnimated(jours.get(i), (long)jours.size() - 1 - i);
        }
    }

    private void selectJour(JourComponent jc) {
        if (this.selectedComponent != null && this.selectedComponent != jc) {
            this.unselectAll();
        }
        if (jc.isSelected()) {
            jc.unselect();
            this.selectedComponent = null;
            this.afficherHeaderModification(false);
            this.cacheHeaderTitre(false);
        } else {
            jc.select();
            this.selectedComponent = jc;
            this.afficherHeaderModification(true);
            this.cacheHeaderTitre(true);

            String t = this.selectedComponent.getJour().getStrId().substring(0, 2);
            t += " " + MoisLibelleEnum.values()[Integer.parseInt(this.selectedComponent.getJour().getStrId().substring(2, 4))].toString();
            t += " " + this.selectedComponent.getJour().getStrId().substring(4);
            this.headerModification.setTitre(t);
        }
    }

    private void unselectAll() {
        for (JourComponent jourComponent : this.semaine) {
            jourComponent.unselect();
            this.selectedComponent = null;
            this.afficherHeaderModification(false);
            this.cacheHeaderTitre(false);
        }
    }

    private void afficherHeaderModification(boolean afficher) {
        if (afficher) {
            this.headerModification.animate()
                    .translationY(this.headerModification.getHeight())
                    .setDuration(300);
        } else {
            this.headerModification.animate()
                    .translationY(0)
                    .setDuration(300);
        }
    }

    private void cacheHeaderTitre(boolean cacher) {
        if (cacher) {
            this.headerTitre.animate().alpha(0).setDuration(200);
        } else {
            this.headerTitre.animate().alpha(1).setDuration(200).setStartDelay(100);
        }
    }

    public void onGestureEvent(MotionEvent event, GestureEventEnum type) {
        if (Arrays.asList(GestureEventEnum.LONG_PRESS, GestureEventEnum.SINGLE_TAP_UP).contains(type)) {
            float x = event.getX();
            float y = event.getY();

            for (JourComponent component : this.semaine) {
                int[] test = new int[2];
                component.getLocationInWindow(test);

                float compXMin = test[0];
                float compXMax = test[0] + component.getWidth();
                float compYMin = test[1];
                float compYMax = test[1] + component.getHeight();

                if (x >= compXMin && x <= compXMax && y >= compYMin && y <= compYMax) {
                    if (type.equals(GestureEventEnum.LONG_PRESS)) {
                        component.onLongPress();
                    } else if (type.equals(GestureEventEnum.SINGLE_TAP_UP)) {
                        selectJour(component);
                    }
                    break;
                }
            }
        }
    }
}
