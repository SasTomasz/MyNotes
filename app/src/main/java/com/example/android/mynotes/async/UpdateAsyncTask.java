package com.example.android.mynotes.async;

import android.os.AsyncTask;

import com.example.android.mynotes.models.Note;
import com.example.android.mynotes.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note,Void, Void> {
    NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.updateNotes(notes);
        return null;
    }
}
