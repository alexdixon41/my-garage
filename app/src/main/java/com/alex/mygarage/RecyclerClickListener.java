package com.alex.mygarage;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Listen for single tap events on garageRecyclerView items to launch the detailed information
 * fragment for the selected vehicle
 */
public class RecyclerClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private RecyclerView garageRecyclerView;
    private GenericClickListener clickListener;

    public RecyclerClickListener(Context context, RecyclerView garageRecyclerView, GenericClickListener clickListener) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            // only detect SingleTapUp events

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        this.garageRecyclerView = garageRecyclerView;
        this.clickListener = clickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (view != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            // fire the click event to select the item if motion event was a single tap
            clickListener.onClick(view, garageRecyclerView.getChildAdapterPosition(view));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
