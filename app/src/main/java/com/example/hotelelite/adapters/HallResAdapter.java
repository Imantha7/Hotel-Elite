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
import com.example.hotelelite.models.Hall;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HallResAdapter extends RecyclerView.Adapter<HallResAdapter.ViewHolder> {

    private Context context;
    private List<?> resCardList;
    private Class<?> activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HallResAdapter(Context context, List<?> resCardList, Class<?> activity) {
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
        Hall hall = (Hall) resCardList.get(position);

        DocumentReference documentReference = db.collection("hallReservations").document(hall.getId());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Hall data = new Hall(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("userId"),
                        documentSnapshot.getString("hallType"),
                        documentSnapshot.getLong("numOfTables"),
                        documentSnapshot.getDate("checkingIn"),
                        documentSnapshot.getDate("checkingOut"),
                        documentSnapshot.getLong("perDay"),
                        documentSnapshot.getLong("totalDays"),
                        documentSnapshot.getLong("totalAmount")
                );

                switch (data.getHallType()){
                    case "King Court":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.king_court));
                        break;
                    case "Queen Court":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.queen_court));
                        break;
                    case "Crown Court":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.crown_court));
                        break;
                    case "Green Court":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.green_court));
                }
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                holder.hallType.setText(data.getHallType());
                holder.resDateRange.setText(dateFormat.format(data.getCheckingIn()) + " - " + dateFormat.format(data.getCheckingOut()));
                holder.totalAmount.setText("Rs." + data.getTotalAmount());

                holder.seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, activity);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("hallType", data.getHallType());
                        intent.putExtra("numOfTables", data.getNumOfTables());
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

        TextView hallType, resDateRange, totalAmount;
        ImageView image;
        Button seeMoreBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.type_image);
            hallType = itemView.findViewById(R.id.type);
            resDateRange = itemView.findViewById(R.id.res_date_range);
            totalAmount = itemView.findViewById(R.id.total_amount);
            seeMoreBtn = itemView.findViewById(R.id.see_more);

        }
    }
}
