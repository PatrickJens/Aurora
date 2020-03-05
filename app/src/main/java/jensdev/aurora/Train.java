package jensdev.aurora;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Train extends AppCompatActivity {


    ArrayList<Double> list_ax = new ArrayList<>();
    ArrayList<Double> list_ay = new ArrayList<>();
    ArrayList<Double> list_az = new ArrayList<>();
    ArrayList<Double> list_gx = new ArrayList<>();
    ArrayList<Double> list_gy = new ArrayList<>();
    ArrayList<Double> list_gz = new ArrayList<>();
    private SensorManager mSensorManager;
    int i = 0 ;

    private final SensorEventListener aSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                double accel_x  = event.values[0];
                list_ax.add(accel_x);
                double accel_y  = event.values[1];
                list_ay.add(accel_y);
                double accel_z  = event.values[2];
                list_az.add(accel_z);
            }
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
            {
                double gyro_x  = event.values[0];
                list_gx.add(gyro_x);
                double gyro_y  = event.values[1];
                list_gy.add(gyro_y);
                double gyro_z  = event.values[2];
                list_gz.add(gyro_z);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        Button saveTrainingButton = findViewById(R.id.save_train_button);
        final TextView axsize = findViewById(R.id.ax_size_textview);

        //Rather than interact with sensor hardware directly, you work with sensor objects that represent that hardware
        //Use sensor service called Sensor Manager Service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Sensor class includes a set of constants that describe which type of hardware sensor is being represented by a particular Sensor object
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            //mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Toast.makeText(getApplicationContext(), "Default Accelerometer Selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Accelerometer", Toast.LENGTH_SHORT).show();
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            Toast.makeText(getApplicationContext(), "Default Gyroscope Selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Gyroscope", Toast.LENGTH_SHORT).show();
        }


        //Create file, and create directory if directory does not exist
        File myfolder = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "DATA");
        if (!myfolder.exists()) {
            myfolder.mkdirs();
        }
        String blank = "";
        //Create text files
        final File axfile = new File(myfolder, "ax.txt");
        final File ayfile = new File(myfolder, "ay.txt");
        final File azfile = new File(myfolder, "az.txt");
        final File gxfile = new File(myfolder, "gx.txt");
        final File gyfile = new File(myfolder, "gy.txt");
        final File gzfile = new File(myfolder, "gz.txt");

        //Clear text files, append mode false means overwrite
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(axfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

            fos = new FileOutputStream(ayfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

            fos = new FileOutputStream(azfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

            fos = new FileOutputStream(gxfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

            fos = new FileOutputStream(gyfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

            fos = new FileOutputStream(gzfile, false);
            fos.write(blank.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //When save button presses, stop collecting data and paste accel values into respective text files
        saveTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                {
                    String delimiter = "\n";
                    mSensorManager.unregisterListener(aSensorEventListener);
                    axsize.setText(String.format("%d", list_ax.size())); //Printing to screen

                    //Create new fileoutputstream with append mode on
                    FileOutputStream fos = null;
                    //Ax file write
                    try {
                            fos = new FileOutputStream(axfile, true);
                            for(i = 0 ; i < list_ax.size() ; i ++ )
                            {
                                String number = list_ax.get(i)+"";
                                fos.write(number.getBytes());
                                fos.write(delimiter.getBytes());
                            }
                            fos.flush();
                            fos.close();

                            fos = new FileOutputStream(ayfile, true);
                            for(i = 0 ; i < list_ay.size() ; i ++ )
                            {
                                String number = list_ay.get(i)+"";
                                fos.write(number.getBytes());
                                fos.write(delimiter.getBytes());
                            }
                            fos.flush();
                            fos.close();

                            fos = new FileOutputStream(azfile, true);
                            for(i = 0 ; i < list_az.size() ; i ++ )
                            {
                                String number = list_az.get(i)+"";
                                fos.write(number.getBytes());
                                fos.write(delimiter.getBytes());
                            }
                            fos.flush();
                            fos.close();

                            fos = new FileOutputStream(gxfile, true);
                            for(i = 0 ; i < list_gx.size() ; i ++ )
                            {
                                String number = list_gx.get(i)+"";
                                fos.write(number.getBytes());
                                fos.write(delimiter.getBytes());
                            }
                            fos.flush();
                            fos.close();
                            fos = new FileOutputStream(gyfile, true);

                            for(i = 0 ; i < list_gy.size() ; i ++ )
                            {
                                String number = list_gy.get(i)+"";
                                fos.write(number.getBytes());
                                fos.write(delimiter.getBytes());
                            }
                            fos.flush();
                            fos.close();

                        fos = new FileOutputStream(gzfile, true);
                        for(i = 0 ; i < list_gz.size() ; i ++ )
                        {
                            String number = list_gz.get(i)+"";
                            fos.write(number.getBytes());
                            fos.write(delimiter.getBytes());
                        }
                        fos.flush();
                        fos.close();
                        

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });
   }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(aSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(aSensorEventListener, mGyroscope,SensorManager.SENSOR_DELAY_FASTEST );
    }

    @Override
    protected void onPause(){
        super.onPause();

        mSensorManager.unregisterListener(aSensorEventListener);

//        try {
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
