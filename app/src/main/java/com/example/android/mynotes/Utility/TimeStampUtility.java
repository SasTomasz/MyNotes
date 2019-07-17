package com.example.android.mynotes.Utility;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtility {
    public static final String TAG = "TimeStampUtility";

    public static String getTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            return dateFormat.format(new Date());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getMonthName(String month){
        switch (month){
            case "01" : return "Jan";

            case "02" : return "Feb";

            case "03" : return "Mar";

            case "04" : return "Apr";

            case "05" : return "May";

            case "06" : return "Jun";

            case "07" : return "Jul";

            case "08" : return "Aug";

            case "09" : return "Sep";

            case "10" : return "Oct";

            case "11" : return "Nov";

            case "12" : return "Dec";

            default : return "Error";
        }
    }
}
