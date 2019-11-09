package ir.mahdidev.digikala.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {

    @BindView(R.id.progress_bar)
    LottieAnimationView progressBar;
    public SplashScreenFragment() {
        // Required empty public constructor
    }

    public static SplashScreenFragment newInstance() {
        Bundle args = new Bundle();
        SplashScreenFragment fragment = new SplashScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
            ProductAsyncTask productAsyncTask = new ProductAsyncTask();
            productAsyncTask.execute();
    }

    private class ProductAsyncTask extends AsyncTask<Void , Void , HashMap<String , List>> {

        @Override
        protected HashMap<String , List> doInBackground(Void... voids) {
            HashMap<String , List> productAndCategoryListHashMap
                    = new HashMap<>();
            try {
                List<WebserviceProductModel> mostVisitedProductList =
                        RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                                .getAllSortedProduct("popularity").execute().body();

                List<WebserviceProductModel> mostRatingProductList =
                        RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                                .getAllSortedProduct("rating").execute().body();

                List<WebserviceProductModel> mostNewestProductList =
                        RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                                .getAllSortedProduct("date").execute().body();
                List<WebserviceCategoryModel> allCategories = RetrofitConfig.getRetrofit()
                        .create(RetrofitApi.class).getAllCategories().execute().body();

                productAndCategoryListHashMap.put(Const.KeyList.MOST_VISITING_LIST, mostVisitedProductList);
                productAndCategoryListHashMap.put(Const.KeyList.MOST_RATING_LIST , mostRatingProductList);
                productAndCategoryListHashMap.put(Const.KeyList.MOST_NEWEST_LIST , mostNewestProductList);
                productAndCategoryListHashMap.put(Const.KeyList.CATEGORY_LIST ,   allCategories);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return productAndCategoryListHashMap;
        }
        @Override
        protected void onPostExecute(HashMap<String , List> productsAndCategorySortHashMap) {
            super.onPostExecute(productsAndCategorySortHashMap);
            Repository.getInstance().setProductsList(productsAndCategorySortHashMap);
            startActivity(MainActivity.newIntent(getActivity()));
            getActivity().finish();
        }
    }
}
