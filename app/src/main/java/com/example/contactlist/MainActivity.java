package com.example.contactlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contactlist.adapter.NoteRecyclerAdapter;
import com.example.contactlist.models.Note;
import com.example.contactlist.persistance.NoteRepository;
import com.example.contactlist.utils.VerticalSpaceDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteRecyclerAdapter.OnNoteListner,
        View.OnClickListener {
    private static final String TAG = "MainActivity";

    //UI component
    private RecyclerView recyclerView;

    //vars
    private ArrayList<Note> mNotes =  new ArrayList<>();
    private NoteRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.fab).setOnClickListener(this);
        mNoteRepository = new NoteRepository(this);

        initRecycleView();
        retrieveNote();
     //   FakeNotes();

        Log.d(TAG, "onCreate: thread:" + Thread.currentThread().getName());
        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");

    }

    //to retrive data from data base
    private void retrieveNote(){
        mNoteRepository.retriveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNotes.size() > 0){
                    mNotes.clear();
                }
                if(notes != null) {
                    mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void FakeNotes(){
        for (int i=0; i < 1000; i++){
            Note note = new Note();
            note.setTitle("title #" + i);
            note.setContent("content #:"+ i);
            note.setTimeStamp("March 2020");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private  void initRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpaceDecorator itemDecorator = new VerticalSpaceDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(mNotes, this);
        recyclerView.setAdapter(mNoteRecyclerAdapter);

    }

    @Override
    public void onNoteClick(int posiion) {
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note",mNotes.get(posiion));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }

    //method to remove note/delete
    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNote(note);
    }

    //swip to delete note
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
