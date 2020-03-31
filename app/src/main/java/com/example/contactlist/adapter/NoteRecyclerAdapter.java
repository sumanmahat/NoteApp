package com.example.contactlist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactlist.R;
import com.example.contactlist.models.Note;
import com.example.contactlist.utils.Utility;

import java.util.ArrayList;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>{
    private static final String TAG = "NoteRecyclerAdapter";

    private ArrayList<Note> mNotes = new ArrayList<>();
    private OnNoteListner onNoteListner;

    public NoteRecyclerAdapter(ArrayList<Note> mNotes, OnNoteListner onNoteListner) {
        this.mNotes = mNotes;
        this.onNoteListner = onNoteListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item,viewGroup,false);
        return new ViewHolder(view, onNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String month = mNotes.get(position).getTimeStamp().substring(0,2);
            month = Utility.getMonthFromNumber(month);
            String year = mNotes.get(position).getTimeStamp().substring(3);
            String timestamp = month + " " + year;
            holder.timestamp.setText(mNotes.get(position).getTimeStamp());
            holder.title.setText(mNotes.get(position).getTitle());
        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: NullPointerException"+ e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, timestamp;
        OnNoteListner onNoteListner;

        public ViewHolder(@NonNull View itemView, OnNoteListner onNoteListner) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_time);
            this.onNoteListner = onNoteListner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListner.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListner{
        void onNoteClick(int posiion);
    }
}
