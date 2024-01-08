package com.minhhieu.chatbotapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.minhhieu.chatbotapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private EditText edt_email, edt_password;
    private Button btn_sign_in;
    private TextView txt_sign_up, txt_forgot_password;
    private CheckBox ck_remember_pass;
    private ProgressDialog progressDialog;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUi();
        initListener();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        edt_email.setText(sharedPreferences.getString("email", ""));
        edt_password.setText(sharedPreferences.getString("password", ""));
        ck_remember_pass.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    private void initListener() {
        btn_sign_in.setOnClickListener(v -> onClickSignIn());
        txt_sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        txt_forgot_password.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void onClickSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String strEmail = edt_email.getText().toString().trim();
        String strPassword = edt_password.getText().toString().trim();
        if (strEmail.isEmpty() && strPassword.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty ", Toast.LENGTH_SHORT).show();
        } else if (strEmail.isEmpty()) {
            Toast.makeText(this, "Please enter your Email ", Toast.LENGTH_SHORT).show();
        } else if (strPassword.isEmpty()) {
            Toast.makeText(this, "Please enter your Password ", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            if (ck_remember_pass.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", strEmail);
                                editor.putString("password", strPassword);
                                editor.putBoolean("checked", true);
                                editor.apply();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.remove("password");
                                editor.remove("checked");
                                editor.apply();
                            }
                            // Sign in success
                            Toast.makeText(SignInActivity.this, "Login Success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails

                            Toast.makeText(SignInActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void initUi() {
        btn_sign_in = findViewById(R.id.btn_login);
        ck_remember_pass = findViewById(R.id.ck_remember_password);
        txt_sign_up = findViewById(R.id.txt_sign_up);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);


        progressDialog = new ProgressDialog(this);
    }
}