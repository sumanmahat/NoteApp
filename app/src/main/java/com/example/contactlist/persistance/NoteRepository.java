package com.example.contactlist.persistance;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.contactlist.async.DeleteAsyncTask;
import com.example.contactlist.async.InsertAsyncTask;
import com.example.contactlist.async.UpdateAsyncTask;
import com.example.contactlist.models.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context){
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }

    public LiveData<List<Note>> retriveNotesTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){

        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}
