package com.ikheiry.sqliteapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ikheiry.sqliteapp.R;
import com.ikheiry.sqliteapp.db.DataBaseManager;
import com.ikheiry.sqliteapp.model.Employee;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    private Context context;
    private List<Employee> employees;
    private  DataBaseManager mdatabase;

    public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employees, DataBaseManager mdatabase) {
        super(context, resource, employees);
        this.context = context;
        this.employees = employees;
        this.mdatabase = mdatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.employees_list_layout, null);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSal = view.findViewById(R.id.textViewSalary);
        TextView textViewDate = view.findViewById(R.id.textViewJoiningDate);

        final Employee employee = employees.get(position);

        textViewName.setText(employee.getName());
        textViewDept.setText(employee.getDept());
        textViewSal.setText(String.valueOf(employee.getSalary()));
        textViewDate.setText(employee.getDate());

        // Events
        view.findViewById(R.id.buttonEditEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(employee);
            }
        });

        view.findViewById(R.id.buttonDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employee);
            }
        });

        return view;
    }

    private void deleteEmployee(final Employee employee) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setTitle("Are you sure?");

        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mdatabase.deleteEmployee(employee.getId());
                ReloadEmployeesFromDataBase();
            }
        });

        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = aBuilder.create();
        dialog.show();
    }

    private void updateEmployee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editTextName = view.findViewById(R.id.name);
        final EditText editTextSal = view.findViewById(R.id.editTextSalary);
        final Spinner spinner = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(employee.getName());
        editTextSal.setText(String.valueOf(employee.getSalary()));

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String salary = editTextSal.getText().toString().trim();
                String departement = spinner.getSelectedItem().toString().trim();

                if(name.isEmpty()){
                    editTextName.setError("Name can't be empty");
                    editTextName.requestFocus();
                    return;
                }

                if(salary.isEmpty()){
                    editTextSal.setError("Salary can't be empty");
                    editTextSal.requestFocus();
                    return;
                }

                if(mdatabase.updateEmployee(employee.getId(),name, departement, Double.parseDouble(salary)))
                    Toast.makeText(context, "Employee updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "Employee not updated", Toast.LENGTH_LONG).show();

                ReloadEmployeesFromDataBase();
                dialog.dismiss();
            }
        });
    }

    private void ReloadEmployeesFromDataBase() {
        Cursor cursor = mdatabase.getAllEmployess();

        if(cursor.moveToFirst()) {
            employees.clear();
            do {
                employees.add(new Employee(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // dept
                        cursor.getString(3), // date
                        cursor.getDouble(4) // salary
                ));
            } while (cursor.moveToNext());
            notifyDataSetChanged();
        }
    }
}
