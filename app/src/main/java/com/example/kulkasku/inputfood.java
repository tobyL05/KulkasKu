package com.example.kulkasku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kulkasku.db.Item;
import com.example.kulkasku.db.ItemDB;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class inputfood extends AppCompatActivity {

    private EditText foodname, exp_date;
    private Button add_food;
    private ItemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputfood);

        add_food = findViewById(R.id.add_food);
        foodname = findViewById(R.id.foodname);
        exp_date = findViewById(R.id.exp_date);

        db = Room.databaseBuilder(getApplicationContext(), ItemDB.class, "items-list").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String food = foodname.getText().toString();
                String expDate = exp_date.getText().toString();
                if (food.length() != 0 && expDate.length() != 0) {
                    try {
                        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                        df.setLenient(false);
                        df.parse(exp_date.getText().toString());
                        Item newItem = new Item(food, expDate);
                        db.itemDAO().insertAll(newItem);
                        foodname.getText().clear();
                        exp_date.getText().clear();
                        finish();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Invalid date format!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}