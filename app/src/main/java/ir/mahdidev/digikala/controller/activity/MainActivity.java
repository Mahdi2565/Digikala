package ir.mahdidev.digikala.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.fragment.MainFragment;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView navigationToggle;
    private DrawerLayout drawerLayout;
    private MainFragmentViewModel viewModel;
    private TextView basketBadge;
    private ImageView basketImg;

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public int containerId() {
        return R.id.frame_layout;
    }

    @Override
    public String fragmentTag() {
        return Const.MAIN_FRAGMENT_TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewModel();
        initToolbar();
        initNavigation();

    }

    private void initNavigation() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        viewModel.getProductCount().observe(this , integer -> {
            if (integer>0){
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            }else {
                basketBadge.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationToggle = findViewById(R.id.navigation_drawer_toggle);
        basketBadge = findViewById(R.id.basket_badge);
        basketImg   = findViewById(R.id.basket_img);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        navigationToggle.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.END));
        basketImg.setOnClickListener(view -> startActivity(ProductBasketActivity.newIntent(this)));
    }

    public static Intent newIntent(Context context){
        return new Intent(context , MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else{
            super.onBackPressed();
        }
    }
        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.favorite_product_menu :{
                    startActivity(FavoriteProductsActivity.newIntent(MainActivity.this));
                    break;
                }
            }
            drawerLayout.closeDrawers();
        return false;
    }
}
