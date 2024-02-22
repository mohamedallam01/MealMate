package com.example.mealmate.details.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.details.presenter.DetailsPresenterImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.weekplan.MealPlanManager;
import com.example.mealmate.weekplan.WeekPlanDialog;
import com.example.mealmate.weekplan.listener.WeekPlanDialogListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import io.reactivex.rxjava3.core.Single;
import timber.log.Timber;


public class Details extends Fragment implements DetailsView {


    private static final String TAG = "Details";
    ImageView ivMealDetails;
    TextView tvMealName;

    DetailsPresenterImpl detailsPresenter;

    String videoId;


    DetailedMeal detailedMeal;

    TextView tvDetails;

    FloatingActionButton fabFavorite;

    OnMealClickListener onMealClickListener;

    private boolean isFavorite = false;


    MealDao mealDao;

    AppDataBase db;

    FloatingActionButton fabPlanMeal;

    MealPlanManager mealPlanManager;
    WeekPlanDialog weekPlanDialog;

    RadioGroup rdDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDataBase.getInstance(getContext());
        mealDao = db.getMealDao();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivMealDetails = view.findViewById(R.id.ivMealDetails);
        tvMealName = view.findViewById(R.id.tvMealName);
        tvDetails = view.findViewById(R.id.tvDetails);
        fabFavorite = view.findViewById(R.id.fabFavorite);
        fabPlanMeal = view.findViewById(R.id.fabWeekPlan);
        rdDays = view.findViewById(R.id.radioGroupDays);

        detailsPresenter = new DetailsPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));
        String mealID = DetailsArgs.fromBundle(getArguments()).getMealId();
        Log.d(TAG, "onViewCreated: " + mealID);
        detailsPresenter.getMealDetails(mealID);

        mealPlanManager = new MealPlanManager();
        weekPlanDialog = new WeekPlanDialog();
    }

    @Override
    public void showDetails(DetailedMeal detailedMeal) {

        this.detailedMeal = detailedMeal;

        if (detailedMeal != null) {
            if (detailedMeal != null) {
                TextView tvMealName = getView().findViewById(R.id.tvMealName);
                ImageView ivMealDetails = getView().findViewById(R.id.ivMealDetails);
                tvMealName.setText(detailedMeal.getStrMeal());

                Glide.with(requireContext()).load(detailedMeal.getStrMealThumb())
                        .apply(new RequestOptions().override(200, 200))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(ivMealDetails);
                isMealInFavorites(detailedMeal.getIdMeal());

            } else {
                Log.e(TAG, "showDetails: Detailed meal is null");
            }
        } else {
            Log.e(TAG, "showDetails: Detailed meal list is null or empty");
        }


        String videoUrl = detailedMeal.getStrYoutube();
        videoId = extractVideoIdFromUrl(videoUrl);

        if (videoId != null) {
            YouTubePlayerView youTubePlayerView = getView().findViewById(R.id.videoViewMeal);
            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0);
                    Log.i(TAG, "onReady: " + videoId);
                }

                @Override
                public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                    super.onError(youTubePlayer, error);
                    Log.e(TAG, "YouTube Player Error: " + error.toString());
                }
            });
        } else {
            Toast.makeText(requireContext(), "The Video is not Available", Toast.LENGTH_SHORT).show();
        }


        String ingredientsText = detailedMeal.strIngredient1 != null ? detailedMeal.strIngredient1 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient2 != null ? detailedMeal.strIngredient2 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient3 != null ? detailedMeal.strIngredient3 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient4 != null ? detailedMeal.strIngredient4 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient5 != null ? detailedMeal.strIngredient5 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient6 != null ? detailedMeal.strIngredient6 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient7 != null ? detailedMeal.strIngredient7 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient8 != null ? detailedMeal.strIngredient8 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient9 != null ? detailedMeal.strIngredient9 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient10 != null ? detailedMeal.strIngredient10 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient11 != null ? detailedMeal.strIngredient11 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient12 != null ? detailedMeal.strIngredient12 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient13 != null ? detailedMeal.strIngredient13 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient14 != null ? detailedMeal.strIngredient14 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient15 != null ? detailedMeal.strIngredient15 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient16 != null ? detailedMeal.strIngredient16 + "\n" : "";

        tvDetails.setText(ingredientsText);

        fabFavorite.setOnClickListener(view -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(requireContext(), "Login first to add to Favorites", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    detailsPresenter.addToFav(detailedMeal);
                    isFavorite = !isFavorite;
                    toggleFavoriteState();
                }
            }).start();
        });

        fabPlanMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeekPlanDialog();
            }
        });
    }

    @Override
    public void showErrorMsg(String error) {

    }

    private String extractVideoIdFromUrl(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("https://www.youtube.com/")) {
            String[] urlParts = youtubeUrl.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandPosition = videoId.indexOf('&');
                if (ampersandPosition != -1) {
                    videoId = videoId.substring(0, ampersandPosition);
                }
            }
        }
        return videoId;
    }


    private void toggleFavoriteState() {
        if (isFavorite) {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }

    private void isMealInFavorites(String id) {
        mealDao.countMealById(id).flatMapSingle(
                count -> {
                    return Single.just(count == 1);
                }
        ).onErrorReturnItem(false).subscribe(
                isExist -> {
                    isFavorite = isExist;
                    detailedMeal.setFavorite(isFavorite);
                    Timber.d("is favorite : " + isFavorite);
                    toggleFavoriteState();
                }
        );
    }

    private void showWeekPlanDialog() {

        WeekPlanDialog.show(requireContext(), new WeekPlanDialogListener() {
            @Override
            public void onMealSelected(String day, String mealId) {

                mealId = detailedMeal.getIdMeal();

                mealPlanManager.saveMeal(requireContext(), day, mealId);
                Log.d(TAG, "onMealSelected: " + day);
            }
        });
    }
}