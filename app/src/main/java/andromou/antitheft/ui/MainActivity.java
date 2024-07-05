package andromou.antitheft.ui;


import static andromou.antitheft.background.PocketService.isPinCorrect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import andromou.antitheft.R;
import andromou.antitheft.background.PocketService;
import andromou.antitheft.databinding.ActivityMainBinding;
import andromou.antitheft.dialogs.CountDownTimerDialog;
import andromou.antitheft.dialogs.EnterPinDialog;
import andromou.antitheft.dialogs.ResetPinDialog;
import andromou.antitheft.dialogs.SetPinDialog;
import andromou.antitheft.utils.AppUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


     ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findViewById(R.id.btn_rate).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new SetPinDialog().onShow(this);

         ((Switch)findViewById(R.id.switch_pocket)).setOnCheckedChangeListener((buttonView, isChecked) -> {
             if (isChecked ) {
                 // show count down timer
               new CountDownTimerDialog().onShow(this);
             } else {
                 stopService(new Intent(MainActivity.this, PocketService.class));
                 AppUtils.displayDialog(R.layout.custom_toast_desactivated, R.id.toast_layout_root, R.id.description_red, R.string.motoion_off, MainActivity.this);

             }
         });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("NonConstantResourceId")
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
         super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
             // write code to remove keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }






}

