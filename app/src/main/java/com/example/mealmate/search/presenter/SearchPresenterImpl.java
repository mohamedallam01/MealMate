package com.example.mealmate.search.presenter;

import android.util.Log;

import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.search.view.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenterImpl implements SearchPresenter {

    private static final String TAG = "SearchPresenterImpl";
    private SearchView searchView;
    private MealsRepository mealsRepository;
    private final PublishSubject<String> querySubject = PublishSubject.create();

    public SearchPresenterImpl(MealsRepository mealsRepository, SearchView searchView) {
        this.mealsRepository = mealsRepository;
        this.searchView = searchView;
        observeQueryChanges();
    }

    private void observeQueryChanges() {
        querySubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(this::search);
    }

    @Override
    public void getSearchMeals(String key) {
        Log.i(TAG, "getSearchMeals: ");
        mealsRepository.getSearchByName(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailedMeals -> {
                    searchView.showSearchResults(detailedMeals.getDetailedMeals());
                }
                        ,
                        error -> {
                            searchView.showError("Failed to fetch Areas");
                            Log.i(TAG, "Error fetching Areas: " + error);
                        }
                );
    }

    @Override
    public void search(String query) {
        getSearchMeals(query);
    }
}
