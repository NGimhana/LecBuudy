package com.example.gimhana.lecbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public final String FireBase = "TAG";
    private RecyclerView my_recycler_view;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<Module> mDataset=new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Modules/");
        my_recycler_view=(RecyclerView) findViewById(R.id.my_recycler_view);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataset.clear();
                for (DataSnapshot moduleInfo : dataSnapshot.getChildren()) {
                    String module_title = moduleInfo.getKey();
                    mDataset.add(new Module("", module_title));
                    refreshList();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.i(FireBase,dataSnapshot.getValue(String.class));
            }
        });
        refreshList();

    }

    public void refreshList() {
        myAdapter=new ListAdapter(mDataset);
        my_recycler_view.setAdapter(myAdapter);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        my_recycler_view.setLayoutManager(mLayoutManager);
    }
}
