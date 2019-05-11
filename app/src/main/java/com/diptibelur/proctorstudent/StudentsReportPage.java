package com.diptibelur.proctorstudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsReportPage extends AppCompatActivity {
    TextView pn;

    ListView lv1;
            ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter<String> ad;

    private DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_report_page);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //String t = toolbar.getTitle().toString();
        pn=(TextView)findViewById(R.id.proctorname);
        //String proctorname1 = pn.getText().toString();
        dr = FirebaseDatabase.getInstance().getReference("Student");
        //DatabaseReference dr1 = dr.child("ZONE_1");
         lv1 =  (ListView)findViewById( R.id.lv1);





        Query q = dr.orderByChild("Proctor_Sem_Key").equalTo("Mr Selvakumar_6");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                   String value =  zoneSnapshot.child("Name").getValue(String.class);
                   al.add(value);
                }
                ad = new ArrayAdapter<String>(StudentsReportPage.this, android.R.layout.simple_list_item_1, al);
                lv1.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                  Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
