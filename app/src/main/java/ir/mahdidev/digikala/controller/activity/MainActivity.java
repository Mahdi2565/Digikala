package ir.mahdidev.digikala.controller.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.fragment.MainFragment;
import ir.mahdidev.digikala.util.Const;

public class MainActivity extends SingleFragmentActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView navigationToggle;
    private DrawerLayout drawerLayout;

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
        initToolbar();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationToggle = findViewById(R.id.navigation_drawer_toggle);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        navigationToggle.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.END);

        });
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
}
