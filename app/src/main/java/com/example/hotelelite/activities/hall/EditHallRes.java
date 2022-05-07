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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class EditHallRes extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayout hall1;
    LinearLayout hall2;
    LinearLayout hall3;
    LinearLayout hall4;
    String id, hallType, checkingIn, checkingOut;
    long numOfTables;
    TextInputEditText numOfTablesInput, checkingInInput, checkingOutInput;
    DatePickerDialog.OnDateSetListener setListenerIn;
    DatePickerDialog.OnDateSetListener setListenerOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hall_res);

        hall1 = (LinearLayout) findViewById(R.id.hall_1);
        hall2 = (LinearLayout) findViewById(R.id.hall_2);
        hall3 = (LinearLayout) findViewById(R.id.hall_3);
        hall4 = (LinearLayout) findViewById(R.id.hall_4);
        numOfTablesInput = findViewById(R.id.no_of_table);
        checkingInInput = findViewById(R.id.checking_in);
        checkingOutInput = findViewById(R.id.checking_out);

        //get data previous intent
        id = getIntent().getStringExtra("id");
        hallType = getIntent().getStringExtra("hallType");
        numOfTables = getIntent().getLongExtra("numOfTables", 0);
        checkingIn = getIntent().getStringExtra("checkingIn");
        checkingOut = getIntent().getStringExtra("checkingOut");

        //default value set
        switch (hallType) {
            case "King Court":
                hall1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Queen Court":
                hall2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Crown Court":
                hall3.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
            case "Green Court":
                hall4.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
                break;
        }
        numOfTablesInput.setText(String.valueOf(numOfTables));
        checkingInInput.setText(String.valueOf(checkingIn));
        checkingOutInput.setText(String.valueOf(checkingOut));


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

//        numOfTablesInput = findViewById(R.id.no_of_room);
//        checkingInInput = findViewById(R.id.checking_in);
//        checkingOutInput = findViewById(R.id.checking_out);

        checkingInInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditHallRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerIn, year, month, day);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditHallRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerOut, year, month, day);
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

    public void updateRes(View view) {
        if (TextUtils.isEmpty(hallType)) {
            Toast.makeText(EditHallRes.this, "Hall type cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numOfTablesInput.getText().toString())) {
            Toast.makeText(EditHallRes.this, "Number of tables cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingInInput.getText().toString())) {
            Toast.makeText(EditHallRes.this, "Checking in cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(checkingOutInput.getText().toString())) {
            Toast.makeText(EditHallRes.this, "Checking out cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            System.out.println("-------------------------- ID : "+ id);
            Intent intent = new Intent(EditHallRes.this, HallResDetails.class);
            intent.putExtra("ACTION", "PUT");
            intent.putExtra("id", id);
            intent.putExtra("hallType", hallType);
            intent.putExtra("numOfTables", Integer.valueOf(numOfTablesInput.getText().toString()));
            intent.putExtra("checkingIn", String.valueOf(checkingInInput.getText()));
            intent.putExtra("checkingOut", String.valueOf(checkingOutInput.getText()));
            startActivity(intent);
        }
    }

    public void deleteRes(View view) {

        DocumentReference documentReference = db.collection("hallReservations").document(id);
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditHallRes.this, "Hall Reservation Deleted Successfully.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HallReservation.class));
            }
        });
    }
}