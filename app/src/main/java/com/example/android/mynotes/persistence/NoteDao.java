package com.example.android.mynotes.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.mynotes.models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    void delete(Note... notes);

    @Update
    void updateNotes(Note... notes);
}
