package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProctorStudentMainPage extends AppCompatActivity {

    Button btns,btnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proctor_student_main_page);
        btnp = findViewById(R.id.btnplogin);
        btns = findViewById(R.id.btnslogin);

        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProctorStudentMainPage.this, LoginProctor.class));

            }
        });

        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProctorStudentMainPage.this, LoginStudent.class));

            }
        });
    }
}
