package com.kas.electricunitxlstodb_20201124;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kas.electricunitxlstodb_20201124.data.PreferencesUtil;
import com.kas.electricunitxlstodb_20201124.databinding.MainActivityBinding;
import com.kas.electricunitxlstodb_20201124.menu.SettingsActivity;
import com.kas.electricunitxlstodb_20201124.viewmodels.PreferencesViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "#_MAIN_ACTIVITY";
    private MainActivityBinding mainActivityBinding;
    private PreferencesViewModel preferencesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        preferencesViewModel = new ViewModelProvider(this).get(PreferencesViewModel.class);

        FloatingActionButton fab = mainActivityBinding.floatingActionButton;
        fab.setOnClickListener(view -> {
            Intent detailsUnitIntent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(detailsUnitIntent);
        });

        boolean isEditMode = PreferencesUtil.isEditMode(this.getApplication());
        if (!isEditMode) {
            fab.setVisibility(View.INVISIBLE);
        }

        boolean isThemeDarkMode = PreferencesUtil.isThemeModeDark(this.getApplication());
        if (isThemeDarkMode) {
            preferencesViewModel.setThemeModeDarkOn();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettingsActivity();
                return true;
            /*case R.id.action_search:
                showLoadingDialog();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}