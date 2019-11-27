package ir.mahdidev.digikala.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.util.Const;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        navigateToCategoryListFragment();
    }

    private void navigateToCategoryListFragment() {
        NavController navController = Navigation.findNavController(this ,R.id.category_nav_host);
        Bundle bundle = new Bundle();
        int categotyPosition = getIntent().getIntExtra(Const.IntentKey.CATEGORY_POSITION_INTENT_KEY,0);
        bundle.putInt(Const.BundleKey.CATEGORY_POSITION_BUNDLE_KEY,categotyPosition);
        navController.setGraph(R.navigation.category_nav_graph , bundle);
    }

    public static Intent newIntent(Context context , int categoryPosition){
        return new Intent(context , CategoryListActivity.class)
                .putExtra(Const.IntentKey.CATEGORY_POSITION_INTENT_KEY , categoryPosition);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
