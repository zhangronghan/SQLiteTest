package com.example.administrator.sqlitetest.UI;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.sqlitetest.R;
import com.example.administrator.sqlitetest.StudentPresenter;
import com.example.administrator.sqlitetest.server.MyStudentSQLite;
import com.example.administrator.sqlitetest.server.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyItemClickListener,onDeleteLisener{
    private Button btnAdd;
    private StudentPresenter mStudentPresenter=new MyStudentSQLite();
    private SQLiteDatabase db;
    private MyAdapter myAdapter;
    private List<Student> stuList=new ArrayList<Student>();
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createOrOpenDatabase();

        init();

        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        stuList=readFromDatabase();

        myAdapter=new MyAdapter(stuList,MainActivity.this);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(this);


    }

    private List<Student> readFromDatabase() {
        stuList.clear();

        Cursor cur=db.query("student",null,null,null,null,null,null);

        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
            byte[] image=cur.getBlob(cur.getColumnIndex("image"));
            Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            String name=cur.getString(cur.getColumnIndex("name"));
            String id=cur.getString(cur.getColumnIndex("id"));
            int age=cur.getInt(cur.getColumnIndex("age"));
            String sex=cur.getString(cur.getColumnIndex("sex"));

            Student stu=new Student(bitmap,name,id,age,sex);
            stuList.add(stu);

        }
        return stuList;
    }


    public void createOrOpenDatabase(){
        String path=getFilesDir().getAbsolutePath()+ File.separator+"stu.db";
        db=SQLiteDatabase.openOrCreateDatabase(path,null);

        mStudentPresenter.caeateTable(db);

    }

    private void init() {
        btnAdd= (Button) findViewById(R.id.btn_Add);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,ActivityB.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        Bitmap bitmap=stuList.get(position).getBitmap();
        String name=stuList.get(position).getName();
        String id=stuList.get(position).getId();
        int age=stuList.get(position).getAge();
        String sex=stuList.get(position).getSex();

        Intent intent=new Intent(MainActivity.this,ActivityB.class);
        intent.putExtra("image",bitmap);
        intent.putExtra("name" ,name);
        intent.putExtra("id",id);
        intent.putExtra("age",age);
        intent.putExtra("sex",sex);
        startActivity(intent);

    }



    @Override
    public void del(int id) {
        db.delete("student","id="+id,null);
        stuList=readFromDatabase();
        myAdapter.notifyDataSetChanged();
    }
}
