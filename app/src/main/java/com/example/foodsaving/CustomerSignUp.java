package com.example.foodsaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodsaving.ui.login.CustomerLoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class CustomerSignUp extends AppCompatActivity {

    EditText textInputEditTextUsername, textInputEditTextEmail, textInputEditTextPassword;
    Button signUpButton;
    TextView textViewLogin;

    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        textInputEditTextUsername = findViewById(R.id.layoutName);
        textInputEditTextEmail = findViewById(R.id.layoutEmail);
        textInputEditTextPassword = findViewById(R.id.layoutPassword);

        signUpButton = findViewById(R.id.signUpbut);
        textViewLogin = findViewById(R.id.signUpText);
        loading = findViewById(R.id.loading);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username, email, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!email.equals("") && !username.equals("") && !password.equals("")) {
                    loading.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "email";
                            field[1] = "username";
                            field[2] = "password";
                            String[] data = new String[3];
                            data[0] = email;
                            data[1] = username;
                            data[2] = password;
                            PutData putData = new PutData("http://10.101.64.153/android_register_login/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    loading.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), CustomerLoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}