package com.example.android.mynotes.async;

import android.os.AsyncTask;

import com.example.android.mynotes.models.Note;
import com.example.android.mynotes.persistence.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note,Void, Void> {
    NoteDao mNoteDao;

    public InsertAsyncTask (NoteDao dao){
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
