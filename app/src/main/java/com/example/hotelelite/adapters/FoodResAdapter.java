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
import com.example.hotelelite.models.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FoodResAdapter extends RecyclerView.Adapter<FoodResAdapter.ViewHolder> {

    private Context context;
    private List<?> resCardList;
    private Class<?> activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public FoodResAdapter(Context context, List<?> resCardList, Class<?> activity) {
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
        Food food = (Food) resCardList.get(position);

        DocumentReference documentReference = db.collection("foodReservations").document(food.getId());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food data = new Food(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("userId"),
                        documentSnapshot.getString("foodType"),
                        documentSnapshot.getLong("quantity"),
                        documentSnapshot.getString("deliveryAddress"),
                        documentSnapshot.getString("contactNum"),
                        documentSnapshot.getDate("orderTime"),
                        documentSnapshot.getDate("deliveryTime"),
                        documentSnapshot.getLong("totalAmount")
                );

                switch (data.getFoodType()){
                    case "Hoppers":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.hopper));
                        break;
                    case "Fried Rice":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.fried_rice));
                        break;
                    case "Thoose":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.those));
                        break;
                    case "String Hoppers":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.string_hoppers));
                }
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                holder.foodType.setText(data.getFoodType());
                holder.resDateRange.setText(dateFormat.format(data.getOrderTime()) + "\n" + dateFormat.format(data.getDeliveryTime()));
                holder.totalAmount.setText("Rs." + data.getTotalAmount());

                holder.seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, activity);
                        intent.putExtra("id", data.getId());
                        System.out.println("--------------------------------------- id data.getId() : " + data.getId());
                        intent.putExtra("foodType", data.getFoodType());
                        intent.putExtra("quantity", data.getQuantity());
                        System.out.println("--------------------------------------- data.getQuantity() : " + data.getQuantity());
                        intent.putExtra("deliveryAddress", data.getDeliveryAddress());
                        intent.putExtra("contactNum", data.getContactNum());
                        intent.putExtra("orderTime", dateFormat.format(data.getOrderTime()));
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

        TextView foodType, resDateRange, totalAmount;
        ImageView image;
        Button seeMoreBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.type_image);
            foodType = itemView.findViewById(R.id.type);
            resDateRange = itemView.findViewById(R.id.res_date_range);
            totalAmount = itemView.findViewById(R.id.total_amount);
            seeMoreBtn = itemView.findViewById(R.id.see_more);

        }
    }
}
