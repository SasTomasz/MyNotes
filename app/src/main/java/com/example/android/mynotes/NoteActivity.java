package com.example.android.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.mynotes.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "NoteActivity";
    // ui components
    LinedEditText mLinedEditText;
    EditText mToolbarEditText;
    TextView mToolbarTextView;

    // vars
    boolean mIsThisNewNote;
    Note mInitialNote;
    GestureDetector mGestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.et_note_activity);
        mToolbarEditText = findViewById(R.id.et_toolbar);
        mToolbarTextView = findViewById(R.id.tv_toolbar);

        initialListener();

        receiveIntent();
        if (receiveIntent()){
            // edit mode
            setNewNoteProperties();
        } else {
            // view mode
            setNoteProperties();
        }

    }

    /**
     * checking if the activity has an extra and if is it a new note
     * return true if it is new note
     */
    public boolean receiveIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + mInitialNote.toString());
            mIsThisNewNote = false;
            return mIsThisNewNote;
        } else {
            mIsThisNewNote = true;
            return mIsThisNewNote;
        }
    }

    // setting initial properties to ui for new note
    public void setNewNoteProperties(){
        mToolbarEditText.setText(getString(R.string.new_note_default_title));
        mToolbarTextView.setText(getString(R.string.new_note_default_title));

    }

    // setting initial properties to ui for existing note
    public void setNoteProperties(){
        mToolbarEditText.setText(mInitialNote.getTitle());
        mToolbarTextView.setText(mInitialNote.getTitle());
        mLinedEditText.setInputType(InputType.TYPE_NULL);
        mLinedEditText.setText(mInitialNote.getContent());

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: double click event");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    // setting GestureDetector in order turn on edit mode in EditText after double click the view
    public void initialListener() {
        mGestureDetector = new GestureDetector(this, this);
        mLinedEditText.setOnTouchListener(this);
    }
}
