package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.workmanager.NewProductNotifWorker;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductNotificationSettingDialogFragment extends DialogFragment {

    @BindView(R.id.notif_radio_group)
    RadioGroup radioGroupNotif;
    @BindView(R.id.save_notif_state)
    Button saveNotifState;
    @BindView(R.id.custom_notif_layout)
    TextInputLayout customNorifLayout;
    @BindView(R.id.custom_notif_edt)
    TextInputEditText customNotifEdt;

    public NewProductNotificationSettingDialogFragment() {
    }
    private NavController navController;
    public static final int _15_MINUTE = 15;
    public static final int _3_HOURS = 3;
    public static final int _5_HOURS = 5;
    public static final int _8_HOURS = 8;
    public static final int _12_HOURS = 12;
    public static final int HOURS = 0;
    public static final int MINUTE = 1;
    public static final boolean ACTIVE = true;
    public static final boolean DE_ACTIVATE = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_product_notification_setting_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = NavHostFragment.findNavController(this);
        getWorkManagerTime();
        radioGroupNotif.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i==R.id.custom_notif){
                customNorifLayout.setVisibility(View.VISIBLE);
            }else {
                customNorifLayout.setVisibility(View.GONE);
            }
        });

        saveNotifState.setOnClickListener(view1 -> {
            switch (radioGroupNotif.getCheckedRadioButtonId()){
                case R.id.turn_off_notif :{
                        enqueWorkManager(0,0,DE_ACTIVATE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.turn_off_notif);
                    break;
                }
                case R.id._15_min_notif:{
                    enqueWorkManager(_15_MINUTE , MINUTE , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id._15_min_notif);
                    break;
                }

                case R.id.every_3_hours_notif:{
                    enqueWorkManager(_3_HOURS , HOURS , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.every_3_hours_notif);
                    break;
                }
                case R.id.every_5_hours_notif:{
                    enqueWorkManager(_5_HOURS , HOURS , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.every_5_hours_notif);
                    break;
                }
                case R.id.every_8_hours_notif:{
                    enqueWorkManager(_8_HOURS , HOURS , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.every_8_hours_notif);
                    break;
                }
                case R.id.every_12_hours_notif:{
                    enqueWorkManager(_12_HOURS , HOURS , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.every_12_hours_notif);
                    break;
                }
                case R.id.custom_notif:{
                    int notifCustomTime = Integer.parseInt(customNotifEdt.getText().toString());
                    enqueWorkManager(notifCustomTime , HOURS , ACTIVE);
                    Pref.saveNotifRadioButtonId(getActivity() , R.id.custom_notif);
                    Pref.saveNotifCustomTime(getActivity() , notifCustomTime);
                    break;
                }
            }
            navController.popBackStack();
        });
    }

    private void getWorkManagerTime() {
        int notifRadioButtonId = Pref.getNotifRadioButton(getActivity());
        if (notifRadioButtonId!=0){
            radioGroupNotif.check(notifRadioButtonId);
            if (notifRadioButtonId==R.id.custom_notif){
                customNorifLayout.setVisibility(View.VISIBLE);
                customNotifEdt.setText(String.valueOf(Pref.getNotifCustomTime(getActivity())));
            }
        }
    }

    private void enqueWorkManager(int time , int timeUnit , boolean isActiveNotif) {
        if (isActiveNotif){
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            PeriodicWorkRequest.Builder newProductWork = new PeriodicWorkRequest.Builder(NewProductNotifWorker.class
                    , time, timeUnit==HOURS?TimeUnit.HOURS:TimeUnit.MINUTES).addTag(Const.PRIODIC_NEW_PRODUCT_TAG)
                    .setConstraints(constraints);
            WorkManager.getInstance(getActivity())
                    .enqueueUniquePeriodicWork(
                    Const.PERIODIC_NEW_PRODUCT_WORK_MANAGER, ExistingPeriodicWorkPolicy.REPLACE, newProductWork.build()
            );
        }else {
            WorkManager.getInstance(getActivity()).cancelAllWork();
        }
    }
}
