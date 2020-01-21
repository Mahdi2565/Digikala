package ir.mahdidev.digikala.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

public class ProductActivity extends AppCompatActivity {

    private MainFragmentViewModel viewModel;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getProductIdFromNotification();
    }

    private void getProductIdFromNotification() {
        if (getIntent().hasExtra(Const.IntentKey.PRODUCT_ID_FORM_NOTIF_TO_PRODUCT_ACTIVITY)){
            productId = getIntent().getIntExtra(Const.IntentKey.PRODUCT_ID_FORM_NOTIF_TO_PRODUCT_ACTIVITY , 0);
            initViewModel();
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        viewModel.setProductId(productId);
        viewModel.loadSingleProduct(productId);
    }

    public static Intent newIntent(Context context ){
        return new Intent(context , ProductActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
