package andromou.antitheft.dialogs;

import android.content.Context;

public interface OnDialogShow {

    String PASSWORD_KEY_PREFERENCES = "password_preferences";
    String PASSWORD = "pin_code";

    void onShow(Context context);
}
