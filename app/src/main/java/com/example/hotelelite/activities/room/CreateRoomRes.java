package com.example.hotelelite.activities.room;

import android.annotation.SuppressLint;
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

public class CreateRoomRes extends AppCompatActivity {
    LinearLayout room1;
    LinearLayout room2;
    LinearLayout room3;
    LinearLayout room4;
    String roomType;

    TextInputEditText numOfRooms, checkingIn, checkingOut;
    DatePickerDialog.OnDateSetListener setListenerIn;
    DatePickerDialog.OnDateSetListener setListenerOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room_res);

        room1 = (LinearLayout) findViewById(R.id.room_1);
        room2 = (LinearLayout) findViewById(R.id.room_2);
        room3 = (LinearLayout) findViewById(R.id.room_3);
        room4 = (LinearLayout) findViewById(R.id.room_4);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        numOfRooms = findViewById(R.id.no_of_room);
        checkingIn = findViewById(R.id.checking_in);
        checkingOut = findViewById(R.id.checking_out);

        checkingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateRoomRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerIn, year, month, day);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateRoomRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerOut, year, month, day);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void kingRoom(View view) {
        room1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        room2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        roomType = "King Room";
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void queenRoom(View view) {
        room1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        room3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        roomType = "Queen Room";
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void singleRoom(View view) {
        room1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room3.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        room4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        roomType = "Single Room";
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void doubleRoom(View view) {
        room1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        room4.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        roomType = "Double Room";
    }

    public void reservation(View view) {


        if (TextUtils.isEmpty(roomType)) {
            Toast.makeText(CreateRoomRes.this, "Room type cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numOfRooms.getText().toString())) {
            Toast.makeText(CreateRoomRes.this, "Number of rooms cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingIn.getText().toString())) {
            Toast.makeText(CreateRoomRes.this, "Checking in cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingOut.getText().toString())) {
            Toast.makeText(CreateRoomRes.this, "Checking out cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(CreateRoomRes.this, RoomResDetails.class);
            intent.putExtra("ACTION", "POST");
            intent.putExtra("roomType", roomType);
            intent.putExtra("numOfRooms", Integer.valueOf(numOfRooms.getText().toString()));
            intent.putExtra("checkingIn", String.valueOf(checkingIn.getText()));
            intent.putExtra("checkingOut", String.valueOf(checkingOut.getText()));
            startActivity(intent);
        }


    }
}