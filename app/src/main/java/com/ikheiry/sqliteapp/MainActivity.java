package com.ikheiry.sqliteapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME_NAME = "mydb";

    private SQLiteDatabase mdatabase;

    private EditText name, salary;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create or open database
        mdatabase = openOrCreateDatabase(DATABASE_NAME_NAME, MODE_PRIVATE, null);

        // create table if not exists
        createTable();

        name = findViewById(R.id.name);
        salary = findViewById(R.id.editTextSalary);
        spinner = findViewById(R.id.spinnerDepartment);

    }

    private void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS employees (\n" +
                "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    name varchar(200) NOT NULL,\n" +
                "    department varchar(200) NOT NULL,\n" +
                "    joiningdate datetime NOT NULL,\n" +
                "    salary double NOT NULL\n" +
                ");";
        mdatabase.execSQL(sql);
    }

    public void addEmployee(View view) {
        String nameVal = name.getText().toString();
        String salaryVal = salary.getText().toString();
        String dept = spinner.getSelectedItem().toString();

        if(nameVal.isEmpty()){
            name.setError("Name can't be empty");
            name.requestFocus();
            return;
        }

        if(salaryVal.isEmpty()){
            salary.setError("Salary can't be empty");
            salary.requestFocus();
            return;
        }

        // request query
        String sql = "INSERT INTO employees \n" +
                "(name, department, joiningdate, salary)\n" +
                "VALUES \n" +
                "(?, ?, ?, ?);";

        // date format
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String date = sdf.format(calendar.getTime());

        // query execution
        mdatabase.execSQL(sql, new String[]{nameVal, dept, date, salaryVal});

        // diplay Toast
        Toast.makeText(this, "Employee added", Toast.LENGTH_LONG).show();
    }

    public void showEmployees(View view) {
        Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
        startActivity(intent);
    }
}
