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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class EditRoomRes extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayout room1;
    LinearLayout room2;
    LinearLayout room3;
    LinearLayout room4;
    String id, roomType, checkingIn, checkingOut;
    long numOfRooms;
    TextInputEditText numOfRoomsInput, checkingInInput, checkingOutInput;
    DatePickerDialog.OnDateSetListener setListenerIn;
    DatePickerDialog.OnDateSetListener setListenerOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room_res);

        room1 = (LinearLayout) findViewById(R.id.room_1);
        room2 = (LinearLayout) findViewById(R.id.room_2);
        room3 = (LinearLayout) findViewById(R.id.room_3);
        room4 = (LinearLayout) findViewById(R.id.room_4);
        numOfRoomsInput = findViewById(R.id.no_of_room);
        checkingInInput = findViewById(R.id.checking_in);
        checkingOutInput = findViewById(R.id.checking_out);


        //get data previous intent
        id = getIntent().getStringExtra("id");
        roomType = getIntent().getStringExtra("roomType");
        numOfRooms = getIntent().getLongExtra("numOfRooms", 0);
        checkingIn = getIntent().getStringExtra("checkingIn");
        checkingOut = getIntent().getStringExtra("checkingOut");

        //default value set
        switch (roomType) {
            case "King Room":
                room1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Queen Room":
                room2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Single Room":
                room3.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Double Room":
                room4.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
        }
        numOfRoomsInput.setText(String.valueOf(numOfRooms));
        checkingInInput.setText(String.valueOf(checkingIn));
        checkingOutInput.setText(String.valueOf(checkingOut));


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

//        numOfRoomsInput = findViewById(R.id.no_of_room);
//        checkingInInput = findViewById(R.id.checking_in);
//        checkingOutInput = findViewById(R.id.checking_out);

        checkingInInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRoomRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerIn, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerIn = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkingInInput.setText(date);
            }
        };

        checkingOutInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRoomRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerOut, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerOut = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                checkingOutInput.setText(date);
            }
        };

    }

    public void updateRes(View view) {

        if (TextUtils.isEmpty(roomType)) {
            Toast.makeText(EditRoomRes.this, "Room type cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numOfRoomsInput.getText().toString())) {
            Toast.makeText(EditRoomRes.this, "Number of rooms cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingInInput.getText().toString())) {
            Toast.makeText(EditRoomRes.this, "Checking in cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingOutInput.getText().toString())) {
            Toast.makeText(EditRoomRes.this, "Checking out cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            System.out.println("-------------------------- ID : "+ id);
            Intent intent = new Intent(EditRoomRes.this, RoomResDetails.class);
            intent.putExtra("ACTION", "PUT");
            intent.putExtra("id", id);
            intent.putExtra("roomType", roomType);
            intent.putExtra("numOfRooms", Integer.valueOf(numOfRoomsInput.getText().toString()));
            intent.putExtra("checkingIn", String.valueOf(checkingInInput.getText()));
            intent.putExtra("checkingOut", String.valueOf(checkingOutInput.getText()));
            startActivity(intent);
        }

    }

    public void deleteRes(View view) {
        DocumentReference documentReference = db.collection("roomReservations").document(id);
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditRoomRes.this, "Room Reservation Deleted Successfully.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RoomReservation.class));
            }
        });
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
}