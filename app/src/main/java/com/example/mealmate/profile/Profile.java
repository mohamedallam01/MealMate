package com.example.mealmate.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealmate.MainActivity;
import com.example.mealmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile extends Fragment {


    Button btnWeekPlan;

    TextView tvLogout;

    FirebaseAuth firebaseAuth;

    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).setBottomNavigationVisibility(true);

        btnWeekPlan = view.findViewById(R.id.btnWeekPlan);
        tvLogout = view.findViewById(R.id.tvLogout);
        firebaseAuth = FirebaseAuth.getInstance();

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.signOut();
                                Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.action_profile_to_registrationLogin2);
                            } else {
                                Toast.makeText(requireContext(), "Failed to sign out. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    firebaseAuth.signOut();
                    Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_profile_to_registrationLogin2);
                }
            }
        });

        btnWeekPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profile_to_weekPlanFragment);
            }
        });

    }
}