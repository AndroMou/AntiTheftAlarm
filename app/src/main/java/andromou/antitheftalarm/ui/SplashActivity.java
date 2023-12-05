package andromou.antitheftalarm.ui;


import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import andromou.antitheftalarm.R;


public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        new Thread(new Run()).start();
    }

    class Run implements Runnable{
        @Override
        public void run() {
            try {
                sleep(2000);
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                Intent intent = new Intent(
                        SplashActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }
    }

}

