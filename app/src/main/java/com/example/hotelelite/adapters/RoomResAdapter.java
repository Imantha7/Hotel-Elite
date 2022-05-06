package com.example.hotelelite.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelelite.R;
import com.example.hotelelite.models.Room;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RoomResAdapter extends RecyclerView.Adapter<RoomResAdapter.ViewHolder> {

    private Context context;
    private List<?> resCardList;
    private Class<?> activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public RoomResAdapter(Context context, List<?> resCardList, Class<?> activity) {
        this.context = context;
        this.resCardList = resCardList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = (Room) resCardList.get(position);

        DocumentReference documentReference = db.collection("roomReservations").document(room.getId());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Room data = new Room(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("userId"),
                        documentSnapshot.getString("roomType"),
                        documentSnapshot.getLong("numOfRooms"),
                        documentSnapshot.getDate("checkingIn"),
                        documentSnapshot.getDate("checkingOut"),
                        documentSnapshot.getLong("perDay"),
                        documentSnapshot.getLong("totalDays"),
                        documentSnapshot.getLong("totalAmount")
                );

                switch (data.getRoomType()){
                    case "King Room":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.king_room));
                        break;
                    case "Queen Room":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.queen_room));
                        break;
                    case "Double Room":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.double_room));
                        break;
                    case "Single Room":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.single_room));
                }
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                holder.roomType.setText(data.getRoomType());
                holder.resDateRange.setText(dateFormat.format(data.getCheckingIn()) + " - " + dateFormat.format(data.getCheckingOut()));
                holder.totalAmount.setText("Rs." + data.getTotalAmount());

                holder.seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, activity);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("roomType", data.getRoomType());
                        intent.putExtra("numOfRooms", data.getNumOfRooms());
                        intent.putExtra("checkingIn", dateFormat.format(data.getCheckingIn()));
                        intent.putExtra("checkingOut", dateFormat.format(data.getCheckingOut()));
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return resCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView roomType, resDateRange, totalAmount;
        ImageView image;
        Button seeMoreBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.type_image);
            roomType = itemView.findViewById(R.id.type);
            resDateRange = itemView.findViewById(R.id.res_date_range);
            totalAmount = itemView.findViewById(R.id.total_amount);
            seeMoreBtn = itemView.findViewById(R.id.see_more);

        }
    }
}
