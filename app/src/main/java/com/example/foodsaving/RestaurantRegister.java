package com.example.foodsaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodsaving.ui.login.CustomerLoginActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RestaurantRegister extends AppCompatActivity {

    EditText textInputEditTextUsername,textInputEditTextPhone,  textInputEditTextEmail, textInputEditTextPassword, textInputEditTextCompanyAdress;
    Button signUpButton;
    TextView textViewLogin;

    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);

        textInputEditTextUsername = findViewById(R.id.layoutNameCompany);
        textInputEditTextPhone = findViewById(R.id.layoutPhone);
        textInputEditTextCompanyAdress = findViewById(R.id.adressCompany);
        textInputEditTextEmail = findViewById(R.id.layoutEmail);
        textInputEditTextPassword = findViewById(R.id.layoutPassword);

        signUpButton = findViewById(R.id.signUpbut);
        textViewLogin = findViewById(R.id.loginText);
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
                final String company_name, company_phone, company_email, company_password, company_address;
                company_name = String.valueOf(textInputEditTextUsername.getText());
                company_phone = String.valueOf(textInputEditTextPhone.getText());
                company_email = String.valueOf(textInputEditTextEmail.getText());
                company_password = String.valueOf(textInputEditTextPassword.getText());
                company_address = String.valueOf(textInputEditTextCompanyAdress.getText());

                if (!company_name.equals("") && !company_phone.equals("") && !company_email.equals("") && !company_password.equals("") && !company_address.equals("")) {
                    loading.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "company_name";
                            field[1] = "company_phone";
                            field[2] = "company_email";
                            field[3] = "company_password";
                            field[4] = "company_address";
                            String[] data = new String[5];
                            data[0] = company_name;
                            data[1] = company_phone;
                            data[2] = company_email;
                            data[3] = company_password;
                            data[4] = company_address;
                            PutData putData = new PutData("http://10.101.64.153/rest_login_register/signup_restaurant.php", "POST", field, data);
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