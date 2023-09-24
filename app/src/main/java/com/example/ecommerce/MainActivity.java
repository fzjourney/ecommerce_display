package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout bannerContainer;
    private Scroller scroller;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inisial fragment -> homefragment
        navigateToFragment(new HomeFragment());

        searchEditText = findViewById(R.id.searchEditText);
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        bannerContainer = findViewById(R.id.bannerContainer);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchEditText.setTextColor(Color.WHITE);
                } else {
                    searchEditText.setTextColor(Color.parseColor("#4DFFFFFF"));
                }
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String searchText = searchEditText.getText().toString().trim();
                    performSearch(searchText);
                    return true;
                }
                return false;
            }
        });

        scroller = new Scroller(this);
        handler = new Handler();

        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacks(scrollRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(scrollRunnable, 20);
                        break;
                }
                return false;
            }
        });

        // INTENT
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    // Handle home selection
                    return true;
                } else if (itemId == R.id.menu_loc) {
                    // Navigate to locOutlet activity
                    Intent locIntent = new Intent(MainActivity.this, locOutlet.class);
                    startActivity(locIntent);
                    return true;
                } else if (itemId == R.id.menu_history) {
                    // Navigate to History activity
                    Intent historyIntent = new Intent(MainActivity.this, History.class);
                    startActivity(historyIntent);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(scrollRunnable, 20);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(scrollRunnable);
    }

    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            int maxScroll = bannerContainer.getChildAt(0).getWidth() * (bannerContainer.getChildCount() - 1);
            int currentScroll = horizontalScrollView.getScrollX();

            if (currentScroll >= maxScroll) {
                horizontalScrollView.scrollTo(0, 0);
            } else {
                horizontalScrollView.smoothScrollBy(2, 0);
            }

            handler.postDelayed(this, 20);
        }
    };

    private void performSearch(String searchText) {
        // Kalau udah ada database bisa fungsi search nya disini
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, locOutlet.class);
        startActivity(intent);
    }

    public void onHistoryClick(View view) {
        Intent intent = new Intent(MainActivity.this, History.class);
        startActivity(intent);
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
