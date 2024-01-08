package com.minhhieu.chatbotapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.minhhieu.chatbotapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText edt_email, edt_password, edt_confirm_password;
    private Button btn_sign_up;
    private TextView txt_sign_up;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();
        initListener();
    }
    private void initListener() {
        btn_sign_up.setOnClickListener(v -> onClickSignUp());
        txt_sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    private void onClickSignUp() {
        String strEmail = edt_email.getText().toString().trim();
        String strPassword = edt_password.getText().toString().trim();
        String strConfirmPassword = edt_confirm_password.getText().toString().trim();

        if (strEmail.isEmpty() && strPassword.isEmpty() && strConfirmPassword.isEmpty()){
            Toast.makeText(this, "Email and Password cannot be empty ", Toast.LENGTH_SHORT).show();
        }else if (!strPassword.equals(strConfirmPassword)){
            Toast.makeText(this, "Password must be the same ", Toast.LENGTH_SHORT).show();
        }else if (strEmail.isEmpty()) {
            Toast.makeText(this, "Please enter your Email ", Toast.LENGTH_SHORT).show();
        } else if (strPassword.isEmpty()) {
            Toast.makeText(this, "Please enter your Password ", Toast.LENGTH_SHORT).show();
        }  else if (strConfirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter your Password ", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success,
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails,
                            Toast.makeText(SignUpActivity.this, "Email already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initUi() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        txt_sign_up = findViewById(R.id.txt_sign_up);
        progressDialog = new ProgressDialog(this);
    }
}