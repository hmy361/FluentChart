package com.tony.stock.fluentchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tony.stock.lib.calculate.Core;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Core core = new Core();
    }
}