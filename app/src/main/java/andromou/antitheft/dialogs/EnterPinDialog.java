package andromou.antitheft.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import andromou.antitheft.R;
import andromou.antitheft.background.PocketService;
import andromou.antitheft.databinding.DialogEnterPinBinding;

public class EnterPinDialog implements OnDialogShow{
    private static final int VOLUME = 2;

    @Override
    public void onShow(Context context) {
        DialogEnterPinBinding binding = DialogEnterPinBinding.inflate(((Activity)context).getLayoutInflater());
        SharedPreferences sharedpreferences = context.getSharedPreferences(PASSWORD_KEY_PREFERENCES, Context.MODE_PRIVATE);
        final String password = sharedpreferences.getString(PASSWORD, "");
        Dialog mPinDialog = new Dialog(context);
        mPinDialog.setContentView(binding.getRoot());
        mPinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPinDialog.setCancelable(false);
        binding.btnDismiss.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        final MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.psiren);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, VOLUME, 0);
        mPlayer.start();
        mPlayer.setLooping(true);
        mPinDialog.show();
        binding.etEnterPin.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String pin = binding.etEnterPin.getText().toString();
                if (pin.equals(password)) {
                    mPlayer.stop();
                    binding.btnDismiss.setVisibility(View.VISIBLE);
                    context.stopService(new Intent(context, PocketService.class));
                    handled = true;

               } else {
                    binding.etEnterPin.getText().clear();
                    binding.etEnterPin.setError(context.getString(R.string.wrong_pin));
                    binding.etEnterPin.requestFocus();
                }
            }
            return handled;
        });
        binding.btnDismiss.setOnClickListener(view -> mPinDialog.dismiss());

    }
}
