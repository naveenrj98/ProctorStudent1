package com.diptibelur.proctorstudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentMainCA extends AppCompatActivity {
    //private ListView lvca;
    //private StudentViewCA adapter;
    private List<CA> mCA;
  private DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_c);
/*
        lvca = (ListView)findViewById(R.id.list_of_announcements);
        mCA = new ArrayList<>();
        dr = FirebaseDatabase.getInstance().getReference("Common Announcements");

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    String atext =  zoneSnapshot.child("announcementText").getValue(String.class);
                    Long atime = zoneSnapshot.child("announcementTime").getValue(Long.class);
                    String auser =  zoneSnapshot.child("announcementUser").getValue(String.class);
                    String asem = zoneSnapshot.child("semesternumber").getValue(String.class);

                    mCA.add(new CA(atext,auser,asem));
                }

                adapter = new StudentViewCA(getApplicationContext(),mCA);
                lvca.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
