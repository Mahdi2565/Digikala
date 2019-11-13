package ir.mahdidev.digikala.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.util.Const;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }
    public static Intent newIntent(Context context ){
        return new Intent(context , ProductActivity.class);
    }
}
