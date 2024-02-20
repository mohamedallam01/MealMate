package com.example.mealmate.search.country.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.search.country.model.Country;
import com.example.mealmate.search.country.presenter.CountryPresenter;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.AreaViewHolder> {

    private final List<Country> countryList;


    public static final String TAG = "CountryAdapter";

    private final Context context;

    CountryPresenter countryPresenter;

   // private OnMealClickListener onProductClickListener;

    MealDao mealDao;


    public CountryAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;


    }


    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.country_item, parent, false);
        AreaViewHolder viewHolder = new AreaViewHolder(view);


        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Country country = countryList.get(position);
        holder.tvCountryName.setText(country.getStrArea());


        if(position == 0){
            holder.ivThumbnail.setImageResource(R.drawable.america);
        } else if (position == 1) {
            holder.ivThumbnail.setImageResource(R.drawable.britain);

        }else if (position == 2) {
            holder.ivThumbnail.setImageResource(R.drawable.canda);

        }else if (position == 3) {
            holder.ivThumbnail.setImageResource(R.drawable.china);

        }else if (position == 4) {
            holder.ivThumbnail.setImageResource(R.drawable.coroatia);

        }else if (position == 5) {
            holder.ivThumbnail.setImageResource(R.drawable.dutch);

        }else if (position == 6) {
            holder.ivThumbnail.setImageResource(R.drawable.egypt);

        }else if (position == 7) {
            holder.ivThumbnail.setImageResource(R.drawable.filipino);

        }else if (position == 8) {
            holder.ivThumbnail.setImageResource(R.drawable.france);

        }else if (position == 9) {
            holder.ivThumbnail.setImageResource(R.drawable.greek);

        }else if (position == 10) {
            holder.ivThumbnail.setImageResource(R.drawable.india);

        }else if (position == 11) {
            holder.ivThumbnail.setImageResource(R.drawable.iran);

        }else if (position == 12) {
            holder.ivThumbnail.setImageResource(R.drawable.italy);

        }else if (position == 13) {
            holder.ivThumbnail.setImageResource(R.drawable.jamaican);

        }else if (position == 14) {
            holder.ivThumbnail.setImageResource(R.drawable.japan);

        }else if (position == 15) {
            holder.ivThumbnail.setImageResource(R.drawable.kenya);

        }else if (position == 16) {
            holder.ivThumbnail.setImageResource(R.drawable.malaysia);

        }else if (position == 17) {
            holder.ivThumbnail.setImageResource(R.drawable.mexican);

        }else if (position == 18) {
            holder.ivThumbnail.setImageResource(R.drawable.morocco);

        }else if (position == 19) {
            holder.ivThumbnail.setImageResource(R.drawable.poland);

        }else if (position == 20) {
            holder.ivThumbnail.setImageResource(R.drawable.portugese);

        }else if (position == 21) {
            holder.ivThumbnail.setImageResource(R.drawable.russia);

        }else if (position == 22) {
            holder.ivThumbnail.setImageResource(R.drawable.spanish);

        }else if (position == 23) {
            holder.ivThumbnail.setImageResource(R.drawable.thai);

        }else if (position == 24) {
            holder.ivThumbnail.setImageResource(R.drawable.tunisia);

        }else if (position == 25) {
            holder.ivThumbnail.setImageResource(R.drawable.turkey);

        }else if (position == 26) {
            //holder.ivThumbnail.setImageResource(R.drawable.);

        }
        else if (position == 27) {
            holder.ivThumbnail.setImageResource(R.drawable.vitenam);

        }


        Log.i(TAG, "onBindViewHolder: ");

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + countryList.size());
        return countryList.size();

    }


    public static class AreaViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountryName;
        ImageView ivThumbnail;
        public AreaViewHolder(@NonNull View view) {


            super(view);

            Log.i(TAG, "AreaViewHolder: ");

            tvCountryName = view.findViewById(R.id.tvAreaName);
            ivThumbnail = view.findViewById(R.id.ivArea);



        }
    }

    public void setList(List<Country> country) {

        countryList.addAll(country);
    }

}