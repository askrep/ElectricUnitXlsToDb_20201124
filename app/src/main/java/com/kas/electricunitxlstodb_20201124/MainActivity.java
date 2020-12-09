package com.kas.electricunitxlstodb_20201124;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.menu.SettingsActivity;
import com.kas.electricunitxlstodb_20201124.ui.ListViewModel;
import com.kas.electricunitxlstodb_20201124.ui.MainViewModel;
import com.kas.electricunitxlstodb_20201124.ui.SearchViewModel;

public class MainActivity extends AppCompatActivity {
    
    private static final String LOG_TAG = "#_MAIN_ACTIVITY";
    private MainViewModel mainViewModel;
    private SearchViewModel searchViewModel;
    private ListViewModel listViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        AppDatabase database = AppDatabase.getInstance(this);
        
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        
        searchViewModel.getFilter().observe(this, filter -> {
            mainViewModel.getFilter().setValue(filter);
            
            Log.d(LOG_TAG, "MainActivity Set unit list filter");
        });
        
        //mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent detailsUnitIntent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(detailsUnitIntent);
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettingsActivity();
                return true;
            case R.id.action_load_table:
                showLoadingDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    private void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    
    private void showLoadingDialog() {
    }
    
}