package andromou.antitheft.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


import andromou.antitheft.ui.MainActivity;

public class PocketService  extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    Notification _notification;
    private static final int SENSOR_SENSITIVITY = 4;
    public static boolean isPinCorrect = false;
    public static int numberOfChanges = 0;


    @Override
    public void onCreate() {
        super.onCreate();
     }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        numberOfChanges = 0;

        if (Build.VERSION.SDK_INT >= 26) {
            String NOTIFICATION_CHANNEL_ID = "Permanence";
            String channelName = "Pocket Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            Notification.Builder notificationBuilder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
            _notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("App is running in background")
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(10101, _notification);
        }

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                numberOfChanges++;
                if (numberOfChanges > 1 && !PhoneStateReceiver.isPhoneRinging){
                    if (event.values[0] > SENSOR_SENSITIVITY ) {
                            isPinCorrect = true;
                            Intent intent = new Intent(this, MainActivity.class);
                            getApplicationContext().startActivity(intent);
                     }
                }
            }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }


}
