package org.md2k.cerebralcortex;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import org.md2k.mcerebrumapi.core.data.MCValue;
import org.md2k.mcerebrumapi.core.extensionapi.library.ExtensionCallback;
import org.md2k.mcerebrumapi.core.extensionapi.library.IPermissionInterface;
import org.md2k.mcerebrumapi.core.extensionapi.library.MCAction;
import org.md2k.mcerebrumapi.core.extensionapi.library.MCExtensionAPILibrary;

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
public class CerebralCortexExtension {
    private static final String LOGIN = "LOGIN";
    private static final String LOGOUT = "LOGOUT";
    private static String[] permissions= new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };
    public static MCExtensionAPILibrary createExtensionAPI(final Context context) {
        return MCExtensionAPILibrary.builder()
                .setId(org.md2k.cerebralcortex.BuildConfig.APPLICATION_ID)
                .setName("Cerebral Cortex")
                .setDescription("Library for communicating with cerebral cortex")
                .setIcon(null)
                .setVersion(org.md2k.cerebralcortex.BuildConfig.VERSION_CODE, org.md2k.cerebralcortex.BuildConfig.VERSION_NAME)
                .setPermissionList(permissions)
                .noConfiguration()

/*
                .addAction(LOGIN, "Login", "Login to cerebral cortex", new MCAction() {
                    @Override
                    public void run(MCValue param, ExtensionCallback extensionCallback) {
                        String[] values=param.getSample();
                        try {
                            boolean loggedIn = CerebralCortexManager.getInstance(context).login(values[0], values[1], values[2]);
                            if(loggedIn)
                                extensionCallback.onSuccess();
                            else extensionCallback.onError("Invalid Username/password");
                        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
                            extensionCallback.onError(mcExceptionInternetConnection.getMessage());
                        } catch (MCExceptionServerDown mcExceptionServerDown) {
                            extensionCallback.onError(mcExceptionServerDown.getMessage());
                        }
                    }
                })
*/
                .build();
    }
}
