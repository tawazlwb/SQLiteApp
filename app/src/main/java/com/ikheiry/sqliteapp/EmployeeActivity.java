package com.ikheiry.sqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ikheiry.sqliteapp.adapter.EmployeeAdapter;
import com.ikheiry.sqliteapp.db.DataBaseManager;
import com.ikheiry.sqliteapp.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private DataBaseManager mdatabase;
    private List<Employee> employees;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        listView = findViewById(R.id.employees_list);
        employees = new ArrayList<>();

        // create database manager instance
        mdatabase = new DataBaseManager(this);

        loadEmployeesFromDatabase();
    }

    private void loadEmployeesFromDatabase(){
        Cursor cursor = mdatabase.getAllEmployess();

        if(cursor.moveToFirst()){
            do {
                employees.add(new Employee(
                    cursor.getInt(0), // id
                    cursor.getString(1), // name
                    cursor.getString(2), // dept
                    cursor.getString(3), // date
                    cursor.getDouble(4) // salary
                ));
            }while (cursor.moveToNext());

            EmployeeAdapter adapter = new EmployeeAdapter(this, R.layout.employees_list_layout, employees, mdatabase);
            listView.setAdapter(adapter);
        }
    }
}
