package com.example.hotelelite.activities.food;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelelite.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateFoodRes extends AppCompatActivity {

    LinearLayout food1;
    LinearLayout food2;
    LinearLayout food3;
    LinearLayout food4;
    String foodType, date, time;
    DatePickerDialog.OnDateSetListener setDateListener;
    TimePickerDialog.OnTimeSetListener setTimeListener;
    TextInputEditText quantityInput, deliveryAddressInput, contactNumInput, orderTimeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_res);

        food1 = (LinearLayout) findViewById(R.id.food_1);
        food2 = (LinearLayout) findViewById(R.id.food_2);
        food3 = (LinearLayout) findViewById(R.id.food_3);
        food4 = (LinearLayout) findViewById(R.id.food_4);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        quantityInput = findViewById(R.id.quantity);
        deliveryAddressInput = findViewById(R.id.delivery_address);
        contactNumInput = findViewById(R.id.contact_number);
        orderTimeInput = findViewById(R.id.order_time);

        orderTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateFoodRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setDateListener, year, month, day);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateFoodRes.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setTimeListener, hour, minute, true);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
                datePickerDialog.show();
            }
        });

        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = dayOfMonth + "/" + month + "/" + year;
                orderTimeInput.setText(date+" "+time);
                System.out.println("-------------------------------- "+ orderTimeInput.getText());
            }
        };

        setTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                time = hourOfDay+":"+minute;
                orderTimeInput.setText(date+" "+time);
            }
        };
    }

    public void selectHoppers(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        foodType = "Hoppers";
    }

    public void selectFriedRice(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        food3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        foodType = "Fried Rice";
    }

    public void selectThoose(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food3.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        food4.setBackground(getResources().getDrawable(R.drawable.background_radius));
        foodType = "Thoose";
    }

    public void selectStringHoppers(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food3.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food4.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        foodType = "String Hoppers";
    }

    public void reservation(View view) {

        if (TextUtils.isEmpty(foodType)) {
            Toast.makeText(CreateFoodRes.this, "Food type cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(quantityInput.getText().toString())) {
            Toast.makeText(CreateFoodRes.this, "Food quantity cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(deliveryAddressInput.getText().toString())) {
            Toast.makeText(CreateFoodRes.this, "Delivery address cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(contactNumInput.getText().toString())) {
            Toast.makeText(CreateFoodRes.this, "Contact number cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(date)) {
            Toast.makeText(CreateFoodRes.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(time)) {
            Toast.makeText(CreateFoodRes.this, "Time cannot be empty", Toast.LENGTH_SHORT).show();
        }else {

            Intent intent = new Intent(CreateFoodRes.this, FoodResDetails.class);
            intent.putExtra("ACTION", "POST");
            intent.putExtra("foodType", foodType);
            intent.putExtra("quantity", Integer.valueOf(quantityInput.getText().toString()));
            intent.putExtra("deliveryAddress", deliveryAddressInput.getText().toString());
            intent.putExtra("contactNum", contactNumInput.getText().toString());
            intent.putExtra("orderTime", date + " " + time);
            startActivity(intent);
        }

    }
}