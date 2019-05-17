package com.example.android.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.mynotes.Utility.RecyclerDecoration;
import com.example.android.mynotes.adapters.NotesRecyclerAdapter;
import com.example.android.mynotes.models.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {
    private ArrayList<Note> mNotes = new ArrayList<>();
    public RecyclerView mRecyclerView;
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecycler();
        setTestData();
    }

    public void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerDecoration recyclerDecoration = new RecyclerDecoration(8);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.addItemDecoration(recyclerDecoration);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);
    }

    public void setTestData(){
        for (int i = 0; i < 1000; i++) {
            mNotes.add(new Note());
            mNotes.get(i).setTitle("Title:" + i);
            mNotes.get(i).setTimeStamp("Jan 2019");
            mNotes.get(i).setContent("Content: " + i);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: #" + position + " position");

        // start new activity with note content
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);

    }
}
