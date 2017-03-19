package deputy.ttb.com.deputy.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by naveenu on 15/03/2017.
 */

public class Utility {

    private final static String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssz"; //
    public static final String  GREEN_DAO_DB_NAME   =   "myShifts.db";

    /**
     *  Show Error message window
     *
     * @param context
     * @param message
     * @param okListener
     */
    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     *
     * @param context
     */
    public static void openDeviceAppSettingsScreen(final Context context) {
        if (context == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    /**
     *
     * Get date in ISO8601 format
     * @return
     */
    public static String getISO8601Date(){
        String today    =   null;
        SimpleDateFormat dateformat = new SimpleDateFormat(ISO_8601_DATE_FORMAT, Locale.getDefault());
        today   =   dateformat.format(new Date());
        return  today;
    }


    /**
     *
     * Set date in ISO8601 format
     * @return
     */
    public static String getDate(String strDate){

        DateFormat sourceFormat =   new SimpleDateFormat(ISO_8601_DATE_FORMAT);
        try {
            Date date   =   sourceFormat.parse(strDate);
            DateFormat destFormat   =   new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            return destFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }




}
