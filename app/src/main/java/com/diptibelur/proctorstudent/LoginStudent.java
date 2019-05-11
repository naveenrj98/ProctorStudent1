package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginStudent extends AppCompatActivity {

    EditText email,pass;
    TextView register;
    Button stlogin,prlogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.emailtext);
        pass = (EditText) findViewById(R.id.passtext);
        stlogin = (Button) findViewById(R.id.LoginAsStudent);
        prlogin = (Button) findViewById(R.id.LoginAsProctor);
        register = (TextView) findViewById(R.id.tvRegister);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(LoginStudent.this, StudentsPage.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginStudent.this, MainActivity.class));

            }
        });


        stlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startValidateS();
                stlogin.setBackground(getResources().getDrawable(R.drawable.colorr));
            }
        });


    }

    public void startValidateS()
    {
        final String emailstring = email.getText().toString();
        final String passstring = pass.getText().toString();
        Query q = FirebaseDatabase.getInstance().getReference("Student").orderByChild("Email").equalTo(emailstring);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    mAuth.signInWithEmailAndPassword(emailstring,passstring).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(LoginStudent.this,StudentsPage.class));
                                Toast.makeText(LoginStudent.this, "Login success",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(LoginStudent.this, "Login failed",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginStudent.this, "Login as a Student",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
