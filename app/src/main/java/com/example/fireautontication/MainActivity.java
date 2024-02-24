package com.example.fireautontication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebase;
    private Button login;
    private EditText email;
    private EditText password;
    private TextView goToRegister;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebase=FirebaseAuth.getInstance();

        //id
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        goToRegister=findViewById(R.id.gotToRegistor);

        //initilization
        progressBar.setVisibility(View.GONE);

        //listener
        login.setOnClickListener(this);
        goToRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login){
            loginUser();
        }else if(view.getId()==R.id.gotToRegistor){
            Intent i=new Intent(getApplicationContext(),register.class);
            startActivity(i);
        }
    }

    private void loginUser() {
        //check if fields are empty
        if(email.getText().toString().trim()==""||password.getText().toString().trim()==""){
            Toast.makeText(getApplicationContext(),"Email Or Password is not filled.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebase.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebase.getCurrentUser();
                            Intent i=new Intent(getApplicationContext(), dashboard.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }
}
