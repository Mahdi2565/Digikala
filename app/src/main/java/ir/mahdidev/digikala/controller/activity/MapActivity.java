package ir.mahdidev.digikala.controller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.neshan.core.LngLat;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.ClickData;
import org.neshan.ui.ClickType;
import org.neshan.ui.MapEventListener;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private CustomerViewModel viewModel;

    @OnClick(R.id.back_toolbar)
    void onBackClicked() {
        finish();
    }


    private boolean firstCameraMove = false;
    public static final int INTERVAL_TIME = 1000;
    public static final int FASTEST_INTERVAL_TIME = 1000;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallBack;
    private Location userLocation = null;
    private Location chooseLocation = null;
    private LngLat mLngLat;
    private VectorElementLayer userMarkerLayer;
    private VectorElementLayer userChooseLocationLayer;

    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLocation();
//        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        checkEnableLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        ButterKnife.bind(this);
        initMap();
        saveLocation();
    }

    private void saveLocation() {
        fab.setOnClickListener(view -> {
            if (chooseLocation==null){
                Toast.makeText(MapActivity.this , getString(R.string.choose_location) , Toast.LENGTH_LONG).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                getCustomerAddress(chooseLocation);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        firstCameraMove = false;
        stopRequestLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firstCameraMove = false;
    }

    private void checkPermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        initMap();
                        startRequestLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            String message = "اجازه دسترسی به لوکیشن را ندارید\n\nمیخواهید اجازه دسترسی به لوکیشن را دریافت کنید؟";
                            showFailDialog(message, 0);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void checkEnableLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GpsStatus) {
            showFailDialog("لوکیشن گوشی فعال نیست!\n\nمیخواهید لوکیشن گوشی را فعال کنید ؟", 1);
        }
    }

    private void initMap() {
        userMarkerLayer = NeshanServices.createVectorElementLayer();
        userChooseLocationLayer = NeshanServices.createVectorElementLayer();
        map.getLayers().add(userMarkerLayer);
        map.getLayers().add(userChooseLocationLayer);
        map.getOptions().setRotatable(false);
        map.getLayers().insert(0, NeshanServices.createBaseMap(NeshanMapStyle.NESHAN));
        map.setFocalPointPosition(new LngLat(52.339210, 35.220551), 0);
        map.setZoom(10, 0);
        map.setMapEventListener(new MapEventListener() {
            @Override
            public void onMapClicked(ClickData mapClickInfo) {
                if (mapClickInfo.getClickType() == ClickType.CLICK_TYPE_LONG) {
                    LngLat clickedLocation = mapClickInfo.getClickPos();
                    chooseLocation(clickedLocation);
                    chooseLocation= new Location("");
                    chooseLocation.setLatitude(clickedLocation.getY());
                    chooseLocation.setLongitude(clickedLocation.getX());
                }
            }
        });
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openLocationSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private void showFailDialog(String message, final int id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("بله", (dialog, which) -> {
            if (id == 0) {
                openAppSettings();
            } else {
                openLocationSetting();
            }
        });
        alertDialog.setNegativeButton("خیر", (dialog, which) -> dialog.cancel());
        alertDialog.show();

    }

    private void initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL_TIME);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                userLocation = locationResult.getLastLocation();
                LngLat lngLat = new LngLat(userLocation.getLongitude(), userLocation.getLatitude());
                mLngLat = lngLat;
                addMarker(lngLat);
                if (!firstCameraMove) {
                    map.setFocalPointPosition(mLngLat, 0);
                    map.setZoom(15, 0.5F);
                    firstCameraMove = true;
                }
            }
        };
    }

    private void addMarker(LngLat lngLat) {
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(36f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_red)));
        MarkerStyle markSt = markStCr.buildStyle();
        org.neshan.vectorelements.Marker marker = new org.neshan.vectorelements.Marker(lngLat, markSt);
        userMarkerLayer.clear();
        userMarkerLayer.add(marker);
    }

    private void chooseLocation(LngLat lngLat) {
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(36f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_blue)));
        MarkerStyle markSt = markStCr.buildStyle();
        org.neshan.vectorelements.Marker marker = new org.neshan.vectorelements.Marker(lngLat, markSt);
        userChooseLocationLayer.clear();
        userChooseLocationLayer.add(marker);
    }

    private void startRequestLocation() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallBack, Looper.myLooper());
    }

    private void stopRequestLocation() {
        mFusedLocationClient.removeLocationUpdates(locationCallBack);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    private void getCustomerAddress(Location location) {
        viewModel.loadCustomerAddress(String.valueOf(location.getLatitude()) ,
                String.valueOf(location.getLongitude())).observe(this , webServiceAddress -> {
                        progressBar.setVisibility(View.GONE);
                        finish();
        });

    }
}
