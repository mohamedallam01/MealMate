package com.example.mealmate.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.search.presenter.SearchPresenterImpl;

import java.util.List;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchMealViewHolder> {

    private List<DetailedMeal> detailedMealList;


    public static final String TAG = "SearchAdapter";

    private final Context context;

    SearchPresenterImpl searchPresenter;



    public SearchAdapter(Context context, List<DetailedMeal> detailedMealList, SearchPresenterImpl searchPresenter) {
        this.context = context;
        this.detailedMealList = detailedMealList;
        this.searchPresenter = searchPresenter;


    }


    @NonNull
    @Override
    public SearchAdapter.SearchMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_item, parent, false);
        SearchAdapter.SearchMealViewHolder viewHolder = new SearchAdapter.SearchMealViewHolder(view);


        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchMealViewHolder holder, @SuppressLint("RecyclerView") int position) {

        DetailedMeal detailedMeal = detailedMealList.get(position);
        holder.tvSearchMealName.setText(detailedMeal.getStrMeal());


        if (holder.itemView.getContext() != null) {
            Glide.with(context).load(detailedMeal.getStrMealThumb())
                    .apply(new RequestOptions().override(200, 200))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.ivSearchThumbnail);
        }


        Log.i(TAG, "onBindViewHolder: ");

        holder.cvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDirections.ActionSearchToDetails action = SearchDirections.actionSearchToDetails(detailedMeal.getIdMeal());
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        int mealsSize = 0;
        if (detailedMealList != null) {
            mealsSize = Objects.requireNonNull(detailedMealList.size());
        }

        Log.i(TAG, "getItemCount: ");

        return mealsSize;

    }


    public static class SearchMealViewHolder extends RecyclerView.ViewHolder {

        TextView tvSearchMealName;
        ImageView ivSearchThumbnail;
        CardView cvSearch;



        public SearchMealViewHolder(@NonNull View view) {
            super(view);

            Log.i(TAG, "SearchMealViewHolder: ");

            tvSearchMealName = view.findViewById(R.id.tvSearchMealName);
            ivSearchThumbnail = view.findViewById(R.id.ivSearch);
            cvSearch = view.findViewById(R.id.cvSearch);

        }
    }

    public void setList(List<DetailedMeal> meals) {
        this.detailedMealList = meals;
        notifyDataSetChanged();
    }
}
