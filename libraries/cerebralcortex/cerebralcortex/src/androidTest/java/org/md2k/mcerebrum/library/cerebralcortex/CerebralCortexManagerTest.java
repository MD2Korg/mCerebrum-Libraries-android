package org.md2k.mcerebrum.library.cerebralcortex;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.md2k.mcerebrum.library.cerebralcortex.exception.MCExceptionConfigNotFound;
import org.md2k.mcerebrum.library.cerebralcortex.exception.MCExceptionInternetConnection;
import org.md2k.mcerebrum.library.cerebralcortex.exception.MCExceptionNotLoggedIn;
import org.md2k.mcerebrum.library.cerebralcortex.exception.MCExceptionServerDown;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
public class CerebralCortexManagerTest {
    private Context context;
    @Test
    public void checkPermissions(){
        int a = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
        int b = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE);
        int c = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        assertEquals(a, PackageManager.PERMISSION_GRANTED);
        assertEquals(b, PackageManager.PERMISSION_GRANTED);
        assertEquals(c, PackageManager.PERMISSION_GRANTED);
    }

    @Before
    public void setUp() throws Exception {
        context = getTargetContext().getApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
    }

    private String convertSHA(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return "";
    }

    @Test
    public void checkValidLogin(){

        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");
//        String password = ("8b2efjwp");
        try {
            boolean result = CerebralCortexManager.getInstance(context).login(server, userName, password);
            assertTrue(result);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            fail();
            mcExceptionInternetConnection.printStackTrace();
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            fail();
            mcExceptionServerDown.printStackTrace();
        }
    }
    @Test
    public void downloadConfigFile(){
        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");
        String fileName = "memphis-stress-test.zip";
//        String password = ("8b2efjwp");
        try {
            boolean result = CerebralCortexManager.getInstance(context).login(server, userName, password);
            assertTrue(result);
            boolean b = CerebralCortexManager.getInstance(context).downloadConfigurationFile(fileName);
            assertTrue(b);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            fail();
            mcExceptionInternetConnection.printStackTrace();
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            fail();
            mcExceptionServerDown.printStackTrace();
        } catch (MCExceptionNotLoggedIn mcExceptionNotLoggedIn) {
            fail();
        } catch (MCExceptionConfigNotFound mcExceptionConfigNotFound) {
            fail();
        }
    }

    @Test
    public void getConfigFiles(){
        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");
//        String password = ("8b2efjwp");
        try {
            boolean result = CerebralCortexManager.getInstance(context).login(server, userName, password);
            assertTrue(result);
            ArrayList<FileInfo> fileInfoArrayList = CerebralCortexManager.getInstance(context).getConfigurationFiles();
            assertEquals(fileInfoArrayList.size(), 2);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            fail();
            mcExceptionInternetConnection.printStackTrace();
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            fail();
            mcExceptionServerDown.printStackTrace();
        } catch (MCExceptionNotLoggedIn mcExceptionNotLoggedIn) {
            fail();
        }
    }

    @Test
    public void checkInvalidURL(){

        String server = "https://abc.abc";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");
//        String password = ("8b2efjwp");
        try {
            boolean result = CerebralCortexManager.getInstance(context).login(server, userName, password);
            assertFalse(result);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            fail();
            mcExceptionInternetConnection.printStackTrace();
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            assertTrue(true);
        }
    }
    @Test
    public void checkInvalidPassword(){

        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("8b2efjwpa");
//        String password = ("8b2efjwp");
        try {
            boolean result = CerebralCortexManager.getInstance(context).login(server, userName, password);
            assertFalse(result);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            fail();
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            fail();
        }
    }

}
