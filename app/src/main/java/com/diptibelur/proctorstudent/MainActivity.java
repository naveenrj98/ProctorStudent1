package com.diptibelur.proctorstudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.FirebaseAppHelper;

public class MainActivity extends AppCompatActivity {
    private Button reg;
    TextView userl;
    private DatabaseReference dr;
    EditText name,usn,email,pass,repass,prname;
    Spinner branch,sem;
    String[] branch1 = {"CSE","ISE","ECE"};
    String[] sem1 = {"2","4","6","8"};
    ArrayAdapter adapter,adapter1;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
       dr = FirebaseDatabase.getInstance().getReference();
       name=(EditText)findViewById(R.id.name);
        usn=(EditText)findViewById(R.id.usn);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        repass=(EditText)findViewById(R.id.repass);
        branch=(Spinner)findViewById(R.id.branch);
        sem=(Spinner)findViewById(R.id.sem);
        userl = (TextView)findViewById(R.id.userLogin);
        prname =(EditText)findViewById(R.id.proctornametext);
        adapter = new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,branch1);
        branch.setAdapter(adapter);
        adapter1 = new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,sem1);
        sem.setAdapter(adapter1);


        reg=(Button)findViewById(R.id.Register);

        userl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginPage.class));
            }
        });

       reg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String n,u,e,p,rp,br,s,pr,pr_sem;
               n=name.getText().toString();
               u=usn.getText().toString();
               br=branch.getSelectedItem().toString();
               s=sem.getSelectedItem().toString();
               pr=prname.getText().toString();
               e=email.getText().toString();
               p=pass.getText().toString();
               rp=repass.getText().toString();
               pr_sem = pr+"_"+s;

               Student st = new Student(n,u,br,s,e,p,rp,pr_sem);
               DatabaseReference studentRef = dr.child("Student");
               studentRef.child(st.Name).setValue(st);
              // Toast.makeText(MainActivity.this,"Successfully Registered!",Toast.LENGTH_LONG).show();

               firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                           startActivity(new Intent(MainActivity.this,LoginPage.class));
                       }
                       else
                       {
                           Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
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
