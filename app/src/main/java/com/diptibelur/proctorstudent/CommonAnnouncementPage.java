package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CommonAnnouncementPage extends AppCompatActivity {
    Button b1;
    TextView t1;
    String userid1;
    EditText e1;
    RelativeLayout c1;
    ListView lv;
    String proctorname1;
    String semester;
    int count=0;
    ArrayList<String> al;
    ArrayAdapter<String> ad;
    private DatabaseReference dr;
    private FirebaseAuth mAuth;


    //TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_announcement_page);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        b1 = (Button) findViewById(R.id.publish);
        t1 = (TextView) findViewById(R.id.textView10);
        e1 = (EditText) findViewById(R.id.publishText);
        lv = (ListView) findViewById(R.id.listView);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.myRelativeLayout);
        c1 = (RelativeLayout) findViewById(R.id.myRelativeLayout1);
        //t1=(TextView)findViewById(R.id.announcement1);
        al = new ArrayList<String>();
        ad = new ArrayAdapter<String>(CommonAnnouncementPage.this, android.R.layout.simple_list_item_1, al){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        lv.setAdapter(ad);
        /*Intent i1 = getIntent();
        String uid1 = i1.getStringExtra("USERID");
        t1.setText(uid1);*/
        Intent i = getIntent();

        semester =  i.getStringExtra("Semester");
        dr = FirebaseDatabase.getInstance().getReference("Proctor");
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null) {
            // Name, email address, and profile photo Url
            //String sc =textschedule.getText().toString();
            //Schedule schedule = new Schedule(sc);
            userid1 = user1.getUid();



            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    proctorname1 = dataSnapshot.child(userid1).child("Name").getValue(String.class);
                    Log.d(TAG, "Name"+ proctorname1);
                    t1.setText(proctorname1+" "+semester );
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = "";

                a = e1.getText().toString();
                al.add(a);
                ad.notifyDataSetChanged();




                FirebaseDatabase.getInstance()
                        .getReference("Common Announcements").child(proctorname1+"_"+semester+Integer.toString(count++))
                        .setValue(new CA(e1.getText().toString(),
                                proctorname1, semester)
                        );

                e1.setText("");

            }
        });
    }
}







