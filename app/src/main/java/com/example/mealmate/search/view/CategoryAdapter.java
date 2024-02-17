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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.search.category.model.Category;
import com.example.mealmate.search.country.model.Area;
import com.example.mealmate.search.country.view.AreaAdapter;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<Category> categoryList;


    public static final String TAG = "CategoryAdapter";

    private final Context context;

    // private OnProductClickListener onProductClickListener;

    MealDao mealDao;


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;


    }


    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item, parent, false);
        CategoryAdapter.CategoryViewHolder viewHolder = new CategoryAdapter.CategoryViewHolder(view);


        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getStrCategory());

        Glide.with(context).load(category.getStrCategoryThumb())
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivCategoryThumbnail);

        Log.i(TAG, "onBindViewHolder: ");

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + categoryList.size());
        return categoryList.size();

    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        ImageView ivCategoryThumbnail;


        public CategoryViewHolder(@NonNull View itemView) {


            super(itemView);

            Log.i(TAG, "AreaViewHolder: ");

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            ivCategoryThumbnail = itemView.findViewById(R.id.ivCategory);

        }
    }

    public void setList(List<Category> categories) {

        categoryList.addAll(categories);
    }

}
