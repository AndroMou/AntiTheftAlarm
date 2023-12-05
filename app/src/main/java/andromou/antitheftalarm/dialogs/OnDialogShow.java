package andromou.antitheftalarm.dialogs;

import android.content.Context;

public interface OnDialogShow {

    String PASSWORD_KEY_PREFERENCES = "Password_Prefs" ;
    String PASS_WORD = "password";

    void onShow(Context context);
}
