package com.example.hotelelite.activities.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelelite.MainMenu;
import com.example.hotelelite.R;
import com.example.hotelelite.adapters.FoodResAdapter;
import com.example.hotelelite.models.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodReservation extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reservation);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void createFoodRes(View view) {
        Intent intent = new Intent(FoodReservation.this, CreateFoodRes.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        getFoodReservation(mAuth.getCurrentUser().getUid());
    }

    private void getFoodReservation(String currentUser) {

        db.collection("foodReservations")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Food> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot foodRes : value) {
                            Food foodObj = new Food(
                                    foodRes.getString("id"),
                                    foodRes.getString("userId"),
                                    foodRes.getString("foodType"),
                                    foodRes.getLong("quantity"),
                                    foodRes.getString("deliveryAddress"),
                                    foodRes.getString("contactNum"),
                                    foodRes.getDate("orderTime"),
                                    foodRes.getDate("deliveryTime"),
                                    foodRes.getLong("totalAmount")

                            );
                            resCardList.add(foodObj);
                        }
                        FoodResAdapter adapter = new FoodResAdapter(FoodReservation.this, resCardList, EditFoodRes.class);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
    }
}