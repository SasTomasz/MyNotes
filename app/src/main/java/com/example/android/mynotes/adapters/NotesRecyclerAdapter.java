package com.example.android.mynotes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mynotes.R;
import com.example.android.mynotes.Utility.TimeStampUtility;
import com.example.android.mynotes.models.Note;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private ArrayList<Note> mNotes;
    private OnNoteListener mOnNoteListener;
    private static final String TAG = "NotesRecyclerAdapter";

    public NotesRecyclerAdapter(ArrayList<Note> mNotes, OnNoteListener onNoteListener) {
        this.mNotes = mNotes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_list,
                viewGroup, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try{
            String month = TimeStampUtility.getMonthName(mNotes.get(i).getTimeStamp()
                    .substring(0, 2));
            String year = mNotes.get(i).getTimeStamp().substring(3);
            String date = month + " " + year;
            viewHolder.title.setText(mNotes.get(i).getTitle());
            viewHolder.timestamp.setText(date);
        } catch(NullPointerException e){
            Log.d(TAG, "onBindViewHolder: " + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, timestamp;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_time_stamp);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
