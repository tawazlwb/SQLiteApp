package com.ikheiry.sqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ikheiry.sqliteapp.adapter.EmployeeAdapter;
import com.ikheiry.sqliteapp.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private SQLiteDatabase mdatabase;
    private List<Employee> employees;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        listView = findViewById(R.id.employees_list);
        employees = new ArrayList<>();

        // create or open database
        mdatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME_NAME, MODE_PRIVATE, null);

        loadEmployeesFromDatabase();
    }

    private void loadEmployeesFromDatabase(){
        String sql = "SELECT * FROM employees;";
        Cursor cursor = mdatabase.rawQuery(sql, null);

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
