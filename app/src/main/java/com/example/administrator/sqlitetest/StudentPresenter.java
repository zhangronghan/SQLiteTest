package com.example.administrator.sqlitetest;

import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.sqlitetest.server.Student;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public interface StudentPresenter {
    void caeateTable(SQLiteDatabase db);
    ArrayList<Student> query(SQLiteDatabase db);
    void insert(SQLiteDatabase db,Student s);
    void delete(SQLiteDatabase db,int id);
    void update(SQLiteDatabase db,Student s);

}
