package com.example.mealmate.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mealmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Fragment {

    EditText etUsername, etPass, etEmail, etConfirmPass;

    Button btnSignUp;

    FirebaseAuth mAuth;

    ProgressBar pbReg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = view.findViewById(R.id.etUserName);
        etPass = view.findViewById(R.id.etPass);
        etEmail = view.findViewById(R.id.etEmail);
        etConfirmPass = view.findViewById(R.id.etConfirmPass);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        pbReg = view.findViewById(R.id.progressBarReg);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String email = etEmail.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();
                String etPassText = etPass.getText().toString().trim();
                String etEmailText = etEmail.getText().toString().trim();
                String etConfirmPassText = etConfirmPass.getText().toString().trim();
                if (etPassText.isEmpty() || etEmailText.isEmpty() || etConfirmPassText.isEmpty()) {
                    if (etEmailText.isEmpty()) {
                        etEmail.setError("Email is required");
                        etEmail.requestFocus();
                    }
                    if (etPassText.isEmpty()) {
                        etPass.setError("Password is required");
                        etPass.requestFocus();
                    }
                    if (etConfirmPassText.isEmpty()) {
                        etConfirmPass.setError("Confirm Password is required");
                        etConfirmPass.requestFocus();
                    }
                    return;
                }
                if (checkPass() && checkEmail()) {
                    pbReg.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        saveUserDataToDatabase(uid, username, email, pass);
                                        Navigation.findNavController(view).navigate(R.id.action_registration_to_login);
                                        pbReg.setVisibility(View.GONE);
                                    } else {
                                        //Toast.makeText(requireContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        if (!checkEmail()){
                                            etEmail.setError(task.getException().getMessage());
                                            etPass.requestFocus();

                                        }
                                        else {
                                            etPass.setError(task.getException().getMessage());
                                            etPass.requestFocus();
                                        }

                                    }

                                }
                            });
                } else if (!checkPass()){

                    etPass.setError("Passwords are not matched");
                    etConfirmPass.setError("Passwords are not matched");
                    etPass.requestFocus();
                    etConfirmPass.requestFocus();

                }

            }

        });
    }

    private void saveUserDataToDatabase(String uid, String username, String email, String pass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        User newUser = new User(username, email, pass);
        usersRef.child(uid).setValue(newUser);
    }


    private boolean checkPass() {
        String pass = etPass.getText().toString();
        String confirmPass = etConfirmPass.getText().toString();

        return pass.equals(confirmPass);

    }

    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean checkEmail() {
        String email = etEmail.getText().toString().trim();
        if (isValidEmail(email)) {
            return true;
        } else {
            etEmail.setError("E-Mail Address is not formatted");
            return false;
        }
    }


}