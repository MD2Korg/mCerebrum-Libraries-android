package org.md2k.mcerebrum.library.samsungwatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

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
public class MCWatch {
    private boolean mIsBound = false;
    private ConsumerService mConsumerService = null;
    private static final int CONNECTED = 0;
    private static final int DISCONNECTED = 2;
    int connection = DISCONNECTED;
    private Context context;


    MCWatch(Context context) {
        this.context = context;
    }

    private void connect() {
        mIsBound = context.bindService(new Intent(context, ConsumerService.class), mConnection, Context.BIND_AUTO_CREATE);

    }

    private void disconnect() {
        try {
            mConsumerService.closeConnection();
        } catch (Exception ignored) {
        }
        try {
            context.unbindService(mConnection);
        } catch (Exception ignored) {
        }
        mIsBound = false;
        connection = DISCONNECTED;
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mConsumerService = ((ConsumerService.LocalBinder) service).getService();
            mConsumerService.findPeers(connectionCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.d("abc", "Service disconnected..");
            mConsumerService = null;
            mIsBound = false;
        }
    };
    private ConnectionCallback connectionCallback = new ConnectionCallback() {
        @Override
        public void onSuccess() {
            connection = CONNECTED;
        }

        @Override
        public void onError(String status) {
            connection = DISCONNECTED;
            try {
                context.unbindService(mConnection);
            } catch (Exception ignored) {
            }
            mIsBound = false;

        }
    };
}