package com.example.sql_inject_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Result extends AppCompatActivity {


//    EditText idInput;
    EditText idInput,salaryInput, nicknameInput, addressInput, emailInput, phoneInput;
    EditText birthdayInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar ab = getSupportActionBar();

        if (ab!=null)
        {
            ab.setTitle(getIntent().getExtras().getString("NAME"));
        }
        idInput = findViewById(R.id.idInput);
        salaryInput = findViewById(R.id.salaryInput);
        nicknameInput = findViewById(R.id.nicknameInput);
        addressInput = findViewById(R.id.addressInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);

        nicknameInput.setText(getIntent().getStringExtra("NICKNAME"));
        addressInput.setText(getIntent().getStringExtra("ADDRESS"));
        emailInput.setText(getIntent().getStringExtra("EMAIL"));
        phoneInput.setText(getIntent().getStringExtra("PHONE"));
        idInput.setText(String.valueOf(getIntent().getIntExtra("ID",0)));
        salaryInput.setText(String.valueOf(getIntent().getIntExtra("SALARY",0)));
//        birthdayInput.setText(getIntent().getStringExtra("BIRTHDAY"));
    }
}