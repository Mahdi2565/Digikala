package ir.mahdidev.digikala.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.util.Const;

public class ProductsListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        navigateToProductListFragment();
    }

    private void navigateToProductListFragment() {
        NavController navController = Navigation.findNavController(this, R.id.products_list_nav_graph);
        ListProductData listProductData = (ListProductData) getIntent().getSerializableExtra(Const.IntentKey.PRODUCT_LIST_DATA_INTENT_KEY);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY , listProductData);
        navController.setGraph(R.navigation.products_list_nav_graph, bundle);
    }

    public static Intent newIntent(Context context , ListProductData listProductData){
        return new Intent(context , ProductsListActivity.class).putExtra(Const.IntentKey.PRODUCT_LIST_DATA_INTENT_KEY
         , listProductData);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
