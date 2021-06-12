package com.example.sql_inject_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    DBHandler dbhandler;
    EditText usernameInput;
    EditText passwordInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        if (ab!=null)
        {
            ab.setTitle("Welcome");
        }
        dbhandler = new DBHandler(this,null,null,1);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        };

        public void signOnClick(View view)
        {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if(username.equals("") || password.equals(""))
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Please fill in username and password!");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                      //  dialog.cancel();
                    }
                });
                dialog.show();
            } else {
                Employee employee = dbhandler.findHandler(username,password);
                if (employee == null)
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage("Incorrect username or password!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                          //  dialog.cancel();
                        }
                    });
                    dialog.show();
                }
                else{
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Intent intent = new Intent(MainActivity.this, Result.class);
                intent.putExtra("ID",employee.getId());
                intent.putExtra("NAME",employee.getName());
                intent.putExtra("NICKNAME",employee.getNickname());
                intent.putExtra("SALARY",employee.getSalary());
                intent.putExtra("PHONE",employee.getPhone());
                intent.putExtra("EMAIL",employee.getEmail());
                intent.putExtra("BIRTHDAY",dateFormat.format(employee.getBirthday()));
                intent.putExtra("PASSWORD",employee.getPassword());
                intent.putExtra("ADDRESS",employee.getAddress());
                startActivity(intent);}
            }


        }


    }