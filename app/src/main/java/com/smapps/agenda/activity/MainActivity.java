package com.smapps.agenda.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.smapps.agenda.R;
import com.smapps.agenda.component.SemaineComponent;
import com.smapps.agenda.utils.GestureEventEnum;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private SemaineComponent semaineComponent;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.semaineComponent = findViewById(R.id.semaine);
        this.selectedDate = Calendar.getInstance();
        this.mDetector = new GestureDetectorCompat(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.semaineComponent.setSemaineFromDate(this.selectedDate.getTime());
        this.semaineComponent.unselectAll();
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
            this.semaineComponent.setSemaineAnimationDescendante(this.selectedDate.getTime());
        } else {
            this.selectedDate.add(Calendar.DAY_OF_MONTH, 7);
            this.semaineComponent.setSemaineAnimationAscendante(this.selectedDate.getTime());
        }
        return true;
    }
}