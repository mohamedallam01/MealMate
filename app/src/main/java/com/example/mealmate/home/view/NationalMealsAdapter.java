package com.example.mealmate.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.home.model.NationalMeal;
import com.example.mealmate.search.category.model.Category;
import com.example.mealmate.search.category.view.CategoryAdapter;

import java.util.List;

public class NationalMealsAdapter extends RecyclerView.Adapter<NationalMealsAdapter.NationalViewHolder> {

    private final List<NationalMeal> nationalMealList;


    public static final String TAG = "NationalMealsAdapter";

    private final Context context;

    // private OnProductClickListener onProductClickListener;

    MealDao mealDao;


    public NationalMealsAdapter(Context context, List<NationalMeal> nationalMealList) {
        this.context = context;
        this.nationalMealList = nationalMealList;


    }


    @NonNull
    @Override
    public NationalMealsAdapter.NationalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.national_item, parent, false);
        NationalMealsAdapter.NationalViewHolder viewHolder = new NationalMealsAdapter.NationalViewHolder(view);


        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NationalMealsAdapter.NationalViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NationalMeal nationalMeal = nationalMealList.get(position);
        holder.tvNationalName.setText(nationalMeal.getMealName());

        Glide.with(context).load(nationalMeal.getMealThumbUrl())
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivNationalThumbnail);

        Log.i(TAG, "onBindViewHolder: ");

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + nationalMealList.size());
        return nationalMealList.size();

    }


    public static class NationalViewHolder extends RecyclerView.ViewHolder {

        TextView tvNationalName;
        ImageView ivNationalThumbnail;


        public NationalViewHolder(@NonNull View itemView) {


            super(itemView);

            Log.i(TAG, "AreaViewHolder: ");

            tvNationalName = itemView.findViewById(R.id.tvNationalName);
            ivNationalThumbnail = itemView.findViewById(R.id.ivNationalMeal);

        }
    }

    public void setList(List<NationalMeal> nationalMeals) {

        nationalMealList.addAll(nationalMeals);
    }

}