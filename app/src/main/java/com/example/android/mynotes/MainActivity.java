package com.example.android.mynotes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.mynotes.Utility.RecyclerDecoration;
import com.example.android.mynotes.adapters.NotesRecyclerAdapter;
import com.example.android.mynotes.models.Note;
import com.example.android.mynotes.persistence.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        NotesRecyclerAdapter.OnNoteListener,
        View.OnClickListener {

    //ui elements
    public RecyclerView mRecyclerView;
    public FloatingActionButton fab;

    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteRepository = new NoteRepository(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        initRecycler();
        retrieveData();
//        setTestData();
    }

    public void retrieveData(){
        mNoteRepository.retrieveNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if (mNotes.size() > 0){
                    mNotes.clear();
                }

                if (notes != null){
                    mNotes.addAll(notes);
                }

                mNotesRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerDecoration recyclerDecoration = new RecyclerDecoration(8);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView);
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
    // start NoteActivity as new note
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRepository.deleteNote(note);
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }

    // add delete by swiping feature
    private ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
