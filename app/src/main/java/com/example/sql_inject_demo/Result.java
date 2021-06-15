package com.example.sql_inject_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result extends AppCompatActivity {


//    EditText idInput;
    EditText idInput,salaryInput, nicknameInput, addressInput, emailInput, phoneInput,birthdayInput,nameInput,ssnInput,passwordInput;
    Employee employee;
    DBHandler dbHandler;
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Button updateBotton, signoffBotton;
    boolean admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar ab = getSupportActionBar();


        if (ab!=null)
        {
            ab.setTitle("Profile");
        }
        idInput = findViewById(R.id.idInput);
        nameInput = findViewById(R.id.nameInput);
        salaryInput = findViewById(R.id.salaryInput);
        ssnInput = findViewById(R.id.ssnInput);
        passwordInput = findViewById(R.id.passwordInput);
        nicknameInput = findViewById(R.id.nicknameInput);
        addressInput = findViewById(R.id.addressInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        birthdayInput = findViewById(R.id.birthdayInput);

        updateBotton = findViewById(R.id.updateButton);
        signoffBotton = findViewById(R.id.signoffButton);

        dbHandler = new DBHandler(this,null,null,1);




        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthdayInput.setText(dateFormat.format(myCalendar.getTime()));
        };

        birthdayInput.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(Result.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        employee = (Employee) getIntent().getSerializableExtra("employee");
        admin = getIntent().getBooleanExtra("admin",false);



        nameInput.setText(employee.getName());
        idInput.setText(String.valueOf(employee.getId()));
        addressInput.setText(employee.getAddress());
        emailInput.setText(employee.getEmail());
        phoneInput.setText(employee.getPhone());
        salaryInput.setText(String.valueOf(employee.getSalary()));
        birthdayInput.setText(dateFormat.format(employee.getBirthday()));
        ssnInput.setText(employee.getSsn());
        passwordInput.setText(employee.getPassword());
        nicknameInput.setText(employee.getNickname());

        idInput.setEnabled(false);

        if(admin)
        {
            signoffBotton.setText("RETURN");
            signoffBotton.setOnClickListener(v -> {
                Intent intent = new Intent(this,AllEmployee.class);
                startActivity(intent);});
            updateBotton.setOnClickListener(this::fullUpdateProfile);
        }
        else
        {
            salaryInput.setEnabled(false);
            ssnInput.setEnabled(false);
            nameInput.setEnabled(false);
            birthdayInput.setEnabled(false);
            signoffBotton.setOnClickListener(v -> finish());
            updateBotton.setOnClickListener(this::partialUpdateProfile);
        }
    }

    public void partialUpdateProfile(View v)
    {
        employee.setNickname(nicknameInput.getText().toString());
        employee.setAddress(addressInput.getText().toString());
        employee.setPassword(passwordInput.getText().toString());
        employee.setPhone(phoneInput.getText().toString());
        employee.setEmail(emailInput.getText().toString());

        try {
            dbHandler.partialUpdateHandler(employee);
            messageBox("Update Successfully!");
        } catch (Exception e) {
            messageBox(e.getMessage());
        }
    }

    public void fullUpdateProfile(View v)
    {
        employee.setNickname(nicknameInput.getText().toString());
        employee.setAddress(addressInput.getText().toString());
        employee.setPassword(passwordInput.getText().toString());
        employee.setPhone(phoneInput.getText().toString());
        employee.setEmail(emailInput.getText().toString());
        employee.setName(nameInput.getText().toString());
        employee.setSsn(ssnInput.getText().toString());
        employee.setSalary(Integer.parseInt(salaryInput.getText().toString().trim()));
        try {employee.setBirthday(dateFormat.parse(birthdayInput.getText().toString()));}
        catch (Exception e) {
            messageBox(e.getMessage());
        }
        if(dbHandler.fullUpdateHandler(employee))
        {
            messageBox("Update Successfully!");
        }
        else
        {
            messageBox("Update Fail!");
        }

    }

    public void messageBox(String title)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(title);
        dialog.setPositiveButton("OK", (dialogInterface, i) -> {});
        dialog.show();
    }

}