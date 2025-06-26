package com.example.videoadslibrary.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationHelper {
    public interface LocationCallback {
        void onLocationRetrieved(double latitude, double longitude);
        void onLocationFailed();
    }

    public static void fetchUserLocation(Context context, LocationCallback callback) {
        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            callback.onLocationFailed();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onLocationRetrieved(location.getLatitude(), location.getLongitude());
                    } else {
                        callback.onLocationFailed();
                    }
                })
                .addOnFailureListener(e -> callback.onLocationFailed());
    }
}
