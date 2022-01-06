package com.example.wallpaperapplication.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;

public class RegisterActivity extends AppCompatActivity {

    TextView terms;
    TextView login;
    EditText username;
    EditText email;
    EditText password;
    EditText r_password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String text = "By Signing up, you agree with our Terms & Conditions";

        terms = findViewById(R.id.tv_terms);
        login = findViewById(R.id.login_fragment);
        username = findViewById(R.id.et_username_register);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password_register);
        r_password = findViewById(R.id.et_repeat_password);
        btnRegister = findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = username.getText().toString();
                String _email = email.getText().toString();
                String _pass = password.getText().toString();

                if(username.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Name can't be empty", Toast.LENGTH_LONG).show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Email can't be empty", Toast.LENGTH_LONG).show();
                }
                else if(!password.getText().toString().equals(r_password.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Password Not Match", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //masih dummy
                    SharedPreference sharedPreference = new SharedPreference(RegisterActivity.this);
                    sharedPreference.save(new User(_name, _email, _pass));

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                finish();
            }
        });

    }
}