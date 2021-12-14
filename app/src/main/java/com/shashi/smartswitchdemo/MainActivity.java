package com.shashi.smartswitchdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivAdd = findViewById(R.id.iv_add_device);
        ivAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
        });
    }
}