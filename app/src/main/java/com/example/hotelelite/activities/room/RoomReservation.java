package com.example.hotelelite.activities.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelelite.MainMenu;
import com.example.hotelelite.R;
import com.example.hotelelite.adapters.RoomResAdapter;
import com.example.hotelelite.models.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RoomReservation extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_reservation);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }




    @Override
    protected void onStart() {
        super.onStart();
        getRoomReservation(mAuth.getCurrentUser().getUid());
    }

    private void getRoomReservation(String currentUser) {

        db.collection("roomReservations")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Room> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot roomRes : value) {
                            Room hallObj = new Room(
                                    roomRes.getString("id"),
                                    roomRes.getString("userId"),
                                    roomRes.getString("roomType"),
                                    roomRes.getLong("numOfRooms"),
                                    roomRes.getDate("checkingIn"),
                                    roomRes.getDate("checkingOut"),
                                    roomRes.getLong("perDay"),
                                    roomRes.getLong("totalDays"),
                                    roomRes.getLong("totalAmount")

                            );
                            resCardList.add(hallObj);
                        }
                        RoomResAdapter adapter = new RoomResAdapter(RoomReservation.this, resCardList, EditRoomRes.class);
                        recyclerView.setAdapter(adapter);
                    }
                });

    }


    public void createRoomRes(View view) {
        Intent i = new Intent(getApplicationContext(), CreateRoomRes.class);
        startActivity(i);
    }
    public void backButton(View view) {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
    }
}