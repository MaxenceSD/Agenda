package com.smapps.agenda.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.smapps.agenda.R;
import com.smapps.agenda.utils.CallBack;

import java.util.Calendar;
import java.util.Date;

public class CalendrierDialog extends DialogFragment {

    private DatePicker datePicker;
    private TextView boutonAujourdhui;
    private TextView boutonTerminer;

    private CallBack<Calendar> callBack;

    private Date jourSelected;

    public CalendrierDialog(Date jourSelected) {
        this.jourSelected = jourSelected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_calendrier_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.datePicker = view.findViewById(R.id.date_picker);
        this.boutonAujourdhui = view.findViewById(R.id.bouton_aujourd_hui);
        this.boutonTerminer = view.findViewById(R.id.bouton_terminer);

        this.init();
    }

    private void init() {
        this.datePicker.setOnDateChangedListener((view, annee, mois, jour) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, annee);
            cal.set(Calendar.MONTH, mois);
            cal.set(Calendar.DAY_OF_MONTH, jour);
            this.callBack.execute(cal);
        });

        this.boutonAujourdhui.setOnClickListener((v) -> this.callBack.execute(Calendar.getInstance()));
        this.boutonTerminer.setOnClickListener((v) -> dismiss());
    }

    public void setCallBack(CallBack<Calendar> callBack) {
        this.callBack = callBack;
    }
}
