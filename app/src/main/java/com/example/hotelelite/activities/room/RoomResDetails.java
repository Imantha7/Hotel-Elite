package com.example.hotelelite.activities.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelelite.R;
import com.example.hotelelite.models.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomResDetails extends AppCompatActivity {

    TextView roomTypeTxt, numOfRoomsTxt, dateRangeTxt, totalDaysTxt, totalAmountTxt, perDayTxt;
    String roomType, checkingIn, checkingOut, ACTION;
    Button confirmRes;
    Integer numOfRooms, totalAmount, perDay;
    Integer different, elapsedDays;
    Date startDate, endDate;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_res_details);

        Room room = new Room();
        roomTypeTxt = findViewById(R.id.type);
        numOfRoomsTxt = findViewById(R.id.no_of_room);
        dateRangeTxt = findViewById(R.id.res_date_range);
        totalDaysTxt = findViewById(R.id.total_days);
        totalAmountTxt = findViewById(R.id.total_amount);
        perDayTxt = findViewById(R.id.per_day);
        confirmRes = findViewById(R.id.confirm_res);

        //get data in previous intent
        ACTION = getIntent().getStringExtra("ACTION");
        roomType = getIntent().getStringExtra("roomType");
        numOfRooms = getIntent().getIntExtra("numOfRooms", 0);
        checkingIn = getIntent().getStringExtra("checkingIn");
        checkingOut = getIntent().getStringExtra("checkingOut");


        //date calculation part
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkingIn);
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkingOut);

            different =(int) (endDate.getTime() - startDate.getTime());
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = (int) (different / daysInMilli);
            different = (int) (different % daysInMilli);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //bill calculation part
        switch (roomType) {
            case "King Room":
                totalAmount = (int) ((elapsedDays) * 10000)*numOfRooms;
                perDay = numOfRooms*10000;
                perDayTxt.setText(String.valueOf("(Rs.10000) X ("+ numOfRooms  + " Rooms) = Rs." + perDay));
                break;
            case "Queen Room":
                totalAmount = ((elapsedDays) * 15000)*numOfRooms;
                perDay = numOfRooms*15000;
                perDayTxt.setText(String.valueOf("(Rs.15000) X ("+ numOfRooms  + " Rooms) = Rs." + numOfRooms*15000));
                break;
            case "Single Room":
                totalAmount =  ((elapsedDays) * 5000)*numOfRooms;
                perDay = numOfRooms*5000;
                perDayTxt.setText(String.valueOf("(Rs.5000) X ("+ numOfRooms  + " Rooms) = Rs." + numOfRooms*5000));
                break;
            case "Double Room":
                totalAmount =  ((elapsedDays) * 7500)*numOfRooms;
                perDay = numOfRooms*7500;
                perDayTxt.setText(String.valueOf("(Rs.7500) X ("+ numOfRooms  + " Rooms) = Rs." + numOfRooms*7500));
                break;
        }

        //values set in TextViews
        roomTypeTxt.setText(roomType);
        dateRangeTxt.setText(String.valueOf(checkingIn + " - " + checkingOut));
        numOfRoomsTxt.setText(String.valueOf(numOfRooms));
        totalDaysTxt.setText(String.valueOf(elapsedDays));
        totalAmountTxt.setText(String.valueOf("Rs."+totalAmount));

        //store firebase
        if (ACTION.equals("POST")){
            room.setId(UUID.randomUUID().toString());
        }else if (ACTION.equals("PUT")){
            room.setId(getIntent().getStringExtra("id"));
        }
        room.setUserId(mAuth.getCurrentUser().getUid());
        room.setRoomType(roomType);
        room.setNumOfRooms(numOfRooms);
        room.setCheckingIn(startDate);
        room.setCheckingOut(endDate);
        room.setPerDay((int) perDay);
        room.setTotalDays((int) elapsedDays);
        room.setTotalAmount((int) totalAmount);

        confirmRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTION.equals("POST")){
                saveRoomReservation(room);
                }else if (ACTION.equals("PUT")){
                    try {
                    updateRoomReservation(room);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateRoomReservation(Room room) throws ParseException {

        Map<String, Object> data = new HashMap<>();
        data.put("roomType", room.getRoomType());
        data.put("numOfRooms", room.getNumOfRooms());
        data.put("checkingIn", room.getCheckingIn());
        data.put("checkingOut", room.getCheckingOut());
        data.put("perDay", room.getPerDay());
        data.put("totalDays", room.getTotalDays());
        data.put("totalAmount", room.getTotalAmount());

        DocumentReference documentReference = db.collection("roomReservations").document(getIntent().getStringExtra("id"));
        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RoomResDetails.this, "Reservation Update Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RoomResDetails.this, RoomReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RoomResDetails.this, "Reservation Update Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveRoomReservation(Room room) {

        DocumentReference documentReference = db.collection("roomReservations").document(room.getId());
        documentReference.set(room)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RoomResDetails.this, "Reservation Saved Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RoomResDetails.this, RoomReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RoomResDetails.this, "Reservation Saved Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}