package com.example.sql_inject_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllEmployee extends AppCompatActivity {

    DBHandler dbHandler;
    ArrayList<Employee> employees;
    RecyclerView recyclerView;
    TextView noDataText;
    CustomAdapter customAdapter;
    FloatingActionButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_employee);

        ActionBar ab = getSupportActionBar();
        if (ab!=null)
        {
            ab.setTitle("All Employees");
        }

        dbHandler = new DBHandler(AllEmployee.this,null,null,1);
        recyclerView = findViewById(R.id.recycleViewer);
        noDataText = findViewById(R.id.noDataText);
        returnButton = findViewById(R.id.returnButton);
        employees = new ArrayList<>();
        storeEmployees();
        customAdapter = new CustomAdapter(this,this,employees);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllEmployee.this));
    }

    protected void storeEmployees()
    {
        Cursor cursor = dbHandler.loadHandler();
        if (cursor.getCount()==0)
        {
            noDataText.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()){
                employees.add(new Employee(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        Integer.parseInt(cursor.getString(8)),
                        cursor.getString(9)
                ));
            }
        }
    }

    public void returnOnClick(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void addOnClick(View v)
    {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

}