package com.kas.electricunitxlstodb_20201124;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.databinding.MainActivityBinding;
import com.kas.electricunitxlstodb_20201124.menu.SettingsActivity;
import com.kas.electricunitxlstodb_20201124.ui.ListFragment;
import com.kas.electricunitxlstodb_20201124.ui.ListViewModel;
import com.kas.electricunitxlstodb_20201124.ui.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "# MainActivity";
    private MainActivityBinding mainActivityBinding;
    private AppDatabase database;
    private MainViewModel mainViewModel;

    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        database = AppDatabase.getInstance(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainActivityBinding = MainActivityBinding.inflate(getLayoutInflater());

        search = mainActivityBinding.search;
        search.setOnEditorActionListener((textView, i, keyEvent) -> {

            return true;
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filter = charSequence.toString();
                Log.d(LOG_TAG, "Search onChanged = "+ filter);
                mainViewModel.setFilter(filter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container, ListFragment.newInstance(1))
                    .commitNow();
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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

    private void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}