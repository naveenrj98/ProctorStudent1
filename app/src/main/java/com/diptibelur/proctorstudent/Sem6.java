package com.diptibelur.proctorstudent;

/**
 * Created by ashokbelur on 4/3/2018.
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class Sem6 extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
     Button b1,chatwstudent6;
    TextView t1,show6;
    TextView showm6,showt6;
    String userid,proctorname;
    private DatabaseReference dr;
    int day,month,year,hour,minute;
    int dayF,monthF,yearF,hourF,minuteF;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.sem6, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Semester 6");
        Toast.makeText(getActivity(),"You are in Sem 6",Toast.LENGTH_SHORT).show();
        view.findViewById(R.id.signout6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ProctorMainPage.class);
                getActivity().startActivity(i);
                view.findViewById(R.id.signout6).setBackgroundColor(getResources().getColor(R.color.colorDarkGreen));
            }
        });
        //show6=view.findViewById(R.id.showSchedule6);
        showm6 = view.findViewById(R.id.month4);
        showt6 = view.findViewById(R.id.time4);
        view.findViewById(R.id.schedulesem6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d= new DatePickerDialog(getActivity(),Sem6.this,year,month,day);
                d.show();
            }
        });
        view.findViewById(R.id.ca6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),CommonAnnouncementPage.class);
                intent1.putExtra("Semester","6");
                getActivity().startActivity(intent1);
            }
        });
        /*view.findViewById(R.id.sr6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), StudentsReportPage.class);
                getActivity().startActivity(intent1);
            }
        });*/

        view.findViewById(R.id.signout6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginProctor.class));

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        dr = FirebaseDatabase.getInstance().getReference("Proctor");
        //show6 = view.findViewById(R.id.showSchedule6);
        if (user != null) {
            // Name, email address, and profile photo Url
            //String sc =textschedule.getText().toString();
            //Schedule schedule = new Schedule(sc);
            userid = user.getUid();

            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    proctorname = dataSnapshot.child(userid).child("Name").getValue(String.class);
                    Log.d(TAG, "Name"+ proctorname);
                    // show4.setText(proctorname);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

        /*view.findViewById(R.id.sendSchedule6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //String premail = user.getEmail();
                //show4 = view.findViewById(R.id.showSchedule4);
                String sc = show6.getText().toString();
                //Schedule schedule = new Schedule(sc);
                //dr.child(userid).child(proctorname+"_6").setValue(sc);
                //DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference("Student");
                //String pr_sem_key = proctorname+"_6";
                FirebaseDatabase.getInstance()
                        .getReference("Schedules").child(proctorname+"_6")
                        .setValue(new Schedule(sc)
                        );


            }


        });*/
        chatwstudent6 = view.findViewById(R.id.chatwithstudent6);
        chatwstudent6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ch = new Intent(getActivity(),ChatProctorList.class);

                ch.putExtra("Name",proctorname);
                ch.putExtra("PSKey",proctorname+"_6");
                ch.putExtra("User","Proctor");
                startActivity(ch);
            }
        });


    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearF = i;
        monthF = i1+1;
        dayF = i2;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog t= new TimePickerDialog(getActivity(),this,hour,minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        t.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourF=i;
        minuteF = i1;
        String sc = "Year: "+yearF+"\n"+
                "Month: "+monthF+"\n"+
                "Day: "+dayF+"\n"+
                "Hour: "+hourF+"\n"+
                "Minute: "+minuteF+"\n";
        String day  = ""+dayF+"/"+monthF+"/"+yearF;
        String time = ""+hourF+":"+minuteF;
        showm6.setText(day);
        showt6.setText(time);
        FirebaseDatabase.getInstance()
                .getReference("Schedules").child(proctorname+"_6")
                .setValue(new Schedule(sc)
                );
        Toast.makeText(getActivity(), "Meeting Scheduled at "+day+" "+time, Toast.LENGTH_SHORT).show();
    }
}


