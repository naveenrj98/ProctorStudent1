package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatProctorList extends AppCompatActivity {
    ListView pl;
    ArrayList<String> alist = new ArrayList<String>();
    ArrayAdapter<String> adproctor;

    private DatabaseReference databaseReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_proctor_list);
        pl = (ListView)findViewById(R.id.proctorlist);
        Intent chp = getIntent();
        final String pskey = chp.getStringExtra("PSKey");
        Query q = FirebaseDatabase.getInstance().getReference("Student").orderByChild("Proctor_Sem_Key").equalTo(pskey);
        adproctor = new ArrayAdapter<String>(ChatProctorList.this, android.R.layout.simple_list_item_1, alist){
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
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alist.clear();
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    String value =  zoneSnapshot.child("Name").getValue(String.class);
                    alist.add(value);
                }

                pl.setAdapter(adproctor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });

        pl.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(getResources().getDrawable(R.drawable.color));
                Intent chat_activity = new Intent(ChatProctorList.this,ChatActivity.class);
                String item = pl.getItemAtPosition(i).toString();
                String pskey1 = pskey.substring(0,pskey.length()-2);
                chat_activity.putExtra("Username",pskey);
                chat_activity.putExtra("Proctor_Name",pskey1);
                chat_activity.putExtra("Student_Name",item);
                chat_activity.putExtra("PSKey",pskey);
                chat_activity.putExtra("User","Proctor");
                startActivity(chat_activity);
            }
        });
    }
}
