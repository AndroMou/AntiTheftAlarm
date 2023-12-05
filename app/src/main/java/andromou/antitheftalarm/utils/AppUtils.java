package andromou.antitheftalarm.utils;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import andromou.antitheftalarm.R;


public class AppUtils {







    public static void onShare(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Anti theft alarm 2021 on Google Play \n\n" + "https://play.google.com/store/apps/dev?id=";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void onRate(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static void onStartComponent(Context context, Class mClass) {
        context.startActivity(new Intent(context, mClass));
    }

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    public static void compart( Context context){
        if(!context.getPackageName().equals("com.signalp.antitheftAlarm2021")){
            String error = null;
            error.getBytes();
        }
    }

    public static void  displayDialog(int ly, int ly_root, int desc, int stDEsc, Context context){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View layoutD = inflater.inflate(ly, ((Activity)context).findViewById(ly_root));
        TextView description;
        description = layoutD.findViewById(desc);
        description.setText(stDEsc);
// Create and show the Toast object
        Toast toastActivate;
        toastActivate = new Toast(context);
        toastActivate.setGravity(Gravity.CENTER, 0, 0);
        toastActivate.setDuration(Toast.LENGTH_LONG);
        toastActivate.setView(layoutD);
        toastActivate.show();
    }



}
