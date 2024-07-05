package andromou.antitheft.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.Toast;

import andromou.antitheft.R;
import andromou.antitheft.databinding.DialogSetPinBinding;

public class SetPinDialog implements OnDialogShow {



    @Override
    public void onShow(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PASSWORD_KEY_PREFERENCES, Context.MODE_PRIVATE);
        String confirmPin = sharedPreferences.getString(PASSWORD, "");

        if (TextUtils.isEmpty(confirmPin)) {
            DialogSetPinBinding binding = DialogSetPinBinding.inflate(((Activity) context).getLayoutInflater());
            Dialog mPinDialog = new Dialog(context);
            mPinDialog.setContentView(binding.getRoot());
            mPinDialog.setCancelable(false);
            mPinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPinDialog.show();

            binding.btnSetPin.setOnClickListener(view -> {
                String pin = binding.etSetPin.getText().toString();
                String confirmPinInput = binding.etConfirmPin.getText().toString();

                if (TextUtils.isEmpty(pin) || pin.length() < 4) {
                    binding.etSetPin.setError(context.getString(R.string.error_minimum_digit));
                    binding.etSetPin.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(confirmPinInput) || confirmPinInput.length() < 4) {
                    binding.etConfirmPin.setError(context.getString(R.string.error_minimum_digit));
                    binding.etConfirmPin.requestFocus();
                    return;
                }

                if (pin.equals(confirmPinInput)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PASSWORD, confirmPinInput);
                    editor.apply();
                    Toast.makeText(context, R.string.password_set, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.password_not_match, Toast.LENGTH_SHORT).show();
                }

                mPinDialog.dismiss();
            });
        }
    }
}
