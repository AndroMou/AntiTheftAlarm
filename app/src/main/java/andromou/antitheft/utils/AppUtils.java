package andromou.antitheft.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import andromou.antitheft.R;

public class AppUtils {

    /**
     * Shares the app using a sharing intent.
     */
    public static void onShare(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Anti theft alarm 2021 on Google Play \n\n" + "https://play.google.com/store/apps/dev?id=";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    /**
     * Opens the app's Play Store page for rating.
     */
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

    /**
     * Starts a new component (activity).
     */
    public static void onStartComponent(Context context, Class<?> mClass) {
        context.startActivity(new Intent(context, mClass));
    }

    /**
     * Retrieves a color resource.
     */
    public static int getColorWrapper(Context context, int id) {
        return context.getColor(id);
    }

    /**
     * Displays a custom Toast message.
     */
    public static void displayDialog(int layoutResId, int layoutRootId, int descriptionId, int descriptionStringId, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(layoutResId, ((Activity) context).findViewById(layoutRootId));
        TextView description = layout.findViewById(descriptionId);
        description.setText(descriptionStringId);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 120);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
