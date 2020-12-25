package com.kas.electricunitxlstodb_20201124;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kas.electricunitxlstodb_20201124.databinding.MainActivityBinding;
import com.kas.electricunitxlstodb_20201124.menu.SettingsActivity;
import com.kas.electricunitxlstodb_20201124.ui.PreferencesViewModel;
import com.kas.electricunitxlstodb_20201124.ui.SharedViewModel;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "#_MAIN_ACTIVITY";
    private MainActivityBinding mainActivityBinding;
    private SharedViewModel sharedViewModel;
    private PreferencesViewModel preferencesViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        
        preferencesViewModel = new ViewModelProvider(this).get(PreferencesViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent detailsUnitIntent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(detailsUnitIntent);
        });
        
        boolean isEditMode = preferencesViewModel.isEditModeState();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //boolean isEditMode = sharedPreferences.getBoolean(getString(R.string.pref_edit_mode), false);
  
        if (!isEditMode) {
            fab.setVisibility(View.INVISIBLE);
        }
        
        //boolean isThemeDarkMode = sharedPreferences.getBoolean(getString(R.string.pref_theme_dark), false);
        boolean isThemeDarkMode = preferencesViewModel.isThemeModeState();
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