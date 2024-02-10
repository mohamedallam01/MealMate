package com.example.mealmate.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.MainActivity;
import com.example.mealmate.R;

public class SplashScreen extends Fragment {

    boolean isLogged = false;

    NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         navController = Navigation.findNavController(requireView());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(isLogged){

                    navController.navigate(R.id.action_splashScreen_to_logged_in_graph);
                    ((MainActivity) requireActivity()).setBottomNavigationVisibility(false);


                }
                else {
                    navController.navigate(R.id.action_splashScreen_to_new_user_graph5);
                }



            }


        }, 2000);



    }
}