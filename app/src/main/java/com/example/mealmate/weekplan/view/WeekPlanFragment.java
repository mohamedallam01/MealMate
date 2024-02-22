package com.example.mealmate.weekplan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.weekplan.MealPlanManager;

import java.util.List;

public class WeekPlanFragment extends Fragment {

    private RecyclerView rvWeekPlan;
    private WeekPlanAdapter weekPlanAdapter;

    private MealPlanManager mealPlanManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_plan, container, false);
        rvWeekPlan = view.findViewById(R.id.rvWeekPlan);
        rvWeekPlan.setLayoutManager(new LinearLayoutManager(getContext()));
        mealPlanManager = new MealPlanManager();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<DetailedMeal> mealPlan = mealPlanManager.getMealPlanFromSharedPreferences(requireContext());
        weekPlanAdapter = new WeekPlanAdapter(requireContext(), mealPlan,mealPlanManager);
        rvWeekPlan.setAdapter(weekPlanAdapter);
    }
}
