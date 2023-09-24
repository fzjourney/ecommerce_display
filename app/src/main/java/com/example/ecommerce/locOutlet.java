package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

public class locOutlet extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        geocoder = new Geocoder(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_outlet);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_loc);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    Intent homeIntent = new Intent(locOutlet.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.menu_history) {
                    Intent historyIntent = new Intent(locOutlet.this, History.class);
                    startActivity(historyIntent);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng outletA = new LatLng(-6.915845285206341, 107.58613438261567);
        LatLng outletB = new LatLng(-6.916319633556482, 107.59370478791487);
        LatLng outletC = new LatLng(-6.912804868628957, 107.59174141113208);

        myMap.addMarker(new MarkerOptions().position(outletA).title("Outlet A").snippet(getMarkerSnippet(outletA)));
        myMap.addMarker(new MarkerOptions().position(outletB).title("Outlet B").snippet(getMarkerSnippet(outletB)));
        myMap.addMarker(new MarkerOptions().position(outletC).title("Outlet C").snippet(getMarkerSnippet(outletC)));

        float zoomLevel = 15.0f; // This goes up to 21
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(outletB, zoomLevel));
    }

    private String getMarkerSnippet(LatLng latLng) {
        StringBuilder snippetBuilder = new StringBuilder();

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                snippetBuilder.append(address.getAddressLine(0)).append("\n");
                snippetBuilder.append(address.getLocality()).append(", ");
                snippetBuilder.append(address.getAdminArea()).append(" ");
                snippetBuilder.append(address.getPostalCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return snippetBuilder.toString();
    }
}