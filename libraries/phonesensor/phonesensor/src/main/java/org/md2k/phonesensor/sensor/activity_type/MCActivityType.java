package org.md2k.phonesensor.sensor.activity_type;

import android.content.Context;
import android.content.pm.PackageManager;

import com.google.android.gms.location.ActivityRecognitionResult;

import org.md2k.phonesensor.sensor.Comparison;
import org.md2k.phonesensor.sensor.Frequency;
import org.md2k.phonesensor.SensorType;
import org.md2k.phonesensor.sensor.MCAbstractSensor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation2.ReactiveLocationProviderConfiguration;

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class MCActivityType extends MCAbstractSensor {
    private Frequency readFrequency;

    private Disposable activityDisposable;


    public void setWriteAsReceived() {
        super.setWriteAsReceived();
    }

    public MCActivityType(Context context) {
        super(context, SensorType.ACTIVITY_TYPE,null);
        setReadFrequency(1, TimeUnit.SECONDS);
        setWriteAsReceived();
    }

    public void setReadFrequency(double frequency, TimeUnit timeUnit) {
        this.readFrequency = new Frequency(frequency, timeUnit);
    }
    public void setWriteFixed(double writeFrequency, TimeUnit timeUnit){
        super.setWriteFixed(writeFrequency, timeUnit);
    }
    public void setWriteOnChange(Comparison comparison){
        super.setWriteOnChange(comparison);
    }

    @Override
    protected void startSensing() {
        final ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context, ReactiveLocationProviderConfiguration
                .builder()
                .setRetryOnConnectionSuspended(true)
                .build()
        );
        int detectIntervalMillis;
        if(readFrequency.getFrequency()==0)
            detectIntervalMillis = 1;
        else detectIntervalMillis = (int) (1000.0/readFrequency.as(TimeUnit.SECONDS).getFrequency());

        activityDisposable = locationProvider
                .getDetectedActivity(detectIntervalMillis)
                .observeOn(AndroidSchedulers.mainThread())

                /*.map(new Function<ActivityRecognitionResult, double[]>() {
                    @Override
                    public ActivityRecognitionResult apply(ActivityRecognitionResult activityRecognitionResult) throws Exception {
                        activityRecognitionResult.getMostProbableActivity()
                        return new double[]{activityRecognitionResult.getMostProbableActivity().getType()};
                    }
                })*/
                .subscribe(new Consumer<ActivityRecognitionResult>() {
                    @Override
                    public void accept(ActivityRecognitionResult activityRecognitionResult) {
                        MCActivityTypeData activityTypeData = new MCActivityTypeData(activityRecognitionResult);
                        setSample(activityRecognitionResult.getTime(), activityTypeData);
                    }
                });
    }

    @Override
    protected void stopSensing() {
        if (activityDisposable != null && !activityDisposable.isDisposed()) {
            activityDisposable.dispose();
        }
    }


    @Override
    public boolean isSupported() {
        PackageManager packMan = context.getPackageManager();
        return packMan.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    @Override
    public HashMap<String, String> getSensorInfo() {
        return new HashMap<>();
    }

    @Override
    protected boolean isChanged(Object prevSample, Object curSample, Comparison comparison) {
        MCActivityTypeData p = (MCActivityTypeData) prevSample;
        MCActivityTypeData c = (MCActivityTypeData) curSample;
        switch(comparison.getComparisonType()){
            case SAMPLE_DIFFERENCE:
                return Math.abs(p.getConfidence(p.getMostProbableActivity()) - c.getConfidence(c.getMostProbableActivity())) > comparison.getValue();
            case NOT_EQUAL:
                return p.getMostProbableActivity() != c.getMostProbableActivity();
                default:
                    return true;
        }
    }

}
