package com.example.android.mynotes.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.android.mynotes.async.InsertAsyncTask;
import com.example.android.mynotes.models.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase mNoteDatabase;

    public NoteRepository (Context context){
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNote(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotes(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){

    }

    public void updateNote(Note note){

    }
}