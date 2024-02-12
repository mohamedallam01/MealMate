package com.example.mealmate;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mealmate.favorite.Favorite;
import com.example.mealmate.home.Home;
import com.example.mealmate.profile.Profile;
import com.example.mealmate.search.Search;
import com.example.mealmate.splashscreen.SplashScreen;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);

        setBottomNavigationVisibility(false);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        final BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.home) {
                            navController.navigate(R.id.home_graph);
                        } else if (item.getItemId() == R.id.search) {
                            navController.navigate(R.id.search_graph);
                        } else if (item.getItemId() == R.id.favorite) {
                            navController.navigate(R.id.favorite_graph);
                        } else if (item.getItemId() == R.id.profile) {
                            navController.navigate(R.id.profile_graph);
                        }

                        NavOptions navOptions = new NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setRestoreState(true)
                                .setPopUpTo(R.id.home,
                                        false,
                                        true)
                                .build();
                        navController.navigate(item.getItemId(), null, navOptions);

                        return true;
                    }
                };

        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);



    }


    public void setBottomNavigationVisibility(boolean isVisible) {
        bottomNavigationView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}
