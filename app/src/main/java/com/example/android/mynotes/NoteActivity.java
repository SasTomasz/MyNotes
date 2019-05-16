package com.example.android.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.mynotes.models.Note;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";
    // ui components
    LinedEditText mLinedEditText;
    EditText mToolbarEditText;
    TextView mToolbarTextView;

    // vars
    boolean mIsThisNewNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.et_note_activity);
        mToolbarEditText = findViewById(R.id.et_toolbar);
        mToolbarTextView = findViewById(R.id.tv_toolbar);

        receiveIntent();
        if (receiveIntent()){
            // edit mode
        } else {
            // view mode
        }

    }

    public boolean receiveIntent(){
        // check the activity has an extra
        if (getIntent().hasExtra("selected_note")){
            Note note = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + note.toString());
            mIsThisNewNote = false;
            return mIsThisNewNote;
        }
        mIsThisNewNote = true;
        return mIsThisNewNote;
    }
}
