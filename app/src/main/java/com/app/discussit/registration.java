package com.app.discussit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {
    private EditText emailTV;
    private FirebaseAuth mAuth;
    private EditText name;
    private EditText passwordTV;
    ProgressDialog progressDialog;
    private Button regBtn;
    Button sign;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_registration);
        this.progressDialog = new ProgressDialog(this);
        FirebaseDatabase.getInstance().getReference("message").setValue("Hello, World!");
        this.mAuth = FirebaseAuth.getInstance();
        initializeUI();
        this.regBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                registration.this.progressDialog.setMessage("Registering...");
                registration.this.progressDialog.show();
                registration.this.registerNewUser();
            }
        });
        this.sign.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                registration.this.startActivity(new Intent(registration.this, login.class));
            }
        });
    }

    /* access modifiers changed from: private */
    public void registerNewUser() {
        final String obj = this.name.getText().toString();
        String obj2 = this.emailTV.getText().toString();
        String obj3 = this.passwordTV.getText().toString();
        if (TextUtils.isEmpty(obj2)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(obj3)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
        } else {
            this.mAuth.createUserWithEmailAndPassword(obj2, obj3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (registration.this.progressDialog.isShowing()) {
                            registration.this.progressDialog.dismiss();
                        }
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        currentUser.updateProfile(new Builder().setDisplayName(obj).build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                task.isSuccessful();
                            }
                        });
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(currentUser);
                        Toast.makeText(registration.this.getApplicationContext(), "Registration successful!",Toast.LENGTH_LONG ).show();
                        registration.this.startActivity(new Intent(registration.this, login.class));
                        return;
                    }
                    if (registration.this.progressDialog.isShowing()) {
                        registration.this.progressDialog.dismiss();
                    }
                    Toast.makeText(registration.this.getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initializeUI() {
        this.emailTV = (EditText) findViewById(R.id.editText5);
        this.passwordTV = (EditText) findViewById(R.id.editText6);
        this.regBtn = (Button) findViewById(R.id.button);
        this.sign = (Button) findViewById(R.id.button3);
        this.name = (EditText) findViewById(R.id.editText4);
    }
}
