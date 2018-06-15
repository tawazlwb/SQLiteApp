package com.ikheiry.sqliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikheiry.sqliteapp.db.DataBaseManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DataBaseManager mdatabase;

    private EditText name, salary;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create database manager instance
        mdatabase = new DataBaseManager(this);

        name = findViewById(R.id.name);
        salary = findViewById(R.id.editTextSalary);
        spinner = findViewById(R.id.spinnerDepartment);

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

        // date format
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.FRANCE);
        String date = sdf.format(calendar.getTime());

        if(mdatabase.addEmployee(nameVal, dept, date, Double.parseDouble(salaryVal)))
            Toast.makeText(this, "Employee added", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Employee not added", Toast.LENGTH_LONG).show();
    }

    public void showEmployees(View view) {
        Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
        startActivity(intent);
    }
}
