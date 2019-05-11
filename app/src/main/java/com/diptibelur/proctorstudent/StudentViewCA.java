package com.diptibelur.proctorstudent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudentViewCA extends  AppCompatActivity {
    ListView lvca1;
    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter<String> ad;
    TextView t1,t2;

    private DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_c);
        lvca1 =  (ListView)findViewById( R.id.list_of_announcements);
        t1 =  (TextView)findViewById( R.id.sp);
        t2 =  (TextView) findViewById( R.id.stsem);
        Intent intent = getIntent();
        final String p = intent.getStringExtra("Proctor");
        final String s = intent.getStringExtra("Sem");
        final String pro = p.substring(0,p.length()-2);
        t1.setText("View " + pro + "'s Announcements");
        t2.setText(s);
        Query query = FirebaseDatabase.getInstance().getReference("Common Announcements");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {

                    String value =  zoneSnapshot.child("announcementText").getValue(String.class);
                    String user = zoneSnapshot.child("announcementUser").getValue(String.class);
                    String sem = zoneSnapshot.child("semesternumber").getValue(String.class);
                    if(user.contentEquals(pro)&&sem.contentEquals(s)) {
                        al.add(user + " says\n\n" + value);
                    }
                }
                ad = new ArrayAdapter<String>(StudentViewCA.this, android.R.layout.simple_list_item_1, al){
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
                };;
                lvca1.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });



    }
}

/*
    private Context mContext;
    private List<CA> mCA;

    public StudentViewCA(Context mContext,List<CA> mCA)
    {
        this.mContext = mContext;
        this.mCA = mCA;
    }
    @Override
    public int getCount() {
        return mCA.size();
    }

    @Override
    public Object getItem(int i) {
        return mCA.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext,R.layout.camessages,null);
        TextView announcementText = (TextView)v.findViewById(R.id.catext);
        TextView announcementUser = (TextView)v.findViewById(R.id.causer);
        TextView announcementTime = (TextView)v.findViewById(R.id.catime);
        TextView announcementSemNum = (TextView)v.findViewById(R.id.casemnum);

        announcementText.setText(mCA.get(i).getAnnouncementText());
        announcementUser.setText(mCA.get(i).getannouncementUser());
        announcementTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                mCA.get(i).getannouncementTime()));
        announcementSemNum.setText(mCA.get(i).getSemesternumber());
        v.setTag(mCA.get(i).getannouncementUser());
        //v.setTag(mCA.get(i).getId);
        return v;*/



   /* private void displayca()
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("Common Announcements").child("LA8pU_UCeh6LODUvOJ3");
        FirebaseListOptions<CA> options = new FirebaseListOptions.Builder<CA>()
                .setQuery(query, CA.class)
                .setLayout(R.layout.camessages)
                .build();
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_announcements);
        adapter = new FirebaseListAdapter<CA>(options) {
            @Override

            protected void populateView(View v, CA model, int position) {
                // Get references to the views of message.xml
                TextView announcementText = (TextView)v.findViewById(R.id.catext);
                TextView announcementUser = (TextView)v.findViewById(R.id.causer);
                TextView announcementTime = (TextView)v.findViewById(R.id.catime);
                TextView announcementSemNum = (TextView)v.findViewById(R.id.casemnum);

                // Set their text
                announcementText.setText(model.getAnnouncementText());
                announcementUser.setText(model.getannouncementUser());
                announcementSemNum.setText(model.getSemesternumber());

                // Format the date before showing it
                announcementTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getannouncementTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }*/
