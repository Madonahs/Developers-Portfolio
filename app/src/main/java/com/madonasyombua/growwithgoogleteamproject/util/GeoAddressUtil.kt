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
package com.madonasyombua.growwithgoogleteamproject.util

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng

internal object GeoAddressUtil {
    fun getAddress(latitude: Double, longitude: Double, activity: Activity?): List<Address>? {
        return try {
            val addresses: List<Address>
            val geocoder: Geocoder = Geocoder(activity)
            if (latitude != 0.0 || longitude != 0.0) {
                addresses = geocoder.getFromLocation(latitude,
                        longitude, 1)
                addresses
            } else {
                Toast.makeText(activity, "latitude and longitude are null",
                        Toast.LENGTH_LONG).show()
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     *
     * @param context context
     * @param name name
     * @return pos
     */
    fun getLatLng(context: Context?, name: String?): LatLng? {
        val gc = Geocoder(context)
        var pos: LatLng? = null
        try {
            val addr = gc.getFromLocationName(name, 1)
            val lat = addr[0].latitude
            val lng = addr[0].longitude
            pos = LatLng(lat, lng)
        } catch (ignore: Exception) {
        }
        return pos
    }
}