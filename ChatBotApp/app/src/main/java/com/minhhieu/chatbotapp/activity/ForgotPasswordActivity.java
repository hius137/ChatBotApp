package com.minhhieu.chatbotapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.minhhieu.chatbotapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edt_email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button btn_send_email = findViewById(R.id.btn_send_email);
        edt_email = findViewById(R.id.edt_email);
        progressDialog = new ProgressDialog(this);

        btn_send_email.setOnClickListener(v -> {
            if (edt_email == null){
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }else {
                onClickForgotPassword();
            }
        });
    }
    private void onClickForgotPassword() {
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String strEmail = edt_email.getText().toString().trim();

        auth.sendPasswordResetEmail(strEmail)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Please Check Your Email.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}