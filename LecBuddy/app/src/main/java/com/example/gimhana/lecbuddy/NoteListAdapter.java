package com.example.gimhana.lecbuddy;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gimhana on 10/25/2017.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public static final String DESCRIPTION = "";
    public static final String IMAGEURL = "";
    public static ArrayList<LecNote> noteList = new ArrayList<>();

    public NoteListAdapter(ArrayList<LecNote> noteList) {
        NoteListAdapter.noteList = noteList;
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notecard, parent, false);
        ViewHolder vh = new ViewHolder(cardview);
        return vh;
    }


    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, int position) {
        LecNote lecNote = noteList.get(position);
        holder.bindCard(lecNote);

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        ImageView LectureNoteImage;
        TextView Note_description_Title;

        private LecNote lecNote;

        public ViewHolder(View itemView) {
            super(itemView);
            LectureNoteImage = (ImageView) itemView.findViewById(R.id.LecNoteImage);
            Note_description_Title = (TextView) itemView.findViewById(R.id.Note_Des_Date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LecNote m = noteList.get(getPosition());
                    Log.i("TAG", "Clicked " + m.getNoteDate());
                    Intent i = new Intent(v.getContext(), NoteView.class);
                    //i.putExtra(DESCRIPTION, m.getNoteDate());
                    i.putExtra(IMAGEURL, m.getLecNoteImage());
//                    Bundle b=new Bundle();

                    v.getContext().startActivity(i);
                }
            });
        }

        public void bindCard(LecNote lecNote) {
            this.lecNote = lecNote;
            PicasoClient.downloadImage(itemView.getContext(), lecNote.getLecNoteImage(), LectureNoteImage);
            Note_description_Title.setText(lecNote.getNoteDate());
        }


    }
}
