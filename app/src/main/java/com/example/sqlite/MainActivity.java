package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    public EditText name ;
    public EditText email ;
    public EditText age ;
    public TextView textView_Result;
    public Button apply;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        email = (EditText) findViewById(R.id.email);
        textView_Result = (TextView) findViewById(R.id.result);
        apply = (Button) findViewById(R.id.apply);

        //going to add data(onCreate)
        sqLiteDatabase = new InfoDb(this).getWritableDatabase();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name" , name.getText().toString());
                values.put("age" , age.getText().toString());
                values.put("email" , email.getText().toString());

                long row = sqLiteDatabase.insert("student" , null , values);

                System.out.println("Ur sqlite data : " + row);

                textView_Result.setText("Name : " + values.get("name") + "\n");
                textView_Result.append("Email : " + values.get("email") + "\n");
                textView_Result.append("Age : " + values.get("age") + "\n");
                textView_Result.append(String.valueOf(row) + "\n");

                //getReadableDataBase :
                getReadabelDatabase();
            }
        });
    }
    public void getReadabelDatabase(){
        sqLiteDatabase = new InfoDb(this).getReadableDatabase();

        String[] projections = {"name" , "email" , "age"};
        Cursor cursor = sqLiteDatabase.query("student" , projections , null , null , null ,null ,null);

        cursor.moveToPosition(18);
        System.out.println("Name is : " + cursor.getString(0));
        textView_Result.append("Name is :" + cursor.getString(0));
    }
}