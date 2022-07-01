package com.smapps.agenda.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.smapps.agenda.R;
import com.smapps.agenda.component.SemaineComponent;
import com.smapps.agenda.db.ApplicationDatabase;
import com.smapps.agenda.model.Jour;
import com.smapps.agenda.model.Note;
import com.smapps.agenda.service.JourService;
import com.smapps.agenda.service.NoteService;
import com.smapps.agenda.utils.GestureEventEnum;
import com.smapps.agenda.utils.JourLibelleEnum;
import com.smapps.agenda.utils.MarquageEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private SemaineComponent semaineComponent;
    private Calendar selectedDate;

    private NoteService noteService;
    private JourService jourService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.selectedDate = Calendar.getInstance();

        this.semaineComponent = findViewById(R.id.semaine);
        this.semaineComponent.setSemaineFromDate(this.selectedDate.getTime());

        this.mDetector = new GestureDetectorCompat(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.semaineComponent.onGestureEvent(motionEvent, GestureEventEnum.SINGLE_TAP_UP);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        this.semaineComponent.onGestureEvent(motionEvent, GestureEventEnum.LONG_PRESS);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (v1 > 0) {
            this.selectedDate.add(Calendar.DAY_OF_MONTH, -7);
            this.semaineComponent.setSemaineToPrevious(this.selectedDate.getTime());
        } else {
            this.selectedDate.add(Calendar.DAY_OF_MONTH, 7);
            this.semaineComponent.setSemaineToNext(this.selectedDate.getTime());
        }
        return true;
    }
}