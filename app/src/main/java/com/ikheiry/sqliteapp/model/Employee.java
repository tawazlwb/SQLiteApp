package com.ikheiry.sqliteapp.model;

public class Employee {
    private int id;
    private String name;
    private String dept;
    private String date;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, String dept, String date, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.date = date;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
