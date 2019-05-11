package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProcotorRegistrationPage extends AppCompatActivity {

    EditText rEmail, rPass, rName, rBranch;
    Button btnrSubmit;

    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        rEmail = findViewById(R.id.remail);
        rPass = findViewById(R.id.rpass);
        rName = findViewById(R.id.rname);
        rBranch = findViewById(R.id.rbranch);

        btnrSubmit = findViewById(R.id.rsubmit);

        firebaseAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();


        btnrSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branch,pass,email,name;
                branch=rBranch.getText().toString();
                pass=rPass.getText().toString();

                email=rEmail.getText().toString();
                name=rName.getText().toString();


                Proctor pt = new Proctor(branch, email, name, pass);
             //   Student st = new Student(n,u,br,s,e,p,rp,pr_sem);
                DatabaseReference proctoreRef = dr.child("Proctor");
                proctoreRef.child(pt.name).setValue(pt);
                // Toast.makeText(MainActivity.this,"Successfully Registered!",Toast.LENGTH_LONG).show();

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProcotorRegistrationPage.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ProcotorRegistrationPage.this,LoginProctor.class));
                        }
                        else
                        {
                            Toast.makeText(ProcotorRegistrationPage.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
