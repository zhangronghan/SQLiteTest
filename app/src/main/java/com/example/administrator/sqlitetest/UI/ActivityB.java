package com.example.administrator.sqlitetest.UI;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.sqlitetest.R;
import com.example.administrator.sqlitetest.StudentPresenter;
import com.example.administrator.sqlitetest.server.MyStudentSQLite;
import com.example.administrator.sqlitetest.server.Student;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ActivityB extends AppCompatActivity {
    private StudentPresenter mStudentPresenter=new MyStudentSQLite();
    private static final int GET_HEAD = 1002;
    private ImageView mImageView;
    private Button btnSave;
    private EditText edtName,edtNum,edtAge;
    private RadioButton rbIsBoy,rbIsGril;
    private boolean isBoy=false;
    private String sex;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_b);

        String path=getFilesDir().getAbsolutePath()+ File.separator+"stu.db";
        db=SQLiteDatabase.openOrCreateDatabase(path,null);

        init();
    }



    private void init() {
        mImageView= (ImageView) findViewById(R.id.iv_image);
        btnSave=(Button)findViewById(R.id.btn_Save);
        edtName= (EditText) findViewById(R.id.edt_name);
        edtNum= (EditText) findViewById(R.id.edt_num);
        edtAge= (EditText) findViewById(R.id.edt_age);
        rbIsBoy= (RadioButton) findViewById(R.id.rb_isBoy);
        rbIsGril= (RadioButton) findViewById(R.id.rb_isGril);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GET_HEAD);

            }
        });

        rbIsBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBoy=true;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setDrawingCacheEnabled(true);
                Bitmap bitmap=Bitmap.createBitmap(mImageView.getDrawingCache());
                mImageView.setDrawingCacheEnabled(false);

                String name=edtName.getText().toString().trim();
                String id=edtNum.getText().toString().trim();
                int age= Integer.valueOf(edtAge.getText().toString());
                if(isBoy){
                    sex="男";
                } else {
                    sex="女";
                }

                if(name.equals("") || id.equals("")){
                    Toast.makeText(ActivityB.this,"请正确输入姓名和学号",Toast.LENGTH_SHORT).show();
                } else {
                    Student stu=new Student(bitmap,name,id,age,sex);
                    mStudentPresenter.insert(db,stu);
                    Intent intent=new Intent();
                    intent.setClass(ActivityB.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        getData();

    }

    private void getData() {
        Bitmap bitmap=getIntent().getParcelableExtra("image");
        String name=getIntent().getStringExtra("name");
        String id=getIntent().getStringExtra("id");
        int age=getIntent().getIntExtra("age",0);
        String sex1=getIntent().getStringExtra("sex");


        mImageView.setImageBitmap(bitmap);
        edtName.setText(name);
        edtNum.setText(id);
        edtAge.setText(String.valueOf(age));
/*        if(sex1.equals("男")){
            rbIsBoy.setChecked(true);
        } else {
            rbIsGril.setChecked(true);
        }*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==GET_HEAD){
            Uri uri=data.getData();
            ContentResolver content=this.getContentResolver();
            try {
                Bitmap bitmap= BitmapFactory.decodeStream(content.openInputStream(uri));
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}
