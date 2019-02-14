package org.md2k.cerebralcortex;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.md2k.cerebralcortex.exception.MCExceptionInvalidLogin;
import org.md2k.cerebralcortex.exception.MCExceptionServerDown;
import org.md2k.mcerebrumapi.core.exception.MCException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        final CountDownLatch latch = new CountDownLatch(1);

        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");

//        String password = ("8b2efjwp");
            CerebralCortexManager.getInstance(context).login(server, userName, password, new CerebralCortexCallback() {
                @Override
                public void onSuccess(Object obj) {
                    assertTrue(true);
                    latch.countDown();

                }

                @Override
                public void onError(MCException exception) {
                    Assert.fail();
                    latch.countDown();

                }
            });
        try {
            latch.await(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }

    }

    @Test
    public void downloadConfigFile(){
        final CountDownLatch latch = new CountDownLatch(1);
        Hawk.init(context).build();
        ServerInfo.setServerAddress("https://odin.md2k.org");
        ServerInfo.setUserName("smh");
        ServerInfo.setPassword(convertSHA("8b2efjwp"));
        ServerInfo.setLoggedIn(true);
        String fileName = "decisions.zip";

        CerebralCortexManager.getInstance(context).downloadConfigurationFile(fileName, new CerebralCortexCallback() {
                @Override
                public void onSuccess(Object obj) {
                    assertTrue(true);
                    latch.countDown();
                }

                @Override
                public void onError(MCException exception) {
                    Log.d("abc","exception="+exception.getMessage());
                    fail();
                    latch.countDown();
                }
            });
        try {
            latch.await(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }

    }

    @Test
    public void getConfigFiles(){
        final CountDownLatch latch = new CountDownLatch(1);
        Hawk.init(context).build();
        ServerInfo.setServerAddress("https://odin.md2k.org");
        ServerInfo.setUserName("smh");
        ServerInfo.setPassword(convertSHA("8b2efjwp"));
        ServerInfo.setLoggedIn(true);
            CerebralCortexManager.getInstance(context).getConfigurationFiles(new CerebralCortexCallback() {
                @Override
                public void onSuccess(Object obj) {
                    ArrayList<FileInfo> files = (ArrayList<FileInfo>) obj;
                    assertEquals(files.size(), 3);
                    latch.countDown();
                }

                @Override
                public void onError(MCException exception) {
                    fail();
                    latch.countDown();
                }
            });
        try {
            latch.await(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }

    }

    @Test
    public void checkInvalidURL(){
        final CountDownLatch latch = new CountDownLatch(1);
        Hawk.init(context).build();
        ServerInfo.setPassword(convertSHA("8b2efjwp"));

        String server = "https://abc.abc";
        String userName = "smh";
        String password = convertSHA("8b2efjwp");
//        String password = ("8b2efjwp");
            CerebralCortexManager.getInstance(context).login(server, userName, password, new CerebralCortexCallback() {
                @Override
                public void onSuccess(Object obj) {
                    fail();
                    latch.countDown();
                }

                @Override
                public void onError(MCException exception) {
                    if(exception instanceof MCExceptionServerDown)
                        assertTrue(true);
                    else fail();
                    latch.countDown();
                }
            });
        try {
            latch.await(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }

    }

    @Test
    public void checkInvalidPassword(){
        final CountDownLatch latch = new CountDownLatch(1);
        Hawk.init(context).build();

        String server = "https://odin.md2k.org";
        String userName = "smh";
        String password = convertSHA("abc");
            CerebralCortexManager.getInstance(context).login(server, userName, password, new CerebralCortexCallback() {
                @Override
                public void onSuccess(Object obj) {
                    fail();
                    latch.countDown();
                }

                @Override
                public void onError(MCException exception) {
                    if(exception instanceof MCExceptionInvalidLogin)
                    assertTrue(true);
                    else fail();
                    latch.countDown();
                }
            });
        try {
            latch.await(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }
    }
}
