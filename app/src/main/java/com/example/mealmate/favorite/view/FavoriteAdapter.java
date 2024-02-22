package com.example.mealmate.favorite.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.favorite.presenter.FavoritesPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.DetailedMealViewHolder> {

    private  List<DetailedMeal> detailedMealList;


    public static final String TAG = "FavoriteAdapter";

    private final Context context;

    private OnFavClickListener onFavClickListener;



    public FavoriteAdapter(Context context, List<DetailedMeal> detailedMealList, OnFavClickListener onFavClickListener,FavoritesPresenter favoritesPresenter) {
        this.context = context;
        this.detailedMealList = detailedMealList;
        this.onFavClickListener = onFavClickListener;


    }


    @NonNull
    @Override
    public DetailedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favorite_item, parent, false);
        DetailedMealViewHolder viewHolder = new DetailedMealViewHolder(view);


        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DetailedMealViewHolder holder, @SuppressLint("RecyclerView") int position) {

        DetailedMeal detailedMeal = detailedMealList.get(position);
        holder.tvFavMealName.setText(detailedMeal.getStrMeal());



        if (holder.itemView.getContext() != null) {
            Glide.with(context).load(detailedMeal.getStrMealThumb())
                    .apply(new RequestOptions().override(200, 200))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.ivFavThumbnail);
        }



        holder.fabRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        onFavClickListener.onMealClick(detailedMeal);

                    }
                }).start();

                Toast.makeText(context, "Meal Removed", Toast.LENGTH_SHORT).show();
            }
        });


        Log.i(TAG, "onBindViewHolder: ");

    }

    @Override
    public int getItemCount() {
        int mealsSize = 0;
        if (detailedMealList != null) {
            mealsSize = Objects.requireNonNull(detailedMealList.size());
        }

        Log.i(TAG, "getItemCount: ");

        return mealsSize;

    }




    public static class DetailedMealViewHolder extends RecyclerView.ViewHolder {

        TextView tvFavMealName;
        ImageView ivFavThumbnail;

        FloatingActionButton fabRemoveFav;


        public DetailedMealViewHolder(@NonNull View itemView) {


            super(itemView);

            Log.i(TAG, "ProductViewHolder: ");

            tvFavMealName = itemView.findViewById(R.id.tvFavMealName);
            ivFavThumbnail = itemView.findViewById(R.id.ivFavorite);
            fabRemoveFav = itemView.findViewById(R.id.fabRemoveFav);

        }
    }


}