package org.md2k.cerebralcortex.cerebralcortexwebapi.utils;

import android.util.Log;

import org.md2k.cerebralcortex.cerebralcortexwebapi.interfaces.CerebralCortexWebApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ApiUtils {

    public static CerebralCortexWebApi getCCService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CerebralCortexWebApi.class);
    }


    public static final Boolean writeResponseToDisk(ResponseBody body, String outputFileDir, String outputFileName) {
        boolean result = false;
        try{
            File minioObject = new File(outputFileDir, outputFileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try{
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(minioObject);

                while (true){
                    int read = inputStream.read(fileReader);
                    if(read==-1){
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("CC Web API", "File Download: "+ fileSizeDownloaded+ " of "+fileSize);
                }
                outputStream.flush();

                result = true;
            }catch (IOException e){
                result = false;
            }finally {
                if(inputStream !=null){
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            }
        }catch (IOException e){
            Log.d("abc","error...");
            result = false;
        }
        return result;
    }

    public static MultipartBody.Part getUploadFileMultipart(String filePath){
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileMultiBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return fileMultiBodyPart;
    }
}