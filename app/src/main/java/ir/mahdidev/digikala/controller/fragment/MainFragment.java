package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.MainRecyclerViewAdapter;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.main_vertical_recyclerView)
    RecyclerView verticalRecyclerView;

    private MainFragmentViewModel viewModel;
    private MainRecyclerViewAdapter adapter;
    public MainFragment() {
        // Required empty public constructor
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

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        viewModel.getProductAndCategoryList().observe(this, this::initRecyclerView);
    }

    private void initRecyclerView(HashMap<String , List> productAndCategoryList) {
        if (adapter == null){
            adapter = new MainRecyclerViewAdapter(productAndCategoryList , getActivity());
            verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            verticalRecyclerView.setAdapter(adapter);
        }else {
            adapter.setProductAndCategoryHashMap(productAndCategoryList);
        }
    }
}
