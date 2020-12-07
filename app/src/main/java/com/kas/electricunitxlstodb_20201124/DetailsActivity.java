package com.kas.electricunitxlstodb_20201124;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kas.electricunitxlstodb_20201124.ui.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_unit_container, DetailsFragment.newInstance())
                    .commitNow();
        }
    }
}