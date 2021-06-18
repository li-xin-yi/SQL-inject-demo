package com.example.sql_inject_demo;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.appcompat.app.AlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBHandler extends SQLiteOpenHelper {
    static final int DB_VERSION = 1;
    static final String DB_NAME = "employeeDB.db";
    static final String TABLE_NAME = "employee";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( ID INTEGER,"
                + "NAME TEXT,"
                + "PASSWORD TEXT,"
                + "SSN TEXT,"
                + "NICKNAME TEXT,"
                + "PHONE TEXT,"
                + "EMAIL TEXT,"
                + "ADDRESS TEXT,"
                + "SALARY INTEGER,"
                + "BIRTHDAY DATE, PRIMARY KEY(ID, NAME))";
        db.execSQL(SQL_CREATE_TABLE);
        addHandler(db,new Employee(99999, "Admin", "admin", "43254314",
                "Ad", "(403)220-1191", "admin@hogwarts.edu", "Gryffindor House", 400000, "1990-03-05"));
        addHandler(db, new Employee(10000, "Alice", "alice", "10211002",
                "Ali", "(400)210-2112", "alice@hogwarts.edu", "Gryffindor House", 20000, "2000-09-20"));
        addHandler(db, new Employee(20000, "Boby", "boby", "10213352",
                "Bob", "(404)789-2313", "boby@hogwarts.edu", "Hufflepuff House", 50000, "2000-04-20"));
        addHandler(db, new Employee(30000, "Ryan", "ryan", "32193525",
                "Ryanny", "(210)096-3287", "ryan@hogwarts.edu", "Ravenclaw House", 90000, "2000-04-10"));
        addHandler(db, new Employee(40000, "Samy", "samy", "32111111",
                "Sam", "(450)218-8876", "samy@hogwarts.edu", "Slytherin House", 40000, "2000-01-11"));
        addHandler(db, new Employee(50000, "Ted", "ted", "24343244",
                "Teddy", "(208)222-8712", "ted@hogwarts.edu", "Azkaban", 110000, "2000-11-3"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor loadHandler() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void addHandler(SQLiteDatabase db, Employee employee) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        if (db != null) {
            values.put("ID", employee.getId());
            values.put("NAME", employee.getName());
            values.put("PASSWORD", employee.getPassword());
            values.put("SSN", employee.getSsn());
            values.put("NICKNAME", employee.getNickname());
            values.put("PHONE", employee.getPhone());
            values.put("SALARY", employee.getSalary());
            values.put("ADDRESS", employee.getAddress());
            values.put("EMAIL", employee.getEmail());
            values.put("BIRTHDAY", dateFormat.format(employee.getBirthday()));
            db.insert(TABLE_NAME, null, values);
        }
    }

    public void addInterface(Employee employee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        addHandler(db,employee);
    }

    public Employee findHandler(String username, String password, Boolean safe) {
        String query;
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        if(!safe) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE NAME='" + username + "' AND PASSWORD='" + password + "'";
            cursor = db.rawQuery(query, null);
        }
        else
        {
            query = "SELECT * FROM "+ TABLE_NAME + " WHERE NAME=? AND PASSWORD=?";
            cursor = db.rawQuery(query, new String[]{username,password});
        }
        Employee employee = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            employee = new Employee(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Integer.parseInt(cursor.getString(8)),
                    cursor.getString(9)
            );
            cursor.close();
        }
        db.close();
        return employee;
    }

    public void partialUpdateHandler(Employee employee) {
        String UPDATE_SQL_COMMAND = String.format("UPDATE %s SET NICKNAME='%s', EMAIL='%s', ADDRESS='%s', PASSWORD='%s', PHONE='%s' WHERE ID=%s",
                TABLE_NAME,
                employee.getNickname(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPassword(),
                employee.getPhone(),
                employee.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(UPDATE_SQL_COMMAND);
    }

    public boolean fullUpdateHandler(Employee employee)
    {
//        invoked by admin, update all fields except ID
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", employee.getName());
        values.put("PASSWORD", employee.getPassword());
        values.put("SSN", employee.getSsn());
        values.put("NICKNAME", employee.getNickname());
        values.put("PHONE", employee.getPhone());
        values.put("SALARY", employee.getSalary());
        values.put("ADDRESS", employee.getAddress());
        values.put("EMAIL", employee.getEmail());
        values.put("BIRTHDAY", dateFormat.format(employee.getBirthday()));
        return -1!=db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(employee.getId())});
    }

    public boolean deleteHandler(Employee employee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(employee.getId())}) > 0;
    }

    public boolean safePartialUpdateHandler(Employee employee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASSWORD", employee.getPassword());
        values.put("NICKNAME", employee.getNickname());
        values.put("PHONE", employee.getPhone());
        values.put("ADDRESS", employee.getAddress());
        values.put("EMAIL", employee.getEmail());
        return -1!=db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(employee.getId())});
    }

}