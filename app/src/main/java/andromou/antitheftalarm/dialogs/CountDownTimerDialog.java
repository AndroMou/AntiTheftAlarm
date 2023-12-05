package andromou.antitheftalarm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.core.app.DialogCompat;
import androidx.fragment.app.DialogFragment;

import andromou.antitheftalarm.R;
import andromou.antitheftalarm.background.PocketService;
import andromou.antitheftalarm.databinding.DialogTimerBinding;
import andromou.antitheftalarm.ui.MainActivity;
import andromou.antitheftalarm.utils.AppUtils;

public class CountDownTimerDialog implements OnDialogShow {


    @Override
    public void onShow(Context context) {
        DialogTimerBinding timerBinding = DialogTimerBinding.inflate(((Activity)context).getLayoutInflater());;
        Dialog timerDialog = new Dialog(context);
        timerDialog.setContentView(timerBinding.getRoot());
        timerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timerBinding.tvDialog.setText(context.getString(R.string.will_be_activ_ated));
        timerBinding.tvCounterDialog.setText("3");
        timerDialog.show();
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerBinding.tvCounterDialog.setText("" + (millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                MainActivity.isPocketSwitchSet = true ;
                timerDialog.hide();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, PocketService.class));
                } else {
                    AppUtils.onStartComponent(context, PocketService.class);
                }
                (((Activity)context).findViewById(R.id.layout_pocket)).setBackgroundResource(R.color.colorPrimaryDark);
            }
        }.start();
        timerDialog.show();
        timerDialog.setCancelable(false);
    }
}
