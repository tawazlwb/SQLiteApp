package com.ikheiry.sqliteapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOIN_DATE = "joiningdate";
    private static final String COLUMN_SALARY = "salary";

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                COLUMN_DEPT + " varchar(200) NOT NULL,\n" +
                COLUMN_JOIN_DATE + " datetime NOT NULL,\n" +
                COLUMN_SALARY + " double NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addEmployee(String name, String dept, String date, double salary) {
        SQLiteDatabase mdatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, dept);
        contentValues.put(COLUMN_JOIN_DATE, date);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        return mdatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor getAllEmployess() {
        SQLiteDatabase mdatabase = getReadableDatabase();
        return mdatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateEmployee(int id, String name, String dept, double salary) {
        SQLiteDatabase mdatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, dept);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        return mdatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteEmployee(int id) {
        SQLiteDatabase mdatabase = getWritableDatabase();
        return mdatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }
}
