package com.example.administrator.sqlitetest.server;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/3/3.
 */

public class Student {
    Bitmap bitmap;
    String name;
    String id;
    int age;
    String sex;

    public Student(Bitmap bitmap,String name,String id,int age,String sex){
        this.bitmap=bitmap;
        this.name=name;
        this.id=id;
        this.age=age;
        this.sex=sex;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
