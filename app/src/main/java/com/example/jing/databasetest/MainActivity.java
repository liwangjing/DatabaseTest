package com.example.jing.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button dbButton;
    private Button addData;
    private Button update;
    private Button query;
    private TextView text;
    private MyDatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        dbButton = (Button)findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        addData = (Button)findViewById(R.id.addDataButton);
        addData.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages",454);
                values.put("price", 16.96);
                db.insert("Book",null,values);

                values.put("name","The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);

            }
        });

        update = (Button)findViewById(R.id.updateDataButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name=?", new String[] {"The Da Vinci Code"});
            }
        });

        query = (Button)findViewById(R.id.queryButton);
        query.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                        if (!TextUtils.isEmpty(text.getText())){
                        text.append("  ," +name);
                        }else {
                            text.setText("Books are :" + name);
                        }
                    }while( cursor.moveToNext() );

                }cursor.close();
            }
        });
    }
}
