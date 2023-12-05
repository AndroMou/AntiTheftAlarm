package andromou.antitheftalarm.ui;


import static andromou.antitheftalarm.background.PocketService.isPinCorrect;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import andromou.antitheftalarm.R;
import andromou.antitheftalarm.background.PhoneStateReceiver;
import andromou.antitheftalarm.background.PocketService;
import andromou.antitheftalarm.databinding.ActivityMainBinding;
import andromou.antitheftalarm.dialogs.CountDownTimerDialog;
import andromou.antitheftalarm.dialogs.EnterPinDialog;
import andromou.antitheftalarm.dialogs.ResetPinDialog;
import andromou.antitheftalarm.dialogs.SetPinDialog;
import andromou.antitheftalarm.utils.AppUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    public static boolean isSwitchChecked, isPlugged , isUnregisterable;


    public static boolean isPhoneRining;

    public static boolean isPassWordSaved = false;

    public static boolean isPocketSwitchSet = false;
    public static ChargerReceiver chargerReceiver;

    ActivityMainBinding binding;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findViewById(R.id.btn_rate).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);



        new SetPinDialog().onShow(this);

        ((Switch)findViewById(R.id.switch_charger)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            onSwitch(isChecked);
        });


     //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
         ((Switch)findViewById(R.id.switch_pocket)).setOnCheckedChangeListener((buttonView, isChecked) -> {

             if (isChecked && isPassWordSaved) {
                 // show count down timer
               new CountDownTimerDialog().onShow(this);
             } else {
                 stopService(new Intent(MainActivity.this, PocketService.class));
                 AppUtils.displayDialog(R.layout.custom_toast_desactivated, R.id.toast_layout_root, R.id.description_red, R.string.motoion_off, MainActivity.this);
                 (findViewById(R.id.layout_pocket)).setBackgroundColor(AppUtils.getColorWrapper(MainActivity.this, R.color.colorAccent));
                 isPocketSwitchSet = false;
             }
         });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }




    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_rate:
                AppUtils.onRate(getBaseContext());
                break;
            case R.id.btn_share:
                AppUtils.onShare(MainActivity.this);
                break;
            case R.id.btn_setting:
                new ResetPinDialog().onShow(MainActivity.this);
                 break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isPinCorrect){
            new EnterPinDialog().onShow(this);
            isPinCorrect = false;
        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
             // write code to remove keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }


    public static class ChargerReceiver extends BroadcastReceiver{

        public static ChargerReceiver mInstance;
        private Context context;

        public ChargerReceiver(Context context) {
            this.context = context;
        }

        public static ChargerReceiver getInstance(Context context){
            if (mInstance == null){
                mInstance = new ChargerReceiver(context);
            }
            return mInstance;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                isPlugged = true;
                if (isUnregisterable) {
                     context.unregisterReceiver(chargerReceiver);
                }
            } else if (plugged == 0) {
                isPlugged = false;
                if (isSwitchChecked) {
                    new EnterPinDialog().onShow(context);
                    ((Switch) ((Activity)context).findViewById(R.id.switch_charger)).setChecked(false);
                    context.unregisterReceiver(chargerReceiver);
                }
            }
        }
    }


    public void onSwitch(boolean isChecked) {
        new Thread(() -> {
            try {
                chargerReceiver = ChargerReceiver.getInstance(MainActivity.this);
                registerReceiver(chargerReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }  finally {
         runOnUiThread(() -> {
             if ( isChecked){
                 if ( isPlugged){
                     isSwitchChecked = true;
                     isUnregisterable = false;
                     (findViewById(R.id.ly_chargeur)).setBackgroundResource(R.color.colorPrimaryDark);
                 } else {
                     AppUtils.displayDialog(R.layout.custom_toast_desactivated, R.id.toast_layout_root,
                             R.id.description_red, R.string.connect_to_charge,MainActivity.this);
                     ((Switch)findViewById(R.id.switch_charger)).setChecked(false);
                     isUnregisterable = true;
                     isSwitchChecked = false;
                 }
             } else {
                 isUnregisterable = true;
                 isSwitchChecked = false;
                 (findViewById(R.id.ly_chargeur)).setBackgroundColor(AppUtils.getColorWrapper(MainActivity.this, R.color.colorAccent));

             }
         });
        }
        }).start();




    }




}

