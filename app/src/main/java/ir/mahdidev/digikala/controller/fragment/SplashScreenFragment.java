package ir.mahdidev.digikala.controller.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

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
    private MainFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        requestProductsList();
        ProductAsyncTask productAsyncTask = new ProductAsyncTask();
        productAsyncTask.execute();

    }

    private void requestProductsList() {
        viewModel.getEspecialProducts();
        viewModel.getAmazingSuggestionListLiveData(1);
        viewModel.getMostRatingListLiveData(1);
        viewModel.getMostVisitingListLiveData(1);
        viewModel.getMostNewestListLiveData(1);

    }

    private class ProductAsyncTask extends AsyncTask<Void, Void, MutableLiveData<List<WebserviceCategoryModel>>> {


        @Override
        protected MutableLiveData<List<WebserviceCategoryModel>> doInBackground(Void... voids) {

            try {
                return Repository.getInstance().loadCategoryList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<WebserviceCategoryModel>> listHashMap) {
            super.onPostExecute(listHashMap);
            try {
                if (listHashMap.getValue()!=null){
                    startActivity(MainActivity.newIntent(getActivity()));
                    getActivity().finish();
                }
            }catch (Exception e){
            }
        }
    }
}
