package com.winnicki.firebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    Button buttonRegister;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is empty!", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()) {
                            Toast.makeText(MainActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "User Failed registered!", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }
}