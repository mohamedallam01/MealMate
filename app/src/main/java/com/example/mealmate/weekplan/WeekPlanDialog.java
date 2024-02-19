package com.example.mealmate.weekplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.mealmate.R;

public class WeekPlanDialog {

    public interface WeekPlanDialogListener {
        void onMealSelected(String day, String meal);
    }

    public static void show(Context context, final WeekPlanDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_week_plan, null);
        builder.setView(dialogView);

        final RadioGroup radioGroupDays = dialogView.findViewById(R.id.radioGroupDays);

        builder.setTitle("Add Meal to Day")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedRadioButtonId = radioGroupDays.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = radioGroupDays.findViewById(selectedRadioButtonId);
                        if (selectedRadioButton != null && listener != null) {
                            String selectedDay = selectedRadioButton.getText().toString();
                            listener.onMealSelected(selectedDay, "");
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }
}
