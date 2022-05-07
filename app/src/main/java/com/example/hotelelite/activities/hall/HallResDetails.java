package com.example.hotelelite.activities.hall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelelite.R;
import com.example.hotelelite.models.Hall;
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

public class HallResDetails extends AppCompatActivity {

    TextView hallTypeTxt, numOfTablesTxt, dateRangeTxt, totalDaysTxt, totalAmountTxt, perDayTxt;
    String hallType, checkingIn, checkingOut, ACTION;
    Button confirmRes;
    Integer numOfTables, totalAmount, perDay;
    Integer different, elapsedDays;
    Date startDate, endDate;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_res_details);

        Hall hall = new Hall();
        hallTypeTxt = findViewById(R.id.hall_type);
        numOfTablesTxt = findViewById(R.id.no_of_table);
        dateRangeTxt = findViewById(R.id.res_date_range);
        totalDaysTxt = findViewById(R.id.total_days);
        totalAmountTxt = findViewById(R.id.total_amount);
        perDayTxt = findViewById(R.id.per_day);
        confirmRes = findViewById(R.id.confirm_res);

        //get data in previous intent
        ACTION = getIntent().getStringExtra("ACTION");
        hallType = getIntent().getStringExtra("hallType");
        numOfTables = getIntent().getIntExtra("numOfTables", 0);
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
        switch (hallType) {
            case "King Court":
                totalAmount = (int) ((elapsedDays) * 10000) + ((elapsedDays) *(numOfTables *500));
                perDay = 10000 + (numOfTables *500);
                perDayTxt.setText(String.valueOf("(Hall + "+ numOfTables +" Tables) = Rs." + perDay));
                break;
            case "Queen Court":
                totalAmount = ((elapsedDays) * 15000) + ((elapsedDays) *(numOfTables *500));
                perDay = 15000 + (numOfTables *500);
                perDayTxt.setText(String.valueOf("(Hall + "+ numOfTables +" Tables) = Rs." + perDay));
                break;
            case "Crown Court":
                totalAmount =  ((elapsedDays) * 5000) + ((elapsedDays) *(numOfTables *500));
                perDay = 5000 + (numOfTables *500);
                perDayTxt.setText(String.valueOf("(Hall + "+ numOfTables +" Tables) = Rs." + perDay));
                break;
            case "Green Court":
                totalAmount =  ((elapsedDays) * 7500) + ((elapsedDays) *(numOfTables *500));
                perDay = 7500 + (numOfTables *500);
                perDayTxt.setText(String.valueOf("(Hall + "+ numOfTables +" Tables) = Rs." + perDay));
                break;
        }

        //values set in TextViews
        hallTypeTxt.setText(hallType);
        dateRangeTxt.setText(String.valueOf(checkingIn + " - " + checkingOut));
        numOfTablesTxt.setText(String.valueOf(numOfTables));
        totalDaysTxt.setText(String.valueOf(elapsedDays));
        totalAmountTxt.setText(String.valueOf("Rs."+totalAmount));

        //store firebase
        if (ACTION.equals("POST")){
            hall.setId(UUID.randomUUID().toString());
        }else if (ACTION.equals("PUT")){
            hall.setId(getIntent().getStringExtra("id"));
        }
        hall.setUserId(mAuth.getCurrentUser().getUid());
        hall.setHallType(hallType);
        hall.setNumOfTables(numOfTables);
        hall.setCheckingIn(startDate);
        hall.setCheckingOut(endDate);
        hall.setPerDay((int) perDay);
        hall.setTotalDays((int) elapsedDays);
        hall.setTotalAmount((int) totalAmount);

        confirmRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTION.equals("POST")){
                    saveRoomReservation(hall);
                }else if (ACTION.equals("PUT")){
                    try {
                        updateRoomReservation(hall);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateRoomReservation(Hall hall) throws ParseException {

        Map<String, Object> data = new HashMap<>();
        data.put("hallType", hall.getHallType());
        data.put("numOfTables", hall.getNumOfTables());
        data.put("checkingIn", hall.getCheckingIn());
        data.put("checkingOut", hall.getCheckingOut());
        data.put("perDay", hall.getPerDay());
        data.put("totalDays", hall.getTotalDays());
        data.put("totalAmount", hall.getTotalAmount());

        DocumentReference documentReference = db.collection("hallReservations").document(getIntent().getStringExtra("id"));
        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(HallResDetails.this, "Reservation Update Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HallResDetails.this, HallReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HallResDetails.this, "Reservation Update Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveRoomReservation(Hall hall) {

        DocumentReference documentReference = db.collection("hallReservations").document(hall.getId());
        documentReference.set(hall)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(HallResDetails.this, "Reservation Saved Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HallResDetails.this, HallReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HallResDetails.this, "Reservation Saved Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}