package org.md2k.mcerebrum.sample;

import android.graphics.Paint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.md2k.library.phonesensor.MCSensorManager;
import org.md2k.library.phonesensor.PermissionCallback;
import org.md2k.library.phonesensor.SensorType;
import org.md2k.library.phonesensor.sensor.Comparison;
import org.md2k.library.phonesensor.sensor.EventListener;
import org.md2k.library.phonesensor.sensor.ISensor;
import org.md2k.library.phonesensor.sensor.accelerometer.MCAccelerometer;
import org.md2k.library.phonesensor.sensor.accelerometer_linear.MCAccelerometerLinear;
import org.md2k.library.phonesensor.sensor.accelerometer_uncalibrated.MCAccelerometerUncalibrated;
import org.md2k.library.phonesensor.sensor.activity_type.MCActivityType;
import org.md2k.library.phonesensor.sensor.activity_type.MCActivityTypeData;
import org.md2k.library.phonesensor.sensor.air_pressure.MCAirPressure;
import org.md2k.library.phonesensor.sensor.ambient_light.MCAmbientLight;
import org.md2k.library.phonesensor.sensor.ambient_temperature.MCAmbientTemperature;
import org.md2k.library.phonesensor.sensor.battery.MCBattery;
import org.md2k.library.phonesensor.sensor.battery_details.MCBatteryDetails;
import org.md2k.library.phonesensor.sensor.battery_details.MCBatteryDetailsSample;
import org.md2k.library.phonesensor.sensor.bluetooth_status.MCBluetoothStatus;
import org.md2k.library.phonesensor.sensor.gps_status.MCGPSStatus;
import org.md2k.library.phonesensor.sensor.gravity.MCGravity;
import org.md2k.library.phonesensor.sensor.gyroscope.MCGyroscope;
import org.md2k.library.phonesensor.sensor.gyroscope_uncalibrated.MCGyroscopeUncalibrated;
import org.md2k.library.phonesensor.sensor.location.MCLocation;
import org.md2k.library.phonesensor.sensor.location.MCLocationData;
import org.md2k.library.phonesensor.sensor.magnetometer.MCMagnetometer;
import org.md2k.library.phonesensor.sensor.magnetometer_uncalibrated.MCMagnetometerUncalibrated;
import org.md2k.library.phonesensor.sensor.proximity.MCProximity;
import org.md2k.library.phonesensor.sensor.relative_humidity.MCRelativeHumidity;
import org.md2k.library.phonesensor.sensor.rotation_vector.MCRotationVector;
import org.md2k.library.phonesensor.sensor.significant_motion.MCSignificantMotion;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MCSensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=MCSensorManager.getInstance(this);
        setAccelerometer(findViewById( R.id.accelerometer));
        setAccelerometerLinear(findViewById( R.id.accelerometer_linear));
        setAccelerometerUncalibrated(findViewById( R.id.accelerometer_uncalibrated));
        setGyroscope(findViewById( R.id.gyroscope));
        setGyroscopeUncalibrated(findViewById( R.id.gyroscope_uncalibrated));
        setGravity(findViewById( R.id.gravity));
        setRotationVector(findViewById( R.id.rotation_vector));
        setMagnetometer(findViewById( R.id.magnetometer));
        setMagnetometerUncalibrated(findViewById( R.id.magnetometer_uncalibrated));
        setProximity(findViewById( R.id.proximity));
        setSignificantMotion(findViewById( R.id.significant_motion));
        setActivityType(findViewById( R.id.activity_type));
        setAmbientLight(findViewById( R.id.ambient_light));
        setAirPressure(findViewById(R.id.air_pressure));
        setRelativeHumidity(findViewById(R.id.humidity));
        setAmbientTemperature(findViewById( R.id.ambient_temperature));
        setGPSStatus(findViewById(R.id.gps_status));
        setBluetoothStatus(findViewById(R.id.bluetooth_status));
        setLocation(findViewById(R.id.location));
        setBattery(findViewById(R.id.battery));
        setBatteryDetails(findViewById(R.id.battery_details));

//        setWeather(findViewById( R.id.weather));
    }
    void setUI(final View view, final ISensor iSensor, final String title, final String settings, final EventListener eventListener){
//        ((TextView)view.findViewById(R.id.textView_title)).setText(title+(!iSensor.isSupported()?"\n(Not Supported)":""));
        ((TextView)view.findViewById(R.id.textView_title)).setText(title);
        if(!iSensor.isSupported())
            ((TextView)view.findViewById(R.id.textView_title)).setPaintFlags(((TextView)view.findViewById(R.id.textView_title)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView)view.findViewById(R.id.textView_settings)).setText(settings);
        //Set switch button
        view.findViewById(R.id.switch_startstop).setEnabled(iSensor.isSupported() && iSensor.hasPermission() );
        ((Switch)view.findViewById(R.id.switch_startstop)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    iSensor.start(eventListener);
                }else iSensor.stop(eventListener);
            }
        });

        view.findViewById(R.id.button_permission).setEnabled(iSensor.isSupported() && !iSensor.hasPermission());
        view.findViewById(R.id.button_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view1) {
                iSensor.getPermission(MainActivity.this, new PermissionCallback() {
                    @Override
                    public void onSuccess() {
                        view.findViewById(R.id.button_permission).setEnabled(iSensor.isSupported() && !iSensor.hasPermission());
                        view.findViewById(R.id.switch_startstop).setEnabled(iSensor.isSupported() && iSensor.hasPermission() );
                    }

                    @Override
                    public void onDenied() {

                    }
                });
            }
        });
        view.findViewById(R.id.button_enable).setVisibility(View.INVISIBLE);
/*
        view.findViewById(R.id.button_enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                iSensor.getEnable(MainActivity.this, new EnableCallback() {
                    @Override
                    public void onSuccess() {
                        setUI(view, iSensor, title, settings, eventListener);
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });

*/
    }
    private double getFrequency(long startTimestamp, int sampleNo){
        return (sampleNo*1000.0)/(System.currentTimeMillis()-startTimestamp);
    }
    void setAccelerometer(final View view){
        final MCAccelerometer sensor = sensorManager.getSensor(SensorType.ACCELEROMETER);
        sensor.setReadFrequency(50,TimeUnit.SECONDS);
        sensor.setWriteFixed(50, TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "freq=%.1f\ntotal=%d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[MCAccelerometer.X], s[MCAccelerometer.Y], s[MCAccelerometer.Z]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Accelerometer", "(read = 50 hz, write = 50 hz)", eventListener);
   }
    void setAccelerometerLinear(final View view){
        final MCAccelerometerLinear sensor = sensorManager.getSensor(SensorType.ACCELEROMETER_LINEAR);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteOnChange(Comparison.sampleDiff(0.5));
//        sensor.setWriteOnChange(Comparison.sampleDiff(0.1));
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[MCAccelerometerLinear.X], s[MCAccelerometerLinear.Y], s[MCAccelerometerLinear.Z]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Accelerometer (Linear)", "(read = 6 hz, writeOnChange = 0.5)", eventListener);
    }
    void setAccelerometerUncalibrated(final View view){
        final MCAccelerometerUncalibrated sensor = sensorManager.getSensor(SensorType.ACCELEROMETER_UNCALIBRATED);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f, x_bias=%.4f, y_bias=%.4f, z_bias=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[MCAccelerometerUncalibrated.X], s[MCAccelerometerUncalibrated.Y], s[MCAccelerometerUncalibrated.Z],s[MCAccelerometerUncalibrated.BIAS_X],s[MCAccelerometerUncalibrated.BIAS_Y],s[MCAccelerometerUncalibrated.BIAS_Z]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Accelerometer (Uncalibrated)", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setGyroscope(final View view){
        final MCGyroscope sensor = sensorManager.getSensor(SensorType.GYROSCOPE);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Gyroscope", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setGyroscopeUncalibrated(final View view){
        final MCGyroscopeUncalibrated sensor = sensorManager.getSensor(SensorType.GYROSCOPE_UNCALIBRATED);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f, x_bias=%.4f, y_bias=%.4f, z_bias=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2],s[3],s[4],s[5]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Gyroscope (Uncalibrated)", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setGravity(final View view){
        final MCGravity sensor = sensorManager.getSensor(SensorType.GRAVITY);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Gravity", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setRotationVector(final View view){
        final MCRotationVector sensor = sensorManager.getSensor(SensorType.ROTATION_VECTOR);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Rotation Vector", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setMagnetometer(final View view){
        final MCMagnetometer sensor = sensorManager.getSensor(SensorType.MAGNETOMETER);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Magnetometer", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setMagnetometerUncalibrated(final View view){
        final MCMagnetometerUncalibrated sensor = sensorManager.getSensor(SensorType.MAGNETOMETER_UNCALIBRATED);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteFixed(6,TimeUnit.SECONDS);
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: x=%.4f, y=%.4f, z=%.4f, x_bias=%.4f, y_bias=%.4f, z_bias=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0], s[1], s[2],s[3],s[4],s[5]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Magnetometer (Uncalibrated)", "(read = 6 hz, write = 6 hz)", eventListener);
    }
    void setSignificantMotion(final View view){
        final MCSignificantMotion sensor = sensorManager.getSensor(SensorType.SIGNIFICANT_MOTION);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: motion=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Significant Motion", "(read_as_received, write_as_received)", eventListener);
    }
    void setProximity(final View view){
        final MCProximity sensor = sensorManager.getSensor(SensorType.PROXIMITY);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteOnChange(Comparison.notEqual());
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: distance=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Proximity", "(read = 6 hz, writeOnChange = sample_not_equal)", eventListener);
    }
    void setActivityType(final View view){
        final MCActivityType sensor = sensorManager.getSensor(SensorType.ACTIVITY_TYPE);
        sensor.setReadFrequency(1,TimeUnit.MILLISECONDS);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                MCActivityTypeData data = (MCActivityTypeData) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\n", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo());
                sampleStr+="most_probable_activity="+data.getMostProbableActivity().name()+"\n";
                sampleStr+="confidence:\n";
                for(int i = 0; i< MCActivityTypeData.Activity.values().length; i++){
                    sampleStr+= MCActivityTypeData.Activity.values()[i].name()+" = \t"+data.getConfidence(MCActivityTypeData.Activity.values()[i])+"\n";
                }
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Activity Type", "(read = 1000 hz, write_as_received)", eventListener);
    }

    void setAmbientLight(final View view){
        final MCAmbientLight sensor = sensorManager.getSensor(SensorType.AMBIENT_LIGHT);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: light=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Ambient Light", "(read = 6 hz, writeAsReceived)", eventListener);
    }
    void setAmbientTemperature(final View view){
        final MCAmbientTemperature sensor = sensorManager.getSensor(SensorType.AMBIENT_TEMPERATURE);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: temperature=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);
            }
        };

        setUI(view, sensor,"Ambient Temperature", "(read = 6 hz, writeAsReceived)", eventListener);
    }
    void setAirPressure(final View view){
        final MCAirPressure sensor = sensorManager.getSensor(SensorType.AIR_PRESSURE);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteOnChange(Comparison.sampleDiff(1.0));
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: air pressure=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);
            }
        };

        setUI(view, sensor,"Air Pressure", "(read = 6 hz, writeOnChange: diff>=1)", eventListener);
    }
    void setRelativeHumidity(final View view){
        final MCRelativeHumidity sensor = sensorManager.getSensor(SensorType.RELATIVE_HUMIDITY);
        sensor.setReadFrequency(6,TimeUnit.SECONDS);
        sensor.setWriteOnChange(Comparison.sampleDiff(1.0));
        final EventListener eventListener=new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double[] s = (double[]) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: air pressure=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
                t.setText(sampleStr);
            }
        };

        setUI(view, sensor,"Relative Humidity", "(read = 6 hz, writeOnChange: diff>=1)", eventListener);
    }
/*
    void setWeather(final View view){
        final MCWeather sensor = sensorManager.getSensor(SensorType.WEATHER);
        sensor.setReadFrequency(1,TimeUnit.MINUTES);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                WeatherData data = (WeatherData) sample;
//                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nsample: distance=%.4f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), s[0]);
//                t.setText(sampleStr);

            }
        };

        setUI(view, sensor,"Weather", "(read = 1 sample/min, write_as_received)", eventListener);

    }
*/
    void setGPSStatus(final View view){
        final MCGPSStatus sensor = sensorManager.getSensor(SensorType.GPS_STATUS);
        sensor.setWriteOnChange();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                boolean data = (boolean) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nGPS Status: %s", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), String.valueOf(data));
                t.setText(sampleStr);

            }
        };
        setUI(view, sensor,"GPS Status", "(read = on_change, write_as_received)", eventListener);
        Button b = view.findViewById(R.id.button_enable);
        b.setVisibility(View.VISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensor.turnOn();
            }
        });
    }
    void setBluetoothStatus(final View view){
        final MCBluetoothStatus sensor = sensorManager.getSensor(SensorType.BLUETOOTH_STATUS);
        sensor.setWriteOnChange();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                boolean data = (boolean) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nBluetooth Status: %s", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), String.valueOf(data));
                t.setText(sampleStr);

            }
        };
        setUI(view, sensor,"Bluetooth Status", "(read = on_change, write_as_received)", eventListener);
        Button b = view.findViewById(R.id.button_enable);
        if(sensor.isOn()) b.setText("Disable");
        else b.setText("Enable");
        b.setVisibility(View.VISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = view.findViewById(R.id.button_enable);
                if(sensor.isOn()){
                    sensor.turnOff();
                    b.setText("Enable");
                }else {
                    sensor.turnOn();
                    b.setText("Disable");
                }
            }
        });
    }

    void setLocation(final View view){
        final MCLocation sensor = sensorManager.getSensor(SensorType.LOCATION);
        sensor.setReadFrequency(1,TimeUnit.SECONDS);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                MCLocationData data = (MCLocationData) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\n", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo());
                t.setText(sampleStr);

            }
        };
        setUI(view, sensor,"Location", "(read = 1 sample/min, write_as_received)", eventListener);
        sensor.getLastKnownLocation(new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                Log.d("abc","abc");
            }
        });
    }
    void setBattery(final View view){
        final MCBattery sensor = sensorManager.getSensor(SensorType.BATTERY);
        sensor.setWriteAsReceived();
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                double data = (double) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\nbattery_level=%.1f", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo(), data);
                t.setText(sampleStr);

            }
        };
        setUI(view, sensor,"Battery", "(read = on_change, write_as_received)", eventListener);
    }
    void setBatteryDetails(final View view){
        final MCBatteryDetails sensor = sensorManager.getSensor(SensorType.BATTERY_DETAILS);
        sensor.setWriteAsReceived();
        final EventListener eventListener=new EventListener() {
            @Override
            public void  onChange(long timestamp, Object sample) {
                TextView t = view.findViewById(R.id.textView_sample);
                MCBatteryDetailsSample data = (MCBatteryDetailsSample) sample;
                String sampleStr=String.format(Locale.getDefault(), "frequency = %.1f\ntotal sample = %d\n" +
                        "battery_level=%d\nbattery_scale=%d\nbattery_voltage=%d\nbattery_temperature=%d\n" +
                                "plugged=%s\nhealth=%s\nstatus=%s", getFrequency(sensor.getStartTimestamp(), sensor.getSampleNo()), sensor.getSampleNo()
                        , data.getLevel(),data.getScale(), data.getVoltage(), data.getTemperature(),
                data.getPlugged(),data.getHealth(), data.getStatus());
                t.setText(sampleStr);

            }
        };
        setUI(view, sensor,"Battery (Details)", "(read = on_change, write_as_received)", eventListener);
    }

}
