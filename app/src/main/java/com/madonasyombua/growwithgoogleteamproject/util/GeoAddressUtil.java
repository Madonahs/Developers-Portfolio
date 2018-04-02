/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.util;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by madona on 2/27/2018.
 */


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

    /**
     *
     * @param context
     * @param name
     * @return
     */
    public static LatLng getLatLng(Context context, String name){
        Geocoder gc = new Geocoder(context);
        LatLng pos = null;

        try{
            List<Address> addr = gc.getFromLocationName(name,1);
            double lat = addr.get(0).getLatitude();
            double lng = addr.get(0).getLongitude();

            pos = new LatLng(lat,lng);
        }catch (Exception ignore){}

        return pos;
    }
}
