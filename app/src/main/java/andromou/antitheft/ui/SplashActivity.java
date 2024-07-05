package andromou.antitheft.ui;


import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import andromou.antitheft.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        new Thread(() -> {
            try {
                sleep(2000);
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }).start();
    }


}

