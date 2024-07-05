package andromou.antitheft.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.CountDownTimer;

import andromou.antitheft.R;
import andromou.antitheft.background.PocketService;
import andromou.antitheft.databinding.DialogTimerBinding;
import andromou.antitheft.ui.MainActivity;
import andromou.antitheft.utils.AppUtils;

public class CountDownTimerDialog implements OnDialogShow {

    @Override
    public void onShow(Context context) {
        DialogTimerBinding timerBinding = DialogTimerBinding.inflate(((Activity) context).getLayoutInflater());
        Dialog timerDialog = new Dialog(context);
        timerDialog.setContentView(timerBinding.getRoot());
        timerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timerBinding.tvDialog.setText(context.getString(R.string.will_be_activated));
        timerBinding.tvCounterDialog.setText("3");

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerBinding.tvCounterDialog.setText("" + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerDialog.dismiss();
                startPocketService(context);
            }
        }.start();

        timerDialog.show();
        timerDialog.setCancelable(false);
    }

    private void startPocketService(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, PocketService.class));
        } else {
            AppUtils.onStartComponent(context, PocketService.class);
        }
    }
}
