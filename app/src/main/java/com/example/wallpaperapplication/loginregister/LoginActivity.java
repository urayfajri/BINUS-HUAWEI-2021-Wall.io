package com.example.wallpaperapplication.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    TextView terms;
    TextView forgot;
    EditText email;
    EditText pass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.sign_up_fragment);
        terms = findViewById(R.id.tv_terms);
        forgot = findViewById(R.id.tv_forgot);
        email = findViewById(R.id.et_email_login);
        pass = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _pass = pass.getText().toString();

                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Email and Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(pass.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //masih dummy
                    SharedPreference sharedPreference = new SharedPreference(LoginActivity.this);
                    sharedPreference.save(new User("User Dummy", _email, _pass));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}