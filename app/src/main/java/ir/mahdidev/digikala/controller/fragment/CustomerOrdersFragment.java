package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahdidev.digikala.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrdersFragment extends Fragment {


    public CustomerOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_orders, container, false);
    }

}
