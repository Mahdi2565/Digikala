package ir.mahdidev.digikala.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.networkmodel.Repository;

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

    private class ProductAsyncTask extends AsyncTask<Void, Void, HashMap<String, List>>{


        @Override
        protected HashMap<String, List> doInBackground(Void... voids) {
            return Repository.getInstance().getProductsAndCategoryList();
        }

        @Override
        protected void onPostExecute(HashMap<String, List> listHashMap) {
            super.onPostExecute(listHashMap);

            Repository.getInstance().setProductsList(listHashMap);
            if (listHashMap.isEmpty())return;
            startActivity(MainActivity.newIntent(getActivity()));
            getActivity().finish();
        }
    }
}
