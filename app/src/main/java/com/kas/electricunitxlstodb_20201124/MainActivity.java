package com.kas.electricunitxlstodb_20201124;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.databinding.MainActivityBinding;
import com.kas.electricunitxlstodb_20201124.menu.SettingsActivity;
import com.kas.electricunitxlstodb_20201124.ui.ListFragment;
import com.kas.electricunitxlstodb_20201124.ui.MainViewModel;
import com.kas.electricunitxlstodb_20201124.ui.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "# MainActivity";
    private MainActivityBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        AppDatabase database = AppDatabase.getInstance(this);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_list_container, ListFragment.newInstance(1))
                    .add(R.id.search_container, SearchFragment.newInstance())
                    .commitNow();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.search_container, SearchFragment.newInstance())
                    .commitNow();
        }*/

        FloatingActionButton fab = mainActivityBinding.floatingActionButton;
        fab.setOnClickListener(view -> {
            Intent detailsUnitIntent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(detailsUnitIntent);
        });
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

    private void showLoadingDialog() {
    }


}