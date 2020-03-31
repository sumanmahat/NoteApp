package com.example.contactlist.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.contactlist.models.Note;
import com.example.contactlist.persistance.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
    private static final String TAG = "DeleteAsyncTask";

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread" + Thread.currentThread().getName());
        mNoteDao.delete(notes);
        return null;
    }
}
