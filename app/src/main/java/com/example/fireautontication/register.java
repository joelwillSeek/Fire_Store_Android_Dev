package com.example.fireautontication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

   // private ProgressBar progressBar;
    private Button signUp;
    private EditText email,password;
    private EditText confirmPassword;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();

        //id
       // progressBar=findViewById(R.id.progressBar2);
        confirmPassword=findViewById(R.id.signconfirmpassword);
        password=findViewById(R.id.signpassword);
        signUp=findViewById(R.id.signlogin);
        email=findViewById(R.id.signemail);

        //initialization
     //   progressBar.setVisibility(View.GONE);

        //listener

        signUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.signlogin){
            signUpHelper();
        }
    }

    private void signUpHelper() {
        String emailString=email.getText().toString().trim();
        String passwordString=password.getText().toString().trim();
       // String confirmPasswordString=confirmPassword.getText().toString().trim();
        if(emailString.equals("") || passwordString.equals("") ){//|| confirmPasswordString.equals("")){
            Toast.makeText(getApplicationContext(), "Fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

//        if(!passwordString.equals(confirmPasswordString)){
//            Toast.makeText(getApplicationContext(), "Password doesn't match",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }

      //  progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //goto dashboard\
                            Intent i=new Intent(getApplicationContext(), dashboard.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                      //  progressBar.setVisibility(View.GONE);
                    }
                });
    }
}