package com.example.administrator.sqlitetest.server;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.sqlitetest.StudentPresenter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/4.
 */

public class MyStudentSQLite implements StudentPresenter {

    public void caeateTable(SQLiteDatabase db){
        String sql="create table if not exists student(image blob,name varchar(10),id varchar(10),age integer,sex varchar(10))";
        db.execSQL(sql);
    }

    @Override
    public ArrayList<Student> query(SQLiteDatabase db) {
        ArrayList<Student> stuList=new ArrayList<Student>();
        Cursor cur=db.query("student",null,null,null,null,null,null);
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
            byte[] img=cur.getBlob(cur.getColumnIndex("image"));
            Bitmap bitmap= BitmapFactory.decodeByteArray(img,0,img.length);
            String name=cur.getString(cur.getColumnIndex("name"));
            String id=cur.getString(cur.getColumnIndex("id"));
            int age=cur.getInt(cur.getColumnIndex("age"));
            String sex=cur.getString(cur.getColumnIndex("age"));
            Student stu=new Student(bitmap,name,id,age,sex);
            stuList.add(stu);
        }
        return stuList;


    }


    @Override
    public void insert(SQLiteDatabase db, Student s) {
        ContentValues values=new ContentValues();

        ByteArrayOutputStream output=new ByteArrayOutputStream();
        s.getBitmap().compress(Bitmap.CompressFormat.PNG,100,output);
        values.put("image",output.toByteArray());
        values.put("name",s.getName());
        values.put("id",s.getId());
        values.put("age",s.getAge());
        values.put("sex",s.getSex());
        db.insert("student",null,values);
    }

    @Override
    public void delete(SQLiteDatabase db, int id) {
        String sql="delete from student where id="+id;
        db.execSQL(sql);
    }

    @Override
    public void update(SQLiteDatabase db, Student s) {
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        s.getBitmap().compress(Bitmap.CompressFormat.PNG,100,output);
        String sql="update student set image="+output.toByteArray()+",name="+s.getName()
                +",age="+s.getAge()+",sex="+s.getSex()+" where id="+s.getId();
        db.execSQL(sql);
    }
}
