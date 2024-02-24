package com.example.fireautontication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class dashboard extends AppCompatActivity implements View.OnClickListener {

    private TextView email,id;
    private Button delete;

    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth=FirebaseAuth.getInstance();

        //id
        email=findViewById(R.id.userEmail);
        id=findViewById(R.id.userId);
        delete=findViewById(R.id.delete);
        progressBar=findViewById(R.id.progressBar3);

        //Inisilizaiton
        progressBar.setVisibility(View.GONE);

        //listener
        delete.setOnClickListener(this);

        //set text
        email.setText(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
        id.setText(firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==R.id.delete){
            deleteUser();
        }
    }

    private void deleteUser() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            progressBar.setVisibility(View.VISIBLE);
            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Could not delete user",Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"User not logged in",Toast.LENGTH_SHORT).show();
        }
    }
}