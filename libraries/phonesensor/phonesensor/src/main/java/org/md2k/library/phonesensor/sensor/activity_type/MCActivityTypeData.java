package org.md2k.library.phonesensor.sensor.activity_type;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.HashMap;

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
public class MCActivityTypeData {
    private Activity mostProbableActivity;
    private HashMap<Activity, Integer> confidences;
    public enum Activity{
        IN_VEHICLE,
        ON_BICYCLE,
        ON_FOOT,
        RUNNING,
        STILL,
        TILTING,
        WALKING,
        UNKNOWN
    }
    MCActivityTypeData(ActivityRecognitionResult result){
        mostProbableActivity=getActivity(result.getMostProbableActivity().getType());
        confidences = new HashMap<>();
        confidences.put(Activity.IN_VEHICLE, 0);
        confidences.put(Activity.ON_BICYCLE, 0);
        confidences.put(Activity.ON_FOOT, 0);
        confidences.put(Activity.RUNNING, 0);
        confidences.put(Activity.STILL, 0);
        confidences.put(Activity.TILTING, 0);
        confidences.put(Activity.WALKING, 0);
        confidences.put(Activity.UNKNOWN, 0);
        for(int i=0;i<result.getProbableActivities().size();i++){
            DetectedActivity r = result.getProbableActivities().get(i);
            confidences.put(getActivity(r.getType()), r.getConfidence());
        }
    }
    private Activity getActivity(int value){
        switch (value){
            case DetectedActivity.IN_VEHICLE:return Activity.IN_VEHICLE;
            case DetectedActivity.ON_BICYCLE:return Activity.ON_BICYCLE;
            case DetectedActivity.ON_FOOT:return Activity.ON_FOOT;
            case DetectedActivity.RUNNING:return Activity.RUNNING;
            case DetectedActivity.STILL:return Activity.STILL;
            case DetectedActivity.TILTING:return Activity.TILTING;
            case DetectedActivity.WALKING:return Activity.WALKING;
            case DetectedActivity.UNKNOWN:
            default:return Activity.UNKNOWN;
        }
    }

    public Activity getMostProbableActivity() {
        return mostProbableActivity;
    }
    public int getConfidence(Activity activity){
        return confidences.get(activity);
    }

    public HashMap<Activity, Integer> getConfidences() {
        return confidences;
    }
}
