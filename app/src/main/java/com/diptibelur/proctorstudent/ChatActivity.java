package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    //private EditText input;
    //private DatabaseReference chat_data_ref;
    private DatabaseReference user_name_ref;
    //private ListView listOfMessages;
    int prcount = 0;
    private FirebaseAuth mAuth;
    private FirebaseListAdapter<Chat> adapter;
    private String st_name_u,username,msguser1,pr_name_u;
    private String proctor_sem_key1, userintent;
    Button send;
    ArrayList<String> al1 = new ArrayList<String>();
    ArrayAdapter<String> ad1,ad2;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth= FirebaseAuth.getInstance();
        //input=(EditText)findViewById(R.id.input);
        Intent ch = getIntent();
        username = ch.getStringExtra("Username");
       st_name_u =  ch.getStringExtra("Student_Name");
       proctor_sem_key1 = ch.getStringExtra("PSKey");
       userintent = ch.getStringExtra("User");
       pr_name_u = ch.getStringExtra("Proctor_Name");
        //Query query= FirebaseDatabase.getInstance().getReference().child("Chat_text");
      //  user_name_ref=FirebaseDatabase.getInstance().getReference(");
        //listOfMessages=(ListView)findViewById(R.id.list_of_messages);
        map=new HashMap<>();
        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                if(userintent.contentEquals("Student")) {
                    FirebaseDatabase.getInstance()
                            .getReference("Chat_text")
                            .push()
                            .setValue(new Chat(input.getText().toString(),
                                    st_name_u, proctor_sem_key1)
                            );
                }
                else if(userintent.contentEquals("Proctor"))
                {
                    FirebaseDatabase.getInstance()
                            .getReference("Chat_text")
                            .push()
                            .setValue(new Chat(input.getText().toString(),
                                    proctor_sem_key1, st_name_u)
                            );
                }
                    // Clear the input
                    input.setText("");

            }
        });
        displayChatMessages();
    }

    private void displayChatMessages()
    {
        final ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        ad1 = new ArrayAdapter<String>(ChatActivity.this, android.R.layout.simple_list_item_1, al1){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
               // tv.setGravity(Gravity.RIGHT);

                // Generate ListView Item using TextView
                return view;
            }
        };

        Query query = FirebaseDatabase.getInstance().getReference("Chat_text");

      query.addValueEventListener(new ValueEventListener() {

          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              al1.clear();
              for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {

                  String msgText =  zoneSnapshot.child("messageText").getValue(String.class);
                  String msguser = zoneSnapshot.child("messageUser").getValue(String.class);
                  if(msguser.contentEquals(proctor_sem_key1))
                  {
                      msguser1 = msguser.substring(0,msguser.length()-2);
                  }
                  else
                  {
                      msguser1 = msguser;
                  }
                  String msgTo = zoneSnapshot.child("messageTo").getValue(String.class);
                  Long msgTime = zoneSnapshot.child("messageTime").getValue(Long.class);
                  if((msgTo.contentEquals(proctor_sem_key1)&&msguser.contentEquals(st_name_u))||(msgTo.contentEquals(st_name_u)&&msguser.contentEquals(proctor_sem_key1)))
                  {
                     if(msguser.contentEquals(username)) {
                         al1.add("                                                                 You said\n\n"+"                                                           "+msgText);


                         ad1.notifyDataSetChanged();
                     }
                     else
                     {
                        al1.add(msguser1+ " says\n\n"+msgText);

                        ad1.notifyDataSetChanged();
                     }

                  }

              }

              listOfMessages.setAdapter(ad1);


          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });



/*
        Query query1 = FirebaseDatabase.getInstance().getReference("Chat_text").orderByChild("messageTo").equalTo(proctor_sem_key1);

        FirebaseListOptions<Chat> options1 = new FirebaseListOptions.Builder<Chat>()
                .setQuery(query1, Chat.class)
                .setLayout(R.layout.message)
                .build();
         adapter=new FirebaseListAdapter<Chat>(
                options1
        ){
            @Override
            protected void populateView(View v, Chat model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTo = (TextView)v.findViewById(R.id.message_to);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTo.setText(model.getMessageTo());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);*/
    }
}







       /* user_name_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                name=dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        FirebaseListOptions<Chat> options1 = new FirebaseListOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .setLayout(R.layout.individual_row)
                .build();
        FirebaseListAdapter<Chat> adapter=new FirebaseListAdapter<Chat>(
                options1
        ) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                TextView msg=(TextView)v.findViewById(R.id.ir1);
                msg.setText(model.getUser_name()+" : "+model.getMessage());
            }

        };
        listView.setAdapter(adapter);
        send = (Button)findViewById(R.id.sendid);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Chat_text").child(name_u).setValue(new Chat(editText.getText().toString(),name_u));//storing actual msg with name of the user
                editText.setText("");//clear the msg in edittext
            }
        });

        */







