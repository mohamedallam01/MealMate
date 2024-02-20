package com.example.mealmate.weekplan.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.weekplan.MealPlanManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WeekPlanAdapter extends RecyclerView.Adapter<WeekPlanAdapter.MealPlanViewHolder> {
    private static final String TAG = "WeekPlanAdapter";

    private Context context;
    private List<DetailedMeal> mealPlan;

    MealPlanManager mealPlanManager;


    public WeekPlanAdapter(Context context, List<DetailedMeal> mealPlan, MealPlanManager mealPlanManager) {
        this.context = context;
        this.mealPlanManager = mealPlanManager;
        this.mealPlan = mealPlanManager.getMealPlanFromSharedPreferences(context);
    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_plan_item, parent, false);
        return new MealPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {
        DetailedMeal meal = mealPlan.get(position);

        holder.tvDay.setText(meal.getDay());
        Log.d(TAG, "onBindViewHolder: " + meal.getDay());
        holder.tvMealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb())
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivMealImage);

        holder.fabRemoveMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mealPlanManager.removeMealFromSharedPreferences(context, meal.getIdMeal());
                mealPlan.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

            }
        });

        holder.cvMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              WeekPlanFragmentDirections.ActionWeekPlanFragment2ToDetails2 action = WeekPlanFragmentDirections.actionWeekPlanFragment2ToDetails2(meal.getIdMeal());
              Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealPlan.size();
    }

    public class MealPlanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMealName;
        private final ImageView ivMealImage;
        private final TextView tvDay;
        FloatingActionButton fabRemoveMealPlan;

        CardView cvMealPlan;

        public MealPlanViewHolder(@NonNull View view) {
            super(view);
            tvMealName = view.findViewById(R.id.tvPlanMealName);
            ivMealImage = view.findViewById(R.id.ivWeekPlan);
            tvDay = view.findViewById(R.id.tvDay);
            fabRemoveMealPlan = view.findViewById(R.id.fabRemoveMealPlan);
            cvMealPlan = view.findViewById(R.id.cvMealPlan);
        }

    }
}
