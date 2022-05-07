package com.example.hotelelite.activities.hall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelelite.MainMenu;
import com.example.hotelelite.R;
import com.example.hotelelite.adapters.HallResAdapter;
import com.example.hotelelite.models.Hall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HallReservation extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_reservation);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHallReservation(mAuth.getCurrentUser().getUid());
    }

    private void getHallReservation(String currentUser) {

        db.collection("hallReservations")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Hall> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot hallRes : value) {
                            Hall hallObj = new Hall(
                                    hallRes.getString("id"),
                                    hallRes.getString("userId"),
                                    hallRes.getString("hallType"),
                                    hallRes.getLong("numOfTables"),
                                    hallRes.getDate("checkingIn"),
                                    hallRes.getDate("checkingOut"),
                                    hallRes.getLong("perDay"),
                                    hallRes.getLong("totalDays"),
                                    hallRes.getLong("totalAmount")

                            );
                            resCardList.add(hallObj);
                        }
                        HallResAdapter adapter = new HallResAdapter(HallReservation.this, resCardList, EditHallRes.class);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    public void createHallRes(View view) {
        Intent i = new Intent(getApplicationContext(), CreateHallRes.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
    }
}