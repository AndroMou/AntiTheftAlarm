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
import andromou.antitheftalarm.databinding.DialogResetPinBinding;

public class ResetPinDialog implements OnDialogShow{


    @Override
    public void onShow(Context context) {
        DialogResetPinBinding binding = DialogResetPinBinding.inflate(((Activity)context).getLayoutInflater());
        SharedPreferences sharedpreferences = context.getSharedPreferences(PASSWORD_KEY_PREFERENCES, Context.MODE_PRIVATE);
        final String password = sharedpreferences.getString(PASS_WORD, "");

        Dialog mPinDialog = new Dialog(context);
        mPinDialog.setContentView(binding.getRoot());


        mPinDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mPinDialog.show();
        binding.btnResetPin.setOnClickListener(v -> {

            String oldPin = binding.etOldPin.getText().toString();
            String newPin = binding.etNewPin.getText().toString();
            String confirmNewPin = binding.etConfirmNewPin.getText().toString();

            if(TextUtils.isEmpty(oldPin) || oldPin.length()<4) {
                binding.etOldPin.setError(context.getString(R.string.error_minimum_digit));
                binding.etOldPin.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(newPin)|| newPin.length()<4) {
                binding.etNewPin.setError(context.getString(R.string.error_minimum_digit));
                binding.etNewPin.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(confirmNewPin) || confirmNewPin.length()<4) {
                binding.etConfirmNewPin.setError(context.getString(R.string.error_minimum_digit));
                binding.etConfirmNewPin.requestFocus();
                return;
            }
            if(oldPin.equals(password)){
                if(newPin.equals(confirmNewPin)){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(PASS_WORD, confirmNewPin);
                    editor.commit();
                    Toast.makeText(context, "Password Reset Successful", Toast.LENGTH_SHORT).show();
                    mPinDialog.dismiss();
                } else {
                    Toast.makeText(context, R.string.password_nt_match, Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.etOldPin.getText().clear();
                binding.etOldPin.setError(context.getString(R.string.wron_pin));
                binding.etOldPin.requestFocus();
            }
        });
    }
}
