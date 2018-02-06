package com.example.gimhana.lecbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainNoteActivity extends AppCompatActivity {

    final String temp = "TAG";
    private RecyclerView noteRecyclerView;
    private RecyclerView.Adapter noteAdapter;
    private ArrayList<LecNote> lecNoteList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);
        noteRecyclerView = (RecyclerView) findViewById(R.id.noteRecyclerView);


        Intent i = getIntent();
        Bundle bun = i.getExtras();
        String description = bun.getString(ListAdapter.DESCRIPTION);
        Log.i(temp, description);

        final FirebaseDatabase databse = FirebaseDatabase.getInstance();
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        final DatabaseReference reference = databse.getReference("Modules/" + description + "/Notes");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lecNoteList.clear();
                for (DataSnapshot lecInfo : dataSnapshot.getChildren()) {

                    String lecDate = lecInfo.child("Date").getValue(String.class);
                    String lecImage = lecInfo.child("Image").getValue(String.class);

                    Log.i(temp, lecImage);
                    lecNoteList.add(new LecNote(lecImage, "", lecDate));
                    refreshList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refreshList();

    }

    public void refreshList() {
        noteAdapter = new NoteListAdapter(lecNoteList);
        noteRecyclerView.setAdapter(noteAdapter);
        mLayoutManager = new LinearLayoutManager(MainNoteActivity.this);
        noteRecyclerView.setLayoutManager(mLayoutManager);
    }
}
