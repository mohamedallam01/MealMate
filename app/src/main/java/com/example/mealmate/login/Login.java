package com.example.mealmate.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;

import java.util.Objects;


public class Login extends Fragment {

    EditText etEmailLog, etPassLog;
    Button btnLogin;

    FirebaseAuth mAuth;

    private static final String TAG = "Login";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmailLog = view.findViewById(R.id.etEmailLog);
        etPassLog = view.findViewById(R.id.etPassLog);
        btnLogin = view.findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials(view);
            }
        });


    }

    private void updateUI(FirebaseUser user, View view) {
        Navigation.findNavController(view).navigate(R.id.action_login_to_logged_in_graph);

    }

    public Boolean validateUserName() {
        String username = etEmailLog.getText().toString();

        if (username.isEmpty()) {
            etEmailLog.setError("E-mail Can not be empty");
            return false;
        } else {
            etEmailLog.setError(null);
            return true;
        }
    }

    public Boolean validatePass() {
        String pass = etPassLog.getText().toString();

        if (pass.isEmpty()) {
            etPassLog.setError("Password Can not be empty");
            return false;
        } else {
            etPassLog.setError(null);
            return true;
        }
    }

    public void checkCredentials(View view) {
        String email = etEmailLog.getText().toString().trim();
        String pass = etPassLog.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                            String uid;
                            if (user != null) {
                                uid = user.getUid();
                                Log.e(TAG, "user is not null " + uid);

                                usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String passFromDB = snapshot.child("password").getValue(String.class);
                                            if (!Objects.equals(passFromDB, pass)) {
                                                etPassLog.setError("Invalid E-mail or Password");
                                                etPassLog.requestFocus();
                                            } else {
                                                Navigation.findNavController(view).navigate(R.id.action_login_to_logged_in_graph);
                                            }
                                        } else {
                                            etEmailLog.setError("Invalid E-Mail or Password");
                                            etEmailLog.requestFocus();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e(TAG, "onCancelled: " + error);
                                    }
                                });
                            }
                            else {
                                Log.e(TAG, "User object is null");}
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.e(TAG, "Invalid email or password");
                                //Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                etEmailLog.setError("E-mail does not exist");
                                etEmailLog.requestFocus();
                            } else {
                                Log.e(TAG, "Login failed: " + task.getException().getMessage());
                                Toast.makeText(requireContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}


