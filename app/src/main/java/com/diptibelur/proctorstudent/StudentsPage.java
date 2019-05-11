package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class StudentsPage extends AppCompatActivity {
    Button signout,announcements,chatwproctor;
    TextView showu, showp, showm,showt,showname;
    String key, pr_sem_key, value;
    HashMap<String, String> map = new HashMap<String, String>();
    private DatabaseReference dr;
    private DatabaseReference dr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_page);
        signout = (Button) findViewById(R.id.signoutbutton);


        showp = (TextView) findViewById(R.id.showParent);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentsPage.this, LoginStudent.class));
            }
        });

        announcements = (Button) findViewById(R.id.announcementsbutton);
        dr = FirebaseDatabase.getInstance().getReference("Student");

        announcements.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 final Intent i = new Intent(StudentsPage.this, StudentViewCA.class);
                                                 FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                                                 if (u != null) {
                                                     String userEmail1 = u.getEmail();
                                                     final Query studentQuery1 = dr.orderByChild("Email").equalTo(userEmail1);

                                                     studentQuery1.addChildEventListener(new ChildEventListener() {
                                                         @Override
                                                         public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                             map.clear();
                                                             //Get the node from the datasnapshot
                                                             String myParentNode = dataSnapshot.getKey();

                                                             String key1 = dataSnapshot.getKey().toString();
                                                             String pr_sem_key1 = dataSnapshot.child("Proctor_Sem_Key").getValue().toString();
                                                             String sem = dataSnapshot.child("Sem").getValue().toString();
                                                             i.putExtra("Proctor",pr_sem_key1);
                                                             i.putExtra("Sem",sem);
                                                             startActivity(i);

                                                         }

                                                         @Override
                                                         public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                         }

                                                         @Override
                                                         public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                         }

                                                         @Override
                                                         public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                         }

                                                         @Override
                                                         public void onCancelled(DatabaseError databaseError) {

                                                         }
                                                     });
                                                 }
                                             }
                                         });

           chatwproctor = (Button)findViewById(R.id.gotochatbutton);
           chatwproctor.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   final Intent i1 = new Intent(StudentsPage.this, ChatActivity.class);
                   FirebaseUser u1 = FirebaseAuth.getInstance().getCurrentUser();
                   if (u1 != null) {
                       String userEmail1 = u1.getEmail();
                       final Query studentQuery2 = dr.orderByChild("Email").equalTo(userEmail1);

                       studentQuery2.addChildEventListener(new ChildEventListener() {
                           @Override
                           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                               map.clear();
                               //Get the node from the datasnapshot
                               String myParentNode = dataSnapshot.getKey();

                               String key1 = dataSnapshot.getKey().toString();
                               String stname = dataSnapshot.child("Name").getValue().toString();
                               String prname15 = dataSnapshot.child("Proctor_Sem_Key").getValue().toString();
                               String prname16 = prname15.substring(0,prname15.length()-2);
                               i1.putExtra("Username",stname);
                               i1.putExtra("Student_Name", stname);
                               i1.putExtra("PSKey", prname15);
                               i1.putExtra("Proctor_Name",prname16);
                               i1.putExtra("User","Student");
                               startActivity(i1);

                           }

                           @Override
                           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                           }

                           @Override
                           public void onChildRemoved(DataSnapshot dataSnapshot) {

                           }

                           @Override
                           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                   }}

           });


            DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference("Student");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userEmail = user.getEmail();
                    //showu = (TextView) findViewById(R.id.showuid);
                    showm = (TextView) findViewById(R.id.months);
                    showt = (TextView) findViewById(R.id.times);
                    //showu.setText(userEmail);
                    final Query studentQuery = dr1.orderByChild("Email").equalTo(userEmail);

                    studentQuery.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            map.clear();
                            //Get the node from the datasnapshot
                            //String myParentNode = dataSnapshot.getKey();

                            key = dataSnapshot.getKey().toString();
                            pr_sem_key = dataSnapshot.child("Proctor_Sem_Key").getValue().toString();
                            //map.put(key,pr_sem_key );
                            showp.setText(pr_sem_key);
                            DatabaseReference dr2 = FirebaseDatabase.getInstance().getReference("Schedules").child(pr_sem_key);
                            dr2.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                   // String k = dataSnapshot.child(pr_sem_key).getValue().toString();
                                   // showproctorvalue1.setText(k);
                                   String sc = dataSnapshot.getValue().toString();
                                   String y = sc.substring(8,10);
                                   String m = sc.substring(17,19);
                                   String d = sc.substring(25,26);
                                  // String sc1 = sc.substring(0,sc.length()-4);

                                    String h = sc.substring(32,35);
                                    String mi = sc.substring(sc.length()-4,sc.length()-1);
                                  showm.setText(d+"/"+m+"/"+y);
                                    showt.setText(h+":"+mi);

                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }


                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }


    }



