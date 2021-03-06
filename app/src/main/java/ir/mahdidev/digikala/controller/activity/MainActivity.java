package ir.mahdidev.digikala.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.fragment.MainFragment;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView navigationToggle;
    private DrawerLayout drawerLayout;
    private MainFragmentViewModel viewModel;
    private TextView basketBadge;
    private ImageView basketImg;
    private TextView basketNavigationViewBadge;
    private ImageView searchImg;
    private ImageView loginRegisterImage;
    private TextView nameCustomer;
    private ImageView personImage;
    private RelativeLayout personInfo;

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
        searchImgFunction();
        personImageFunction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCustomerLoggedIn();
    }

    private void checkCustomerLoggedIn() {
        WebServiceCustomerModel webServiceCustomerModel = Pref.getCustomerModelFromPref(MainActivity.this);
        if (webServiceCustomerModel == null){
            personImage.setVisibility(View.GONE);
        }else {
            personImage.setVisibility(View.VISIBLE);
        }
        headerNavigationView();
    }

    private void personImageFunction() {
        personImage.setOnClickListener(view -> startActivity(CustomerInfoActivity.newIntent(MainActivity.this)));
    }

    private void headerNavigationView() {
        View headerLayout = navigationView.getHeaderView(0);
        loginRegisterImage = headerLayout.findViewById(R.id.loginRegisterImg);
        nameCustomer = headerLayout.findViewById(R.id.name_customer_txt);
        loginRegisterImage.setOnClickListener(view ->
                startActivity(LoginRegisterActivity.newIntent(MainActivity.this)));
        WebServiceCustomerModel webServiceCustomerModel = Pref.getCustomerModelFromPref(this);
        personInfo = headerLayout.findViewById(R.id.person_info);

        if (webServiceCustomerModel!=null){
            String nameTxt = webServiceCustomerModel.getFirstName() + " " + webServiceCustomerModel.getLastName();
            nameCustomer.setText(nameTxt);
            loginRegisterImage.setVisibility(View.GONE);
            personInfo.setVisibility(View.VISIBLE);
        }else {
            loginRegisterImage.setVisibility(View.VISIBLE);
            personInfo.setVisibility(View.GONE);
        }

        personInfo.setOnClickListener(view -> {
              startActivity(CustomerInfoActivity.newIntent(MainActivity.this));
        });

    }

    private void searchImgFunction() {
        searchImg.setOnClickListener(view ->
                startActivity(SearchActivity.newIntent(MainActivity.this)));
    }

    private void initBasketBadge(int basketBadge) {
        basketNavigationViewBadge=(TextView) (navigationView.getMenu().findItem(R.id.basket_menu).getActionView());
        basketNavigationViewBadge.setGravity(Gravity.CENTER_VERTICAL);
        basketNavigationViewBadge.setTextColor(getResources().getColor(R.color.black));
        basketNavigationViewBadge.setTextSize(16);
        basketNavigationViewBadge.setText(MyApplication.getInstance().getPersianNumber(basketBadge));
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
                initBasketBadge(integer);
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
        searchImg   = findViewById(R.id.search_img);
        personImage = findViewById(R.id.person_image);
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
                case R.id.basket_menu : {
                    startActivity(ProductBasketActivity.newIntent(MainActivity.this));
                    break;
                }
                case R.id.category_menu:{
                    startActivity(CategoryListActivity.newIntent(MainActivity.this , 0));
                    break;
                }
                case R.id.most_newest_menu:{
                    startActivity(ProductsListActivity.newIntent(MainActivity.this,new ListProductData("جدیدترین محصولات" ,
                            Const.OrderTag.MOST_NEWEST_PRODUCT , "desc" , "")));
                    break;
                }
                case R.id.most_rating_menu:{
                    startActivity(ProductsListActivity.newIntent(MainActivity.this,new ListProductData("پرامتیازترین محصولات" ,
                            Const.OrderTag.MOST_RATING_PRODUCT , "desc" , "")));
                    break;
                }
                case R.id.most_visited_menu:{
                    startActivity(ProductsListActivity.newIntent(MainActivity.this,new ListProductData("پربازدیدترین محصولات" ,
                            Const.OrderTag.MOST_VISITING_PRODUCT , "desc" , "")));
                    break;
                }
                case R.id.setting_menu :{
                    startActivity(SettingActivity.newIntent(MainActivity.this));
                    break;
                }
            }
            drawerLayout.closeDrawers();
        return false;
    }
}
