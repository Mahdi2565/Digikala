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
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
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

    private class ProductAsyncTask extends AsyncTask<Void , Void , HashMap<String , List<WebserviceProductModel>>> {

        @Override
        protected HashMap<String , List<WebserviceProductModel>> doInBackground(Void... voids) {
            HashMap<String , List<WebserviceProductModel>> productListHashMap
                    = new HashMap<>();

            /*
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


                productListHashMap.put("mostVisitedProductList" , mostVisitedProductList);
                productListHashMap.put("mostRatingProductList" , mostRatingProductList);
                productListHashMap.put("mostNewestProductList" , mostNewestProductList);


            } catch (IOException e) {
                e.printStackTrace();
            }

             */
            return productListHashMap;
        }


        @Override
        protected void onPostExecute(HashMap<String , List<WebserviceProductModel>> productsSortHashMap) {
            super.onPostExecute(productsSortHashMap);
            Repository.getInstance().setMostVisitedProduct(productsSortHashMap.get("mostVisitedProductList"));
            Repository.getInstance().setMostRatingProduct(productsSortHashMap.get("mostRatingProductList"));
            Repository.getInstance().setMostNewestProduct(productsSortHashMap.get("mostNewestProductList"));
            startActivity(MainActivity.newIntent(getActivity()));
            getActivity().finish();
        }
    }
}
