package com.example.android.mynotes.async;

import android.os.AsyncTask;

import com.example.android.mynotes.models.Note;
import com.example.android.mynotes.persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note,Void, Void> {
    NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
