package com.example.hotelelite.activities.hall;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelelite.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateHallRes extends AppCompatActivity {

    LinearLayout hall1;
    LinearLayout hall2;
    LinearLayout hall3;
    LinearLayout hall4;
    String hallType;

    TextInputEditText numOfTables, checkingIn, checkingOut;
    DatePickerDialog.OnDateSetListener setListenerIn;
    DatePickerDialog.OnDateSetListener setListenerOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hall_res);

        hall1 = (LinearLayout) findViewById(R.id.hall_1);
        hall2 = (LinearLayout) findViewById(R.id.hall_2);
        hall3 = (LinearLayout) findViewById(R.id.hall_3);
        hall4 = (LinearLayout) findViewById(R.id.hall_4);

        numOfTables = findViewById(R.id.no_of_table);
        checkingIn = findViewById(R.id.checking_in);
        checkingOut = findViewById(R.id.checking_out);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        checkingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateHallRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerIn, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerIn = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkingIn.setText(date);
            }
        };

        checkingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateHallRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerOut, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerOut = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkingOut.setText(date);
            }
        };

    }

    public void kingCourt(View view) {
        hall1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        hall2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hallType = "King Court";
    }

    public void queenCourt(View view) {
        hall1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        hall3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hallType = "Queen Court";
    }

    public void crownCourt(View view) {
        hall1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall3.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        hall4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hallType = "Crown Court";
    }

    public void greenCourt(View view) {
        hall1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        hall4.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        hallType = "Green Court";
    }

    public void reservation(View view) {


        if (TextUtils.isEmpty(hallType)) {
            Toast.makeText(CreateHallRes.this, "Hall type cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numOfTables.getText().toString())) {
            Toast.makeText(CreateHallRes.this, "Number of tables cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingIn.getText().toString())) {
            Toast.makeText(CreateHallRes.this, "Checking in cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingOut.getText().toString())) {
            Toast.makeText(CreateHallRes.this, "Checking out cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(CreateHallRes.this, HallResDetails.class);
            intent.putExtra("ACTION", "POST");
            intent.putExtra("hallType", hallType);
            intent.putExtra("numOfTables", Integer.valueOf(numOfTables.getText().toString()));
            intent.putExtra("checkingIn", String.valueOf(checkingIn.getText()));
            intent.putExtra("checkingOut", String.valueOf(checkingOut.getText()));
            startActivity(intent);
        }

    }
}