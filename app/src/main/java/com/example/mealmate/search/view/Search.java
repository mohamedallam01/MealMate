package com.example.mealmate.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.MainActivity;
import com.example.mealmate.R;

public class Search extends Fragment {

    CardView cvByCountry;
    CardView cvByIngredients;
    CardView cvByCategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).setBottomNavigationVisibility(true);

        cvByCountry = view.findViewById(R.id.cvCountry);
        cvByCategory = view.findViewById(R.id.cvCategory);
        cvByIngredients = view.findViewById(R.id.cvIngredients);


        cvByCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_byCountry);
            }
        });
        cvByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_byCategory);
            }
        });
        cvByIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_search_to_byIngredients);
            }
        });

    }
}