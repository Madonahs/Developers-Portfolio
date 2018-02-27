package com.madonasyombua.growwithgoogleteamproject.util;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.util.List;

/**
 * Created by madona on 2/27/2018.
 */

// we will need this for location
public class GeoAddressUtil {

    public static List<Address> getAddress(double latitude, double longitude, Activity activity) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(activity);
            if (latitude != 0 || longitude != 0) {
                addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                return addresses;

            } else {
                Toast.makeText(activity, "latitude and longitude are null",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
