package com.example.sql_inject_demo;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "employeeDB.db";
    private static final String TABLE_NAME = "employee";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "( ID INTEGER,"
                + "NAME TEXT,"
                + "PASSWORD TEXT,"
                + "SSN TEXT,"
                + "NICKNAME TEXT,"
                + "PHONE TEXT,"
                + "EMAIL TEXT,"
                + "ADDRESS TEXT,"
                + "SALARY INTEGER,"
                + "BIRTHDAY DATE)";
        db.execSQL(SQL_CREATE_TABLE);
        addHandler(db, new Employee(99999,"Admin","seedadmin","43254314",
                "","","","",400000,"1990-03-05"));
        addHandler(db, new Employee(10000,"Alice","seedalice","10211002",
                "","","","",20000,"2000-09-20"));
        addHandler(db, new Employee(20000,"Boby","seedboby","10213352",
                "","","","",50000,"2000-04-20"));
        addHandler(db, new Employee(30000,"Ryan","seedryan","32193525",
                "","","","",90000,"2000-04-10"));
        addHandler(db, new Employee(30000,"Samy","seedsamy","32111111",
                "","","","",40000,"2000-01-11"));
        addHandler(db, new Employee(40000,"Ted","seedted","24343244",
                "","","","",110000,"2000-11-3"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public Cursor loadHandler()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
            db.close();
        }

        return cursor;
    }

    public void addHandler(SQLiteDatabase db, Employee employee)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        if (db!=null)
        {
            values.put("ID", employee.getId());
            values.put("NAME", employee.getName());
            values.put("PASSWORD", employee.getPassword());
            values.put("SSN", employee.getSsn());
            values.put("NICKNAME", employee.getNickname());
            values.put("PHONE", employee.getPhone());
            values.put("SALARY", employee.getSalary());
            values.put("ADDRESS", employee.getAddress());
            values.put("EMAIL", employee.getSalary());
            values.put("BIRTHDAY", dateFormat.format(employee.getBirthday()));
            db.insert(TABLE_NAME, null, values);
        }
    }
    public Employee findHandler(String username, String password)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE NAME='" + username + "' AND PASSWORD='" + password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Employee employee= null;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor!=null && cursor.getCount()>0 && cursor.moveToFirst()) {
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

    public boolean updateHandler(Employee employee)
    {
        String UPDATE_SQL_COMMAND = String.format("UPDATE %s SET NICKNAME=%s, EMAIL=%s, ADDRESS=%s, PASSWORD=%s, PHONE=%s WHERE ID=%s RETURNING *",
                TABLE_NAME,
                employee.getNickname(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPassword(),
                employee.getPhone(),
                employee.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(UPDATE_SQL_COMMAND,null);
        cursor.close();
        return cursor.getCount()==1;
    }

}