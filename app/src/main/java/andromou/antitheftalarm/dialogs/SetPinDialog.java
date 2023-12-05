package andromou.antitheftalarm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.Toast;

import andromou.antitheftalarm.R;
import andromou.antitheftalarm.databinding.DialogSetPinBinding;
import andromou.antitheftalarm.ui.MainActivity;

public class SetPinDialog implements OnDialogShow{
    @Override
    public void onShow(Context context) {
        DialogSetPinBinding binding = DialogSetPinBinding.inflate(((Activity)context).getLayoutInflater());
        SharedPreferences sharedpreferences = context.getSharedPreferences(PASSWORD_KEY_PREFERENCES, Context.MODE_PRIVATE);
        String confirmPin1 = sharedpreferences.getString(PASS_WORD,"");
        if(!(confirmPin1.length() > 0)){
            Dialog mPinDialog = new Dialog(context);
            mPinDialog.setContentView(binding.getRoot());
            mPinDialog.setCancelable(false);
            mPinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPinDialog.show();
            binding.btnSetPin.setOnClickListener(view -> {
                String pin = binding.etSetPin.getText().toString();
                String confirmPin = binding.etConfirmPin.getText().toString();
                if (TextUtils.isEmpty(pin) || pin.length() < 4) {
                    binding.etSetPin.setError(context.getString(R.string.error_minimum_digit));
                    binding.etSetPin.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(confirmPin) || confirmPin.length() < 4) {
                    binding.etConfirmPin.setError(context.getString(R.string.error_minimum_digit));
                    binding.etConfirmPin.requestFocus();
                    return;
                }
                if (pin.equals(confirmPin)) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(PASS_WORD, confirmPin);
                    editor.commit();
                    Toast.makeText(context, R.string.password_set, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.password_nt_match, Toast.LENGTH_SHORT).show();
                }
                mPinDialog.dismiss();
            });
        } else {
            MainActivity.isPassWordSaved = true;
        }
    }
}
