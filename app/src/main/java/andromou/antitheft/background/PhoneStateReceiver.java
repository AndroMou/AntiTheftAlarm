package andromou.antitheft.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import andromou.antitheft.ui.MainActivity;

public class PhoneStateReceiver extends BroadcastReceiver {

    public static boolean isPhoneRinging;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver is active", Toast.LENGTH_SHORT).show();
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                isPhoneRinging = true;
              } else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                isPhoneRinging = false;
             }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
