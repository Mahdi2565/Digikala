package ir.mahdidev.digikala.controller.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.MainVerticalRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.SliderAdapter;
import ir.mahdidev.digikala.controller.activity.ProductActivity;
import ir.mahdidev.digikala.eventbus.OnProductClickedMessage;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.main_vertical_recyclerView)
    RecyclerView verticalRecyclerView;
    @BindView(R.id.imageSlider)
    SliderView sliderView;

    private SliderAdapter sliderAdapter;
    private MainFragmentViewModel viewModel;
    private MainVerticalRecyclerViewAdapter adapter;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();
    }

    private void initSliderView(HashMap<String, List> listHashMap) {
        sliderAdapter = new SliderAdapter(listHashMap.get(Const.KeyList.CATEGORY_LIST) , getActivity());
        sliderView.setSliderAdapter(sliderAdapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        viewModel.getProductAndCategoryList().observe(this, listHashMap -> {
            if (listHashMap.isEmpty()) return;
            Log.e("TAG4", "HERE UPDATE UI");
            initRecyclerView(listHashMap);
            initSliderView(listHashMap);
        });
    }

    private void initRecyclerView(HashMap<String , List> productAndCategoryList) {
        if (adapter == null){
            adapter = new MainVerticalRecyclerViewAdapter(productAndCategoryList , getActivity());
            verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            verticalRecyclerView.setAdapter(adapter);
        }else {
            adapter.setProductAndCategoryHashMap(productAndCategoryList);
            adapter.notifyDataSetChanged();
        }
    }
    @Subscribe
    public void onProductClicked(OnProductClickedMessage message){
        Repository.getInstance().setProductId(message.getProductId());
        Intent intent = ProductActivity.newIntent(getActivity() , message.getProductId());
        startActivity(intent);
    }
}
