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

import android.content.Context;
import android.content.res.Resources;
import com.madonasyombua.growwithgoogleteamproject.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by madon on 3/10/2018.
 */

public class Date {
    public ArrayList<String> months;

    public static final int YEAR = Calendar.YEAR;
    public static final int MONTH = Calendar.MONTH;
    public static final int DATE = Calendar.DATE;
    public static final int HOUR = Calendar.HOUR_OF_DAY;
    public static final int MINUTE = Calendar.MINUTE;

    DateFormat dateFormat;

    public Date(Context context) {
        months = new ArrayList<>();
        Resources strings = context.getResources();
        months.add(strings.getString(R.string.january));
        months.add(strings.getString(R.string.february));
        months.add(strings.getString(R.string.march));
        months.add(strings.getString(R.string.april));
        months.add(strings.getString(R.string.may));
        months.add(strings.getString(R.string.june));
        months.add(strings.getString(R.string.july));
        months.add(strings.getString(R.string.august));
        months.add(strings.getString(R.string.september));
        months.add(strings.getString(R.string.october));
        months.add(strings.getString(R.string.november));
        months.add(strings.getString(R.string.december));

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Formats a time string.
     *
     * @param dateTime The time string
     * @return A pretty time string
     */
    public String getPrettyTime(String dateTime) {

        StringBuilder time = new StringBuilder();

        Calendar then = Calendar.getInstance();
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        then.setTime(date);

        Calendar now = Calendar.getInstance();

        boolean sameYear = false;
        boolean sameDay = false;

        if (then.get(YEAR) == now.get(YEAR)) {
            sameYear = true;
            if (then.get(MONTH) == now.get(MONTH)) {
                if (then.get(DATE) == now.get(DATE)) {
                    sameDay = true;
                }
            }
        }

        if (!sameDay) {
            if (wasYesterday(now, then)) {
                time.append("Yesterday");
            }
            else {
                time.append(getDate(then));
                if (!sameYear) {
                    time.append(" ");
                    time.append(then.get(YEAR));
                }
            }
        }
        else {
            // Same day
            time.append("Today");
        }
        time.append(", ");

        time.append(getTime(then));

        return time.toString();
    }

    /**
     * Checks if the given date was yesterday.
     *
     * @param now The current date
     * @param then The given date
     * @return Whether the given date was yesterday
     */
    private boolean wasYesterday(Calendar now, Calendar then) {
        Calendar nextDay = then;
        nextDay.add(Calendar.DAY_OF_YEAR, 1);
        if (now.get(YEAR) == nextDay.get(YEAR)) {
            if (now.get(MONTH) == nextDay.get(MONTH)) {
                if (now.get(DATE) == nextDay.get(DATE)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Formats the date.
     *
     * @param cal The calendar object
     * @return A pretty date string
     */
    private String getDate(Calendar cal) {
        StringBuilder date = new StringBuilder();
        date.append(cal.get(DATE)).append(" ");
        date.append(months.get(cal.get(MONTH)));
        return date.toString();
    }

    /**
     * Formats the time.
     *
     * @param cal The calendar object
     * @return A pretty time string
     */
    private String getTime(Calendar cal) {
        StringBuilder time = new StringBuilder();
        time.append(cal.get(HOUR) > 9 ? cal.get(HOUR) : "0" + cal.get(HOUR));
        time.append(":");
        time.append(cal.get(MINUTE) > 9 ? cal.get(MINUTE) : "0" + cal.get(MINUTE));
        return time.toString();
    }

}
