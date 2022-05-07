package com.example.hotelelite.activities.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelelite.R;
import com.example.hotelelite.models.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FoodResDetails extends AppCompatActivity {

    TextView foodTypeTxt, quantityTxt, deliveryAddressTxt, contactNumTxt, orderTimeTxt, deliveryTimeTxt, totalAmountTxt;
    String  foodType, deliveryAddress, orderTime, deliveryTime, contactNum, ACTION;
    Date orderTimeDate, deliveryTimeDate;
    Button confirmRes;
    Integer quantity, totalAmount;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_res_details);

        foodTypeTxt = findViewById(R.id.food_type);
        quantityTxt = findViewById(R.id.food_quantity);
        deliveryAddressTxt = findViewById(R.id.delivery_address);
        contactNumTxt = findViewById(R.id.contact_number);
        orderTimeTxt = findViewById(R.id.order_time);
        deliveryTimeTxt = findViewById(R.id.delivery_time);
        totalAmountTxt = findViewById(R.id.total_amount);
        confirmRes = findViewById(R.id.confirm_res);

        //get data in previous intent
        ACTION = getIntent().getStringExtra("ACTION");
        foodType = getIntent().getStringExtra("foodType");
        quantity = getIntent().getIntExtra("quantity", 0);
        deliveryAddress = getIntent().getStringExtra("deliveryAddress");
        contactNum = getIntent().getStringExtra("contactNum");
        orderTime = getIntent().getStringExtra("orderTime");

        //delivery Time calculation
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            orderTimeDate = dateFormat.parse(orderTime);
            Calendar c = Calendar.getInstance();
            c.setTime(orderTimeDate);
            c.add(Calendar.MINUTE, 30);

            deliveryTimeDate = c.getTime();
            deliveryTime = dateFormat.format(deliveryTimeDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //bill calculation part
        switch (foodType) {
            case "Hoppers":
                totalAmount = 60 * quantity;
                break;
            case "Fried Rice":
                totalAmount = 800 * quantity;
                break;
            case "Thoose":
                totalAmount = 100 * quantity;
                break;
            case "String Hoppers":
                totalAmount = 20 * quantity;
                break;
        }

        //values set in TextViews
        foodTypeTxt.setText(foodType);
        quantityTxt.setText(String.valueOf(quantity));
        deliveryAddressTxt.setText(String.valueOf(deliveryAddress));
        contactNumTxt.setText(String.valueOf(contactNum));
        orderTimeTxt.setText(String.valueOf(orderTime));
        deliveryTimeTxt.setText(String.valueOf(deliveryTime));
        totalAmountTxt.setText(String.valueOf("Rs."+totalAmount));


        //store firebase
        Food food = new Food();
        if (ACTION.equals("POST")){
            food.setId(UUID.randomUUID().toString());
        }else if (ACTION.equals("PUT")){
            food.setId(getIntent().getStringExtra("id"));
        }
        food.setUserId(mAuth.getCurrentUser().getUid());
        food.setFoodType(foodType);
        food.setQuantity(quantity);
        food.setDeliveryAddress(deliveryAddress);
        food.setContactNum(contactNum);
        food.setOrderTime(orderTimeDate);
        food.setDeliveryTime(deliveryTimeDate);
        food.setTotalAmount(totalAmount);

        System.out.println("----------------------- food.setId(getIntent().getStringExtra(\"id\")" + getIntent().getStringExtra("id") );
        confirmRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTION.equals("POST")){
                    saveRoomReservation(food);
                }else if (ACTION.equals("PUT")){
                    try {
                        updateRoomReservation(food);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveRoomReservation(Food food) {

        DocumentReference documentReference = db.collection("foodReservations").document(food.getId());
        documentReference.set(food)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(FoodResDetails.this, "Reservation Saved Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FoodResDetails.this, FoodReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodResDetails.this, "Reservation Saved Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void updateRoomReservation(Food food) throws ParseException {
        Map<String, Object> data = new HashMap<>();
        data.put("foodType", food.getFoodType());
        data.put("quantity", food.getQuantity());
        data.put("deliveryAddress", food.getDeliveryAddress());
        data.put("contactNum", food.getContactNum());
        data.put("orderTime", food.getOrderTime());
        data.put("deliveryTime", food.getDeliveryTime());
        data.put("totalAmount", food.getTotalAmount());

        DocumentReference documentReference = db.collection("foodReservations").document(food.getId());
        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(FoodResDetails.this, "Reservation Update Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FoodResDetails.this, FoodReservation.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodResDetails.this, "Reservation Update Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}