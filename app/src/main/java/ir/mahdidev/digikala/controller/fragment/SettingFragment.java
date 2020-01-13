package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;


public class SettingFragment extends Fragment {

    @BindView(R.id.setting_linear)
    LinearLayout settingLinear;

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }

    private NavController navController;
    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(view);
        newProductNotifSetting();
    }

    private void newProductNotifSetting() {
        settingLinear.setOnClickListener(view -> {
            navController.navigate(R.id.action_settingFragment_to_newProductNotificationSettingDialogFragment);
        });
    }
}
