package com.example.android.mynotes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.mynotes.models.Note;
import com.example.android.mynotes.persistence.NoteRepository;

public class NoteActivity extends AppCompatActivity
        implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "NoteActivity";
    private static final int ENABLED_EDIT_MODE = 0;
    private static final int DISABLED_EDIT_MODE = 1;

    // ui components
    LinedEditText mLinedEditText;
    EditText mToolbarEditText;
    TextView mToolbarTextView;
    ImageButton mToolbarBackArrow, mToolbarPositiveCheck;

    // vars
    boolean mIsThisNewNote;
    Note mInitialNote;
    Note mFinalNote;
    GestureDetector mGestureDetector;
    int mMode;
    NoteRepository mNoteRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.et_note_activity);
        mToolbarEditText = findViewById(R.id.et_toolbar);
        mToolbarTextView = findViewById(R.id.tv_toolbar);
        mToolbarBackArrow = findViewById(R.id.b_back_arrow);
        mToolbarPositiveCheck = findViewById(R.id.b_positive_check);

        mNoteRepository = new NoteRepository(this);


        initialListener();

        receiveIntent();
        if (receiveIntent()){
            // edit mode
            setNewNoteProperties();
            enableEditMode();
        } else {
            // view mode
            setNoteProperties();
            disableEditMode();
        }

    }

    /**
     * checking if the activity has an Extra
     * @return true when it is a new note
     */
    public boolean receiveIntent(){
        if (getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            mFinalNote = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + mInitialNote.toString());
            mIsThisNewNote = false;
            return mIsThisNewNote;
        } else {
            mIsThisNewNote = true;
            return mIsThisNewNote;
        }
    }

    private void saveChanges(){
        if (mIsThisNewNote){
            saveNote();
            //        todo continue save changes (save new note or update it)
        } else {
            // update note
        }
    }

    private void saveNote(){
        mNoteRepository.insertNote(mFinalNote);
    }

    // setting initial properties to ui for new note
    public void setNewNoteProperties(){
        mToolbarEditText.setText(getString(R.string.new_note_default_title));
        mToolbarTextView.setText(getString(R.string.new_note_default_title));

        mInitialNote = new Note();
        mFinalNote = new Note();
        mInitialNote.setTitle("Note Title");
        mInitialNote.setContent("What are You thinking about");
        mInitialNote.setTimeStamp("Jun 2014");
        mFinalNote.setTitle("Note Title");

        Log.d(TAG, "setNewNoteProperties: mInitialNote = " + mInitialNote.toString()
                + "\n mFinalNote = " + mFinalNote.toString());

    }

    // setting initial properties to ui for existing note
    public void setNoteProperties(){
        mToolbarEditText.setText(mInitialNote.getTitle());
        mToolbarTextView.setText(mInitialNote.getTitle());
        mLinedEditText.setInputType(InputType.TYPE_NULL);
        mLinedEditText.setText(mInitialNote.getContent());
        Log.d(TAG, "setNoteProperties: mInitialNote = " + mInitialNote.toString());

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
        enableEditMode();
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
    // set OnClickListener to needed views
    public void initialListener() {
        mGestureDetector = new GestureDetector(this, this);
        mLinedEditText.setOnTouchListener(this);
        mToolbarPositiveCheck.setOnClickListener(this);
        mToolbarTextView.setOnClickListener(this);
        mToolbarBackArrow.setOnClickListener(this);
        mToolbarEditText.addTextChangedListener(this);

    }

    // set particular ui component to visible or not
    private void enableEditMode() {
        mToolbarBackArrow.setVisibility(View.GONE);
        mToolbarTextView.setVisibility(View.GONE);

        mToolbarPositiveCheck.setVisibility(View.VISIBLE);
        mToolbarEditText.setVisibility(View.VISIBLE);

        mMode = ENABLED_EDIT_MODE;

        enableEditContent();

        Log.d(TAG, "enableEditMode: Back Arrow = " + mToolbarBackArrow.getVisibility());
    }

    private void disableEditMode(){
        mToolbarBackArrow.setVisibility(View.VISIBLE);
        mToolbarTextView.setVisibility(View.VISIBLE);

        mToolbarPositiveCheck.setVisibility(View.GONE);
        mToolbarEditText.setVisibility(View.GONE);

        mMode = DISABLED_EDIT_MODE;

        disableEditContent();



        Log.d(TAG, "disableEditMode: Back Arrow = " + mToolbarBackArrow.getVisibility());

//        Check if the user typed something to note. If not the note won't be save
        String noteContent = mLinedEditText.getText().toString();
        noteContent.replace("\n", "");
        noteContent.replace(" ", "");
        if (noteContent.length() > 0){
            mFinalNote.setTitle(mToolbarEditText.getText().toString());
            mFinalNote.setContent(mLinedEditText.getText().toString());
            String timeStamp = "Jan 16";
            mFinalNote.setTimeStamp(timeStamp);

            // if the note was altered change it
            /**
             * todo in this condition there's a bug. If You make new note there's no content so
             * todo getContent produce nullpointerexception
             */
            if (!mInitialNote.getTitle().equals(mFinalNote.getTitle())
                    || !mInitialNote.getContent().equals(mFinalNote.getContent())){
                saveChanges();
            }
        }
    }

    private void disableEditContent(){
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableEditContent(){
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)
                this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null){
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.b_positive_check:
                hideSoftKeyboard();
                disableEditMode();
                break;

            case R.id.tv_toolbar:
                enableEditMode();
                mToolbarEditText.requestFocus();
                mToolbarEditText.setSelection(mToolbarEditText.length());
                break;

            case R.id.b_back_arrow:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == ENABLED_EDIT_MODE){
            disableEditMode();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if (mMode == ENABLED_EDIT_MODE){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mToolbarTextView.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
