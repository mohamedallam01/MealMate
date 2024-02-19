package com.example.mealmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.appcheck.interop.BuildConfig;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController);

        setBottomNavigationVisibility(false);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        NavOptions navOptions = new NavOptions.Builder()
                .setRestoreState(true)
                .setPopUpTo(R.id.home,
                        false,
                        true)
                .build();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    navController.navigate(R.id.home_graph, null, navOptions);
                } else if (item.getItemId() == R.id.search) {
                    navController.navigate(R.id.search_graph, null, navOptions);
                } else if (item.getItemId() == R.id.favorite) {
                    navController.navigate(R.id.favorite_graph, null, navOptions);
                } else if (item.getItemId() == R.id.profile) {
                    navController.navigate(R.id.profile_graph, null, navOptions);
                }

                return true;
            }
        });

    }

    public void setBottomNavigationVisibility(boolean isVisible) {
        bottomNavigationView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}
