package org.md2k.phonesensor.sensor.sms_log;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import org.md2k.phonesensor.sensor.Comparison;
import org.md2k.phonesensor.SensorType;
import org.md2k.phonesensor.sensor.MCAbstractSensor;

import java.security.MessageDigest;
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
public class MCSmsLog extends MCAbstractSensor {
    private SMSOutgoing smsOutgoing;
    private SMSIncoming smsIncoming;

    protected MCSmsLog(Context context, SensorType sensorType) {
        super(context, sensorType, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS});
    }

    @Override
    protected void startSensing() {
        smsOutgoing = new SMSOutgoing(new Handler(Looper.getMainLooper()), context);
        smsIncoming = new SMSIncoming();
        context.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, smsOutgoing);
        context.registerReceiver(smsIncoming, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
    }

    @Override
    protected void stopSensing() {
        context.getContentResolver().unregisterContentObserver(smsOutgoing);
        context.unregisterReceiver(smsIncoming);
    }

    @Override
    public HashMap<String, String> getSensorInfo() {
        return null;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    protected boolean isChanged(Object prevSample, Object curSample, Comparison comparison) {
        return false;
    }

    private class SMSOutgoing extends ContentObserver {

        private Context context;

        private final Uri SMS_STATUS_URI = Uri.parse("content://sms");

        SMSOutgoing(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Cursor sms_sent_cursor = context.getContentResolver().query(SMS_STATUS_URI, null, null, null, null);
            if (sms_sent_cursor == null) return;
            sms_sent_cursor.moveToNext();
            if (sms_sent_cursor.getInt(sms_sent_cursor.getColumnIndex("type")) == 2) {
                String phoneNumber = sha256(sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("address")));
                long timestamp = sms_sent_cursor.getLong(sms_sent_cursor.getColumnIndexOrThrow("date"));
                int length = (sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("body"))).length();
                SMSSample smsSample = new SMSSample(SMSSample.SMSType.OUTGOING, timestamp, phoneNumber, length);
                setSample(System.currentTimeMillis(), smsSample);
            }

        }//fn onChange

    }

    public class SMSIncoming extends BroadcastReceiver {

        private static final String TAG = "SmsBroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                String smsSender = "";
                String smsBody = "";
                long timestamp=0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        smsSender = smsMessage.getDisplayOriginatingAddress();
                        timestamp = smsMessage.getTimestampMillis();
                        smsBody += smsMessage.getMessageBody();
                    }
                } else {
                    Bundle smsBundle = intent.getExtras();
                    if (smsBundle != null) {
                        Object[] pdus = (Object[]) smsBundle.get("pdus");
                        if (pdus == null) {
                            // Display some error to the user
                            Log.e(TAG, "SmsBundle had no pdus key");
                            return;
                        }
                        SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < messages.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            smsBody += messages[i].getMessageBody();
                            messages[i].getTimestampMillis();
                        }
                        smsSender = messages[0].getOriginatingAddress();
                    }
                }
                SMSSample smsSample = new SMSSample(SMSSample.SMSType.INCOMING, timestamp, smsSender, smsBody.length());
                setSample(System.currentTimeMillis(), smsSample);
            }
        }
    }
    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            return "";
        }
    }

}
