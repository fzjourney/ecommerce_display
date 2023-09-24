package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.MenuItem;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("HP Envy Laptop","Tanggal Pembelian : 10 Januari 2023","Rp 15.000.000",R.drawable.produk1));
        items.add(new Item("Oppo Handphone","Tanggal Pembelian : 16 Maret 2023","Rp 1.225.000",R.drawable.produk2));
        items.add(new Item("Oculus Quest VR","Tanggal Pembelian : 21 Agustus 2023","Rp 3.200.000",R.drawable.produk3));
        items.add(new Item("Old Puma Melo","Tanggal Pembelian : 11 Oktober 2023","Rp 2.500.000",R.drawable.produk4));
        items.add(new Item("Noir N1 Mechanical Keyboard","Tanggal Pembelian : 2 Desember 2023","Rp 900.000",R.drawable.produk5));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    Intent homeIntent = new Intent(History.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.menu_loc) {
                    Intent locIntent = new Intent(History.this, locOutlet.class);
                    startActivity(locIntent);
                    return true;
                }

                return false;
            }
        });
    }
}
