package andromou.antitheftalarm.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import andromou.antitheftalarm.ui.MainActivity;

public class PhoneStateReceiver extends BroadcastReceiver {


    public Context context;


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Receiver is active", Toast.LENGTH_SHORT).show();

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                MainActivity.isPhoneRining = false;
                intent.putExtra("pin", true);
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent1);
             } else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                MainActivity.isPhoneRining = true;
             }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
