package org.md2k.library.datakit.storage.archiver.csv;

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
public class CSVArchiver /*implements IArchiver*/ {
/*
    public static final String ID = "CSV";
    private static final String DIRNAME = "csv";
    private static final String METADATA_PREFIX="metadata-";
    private static final String DATA_PREFIX="data-";
    private static final String FILE_EXTENSION = ".csv";
    private HashMap<String, String> hashMap;
    private String directoryPath;
    private int maxFileSize;

    CSVArchiver(String directoryPath, int maxFileSize){
        this.directoryPath = directoryPath+DIRNAME;
        this.maxFileSize = maxFileSize;
    }

    @Override
    public void start(){
        hashMap = new HashMap<>();
        FileUtils.createOrExistsDir(directoryPath);
    }
    @Override
    public DataKitInfo.Info getInfo() {
        long size = FileUtils.getDirLength(directoryPath);
        long fileCount = FileUtils.listFilesInDir(directoryPath, true).size();
        DataKitInfo.Info a = new DataKitInfo.Info();
        a.directoryPath = directoryPath;
        a.size = size;
        a.fileCount = fileCount;
        return a;
    }

    @Override
    public void deleteAll(){
        stop();
        FileUtils.deleteDir(directoryPath);
    }
    @Override
    public void stop(){

    }
    public String getDirectory(){
        return directoryPath;
    }
    public int getFileNo(){
        return FileUtils.listFilesInDir(directoryPath).size();
    }

    @Override
    public void insertDataSource(DataSource d) {
        String fileName = METADATA_PREFIX+d.toUUID();
        String filePath = getFilePath(fileName);
        writeString(filePath, d.toString());
    }

    @Override
    public void insertData(HashMap<String, Data[]> data) {
        String fileName;
        for (Map.Entry<String, Data[]> entry : data.entrySet()) {
            String key = entry.getKey();
            Data[] value = entry.getValue();
            fileName = DATA_PREFIX+key;
            String filePath = getFilePath(fileName);
            String str = getString(value);
            writeString(filePath, str);
        }
    }
    private void writeString(String filePath, String data){
        try {
            File file = new File(filePath);
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
    private String getString(Data[] data){
        StringBuilder str = new StringBuilder();
        for (Data aData : data) {
            str.append(aData.toString()).append("\n");
        }
        return str.toString();
    }
    private String getFilePath(String filePrefix){
        String filePath=null;
        if(hashMap.get(filePrefix)!=null){
            filePath = hashMap.get(filePrefix);
            if(FileUtils.getFileLength(filePath)>maxFileSize){
                hashMap.put(filePrefix, createFileName(filePrefix));
            }
        }else{
            hashMap.put(filePrefix, createFileName(filePrefix));
        }
        return hashMap.get(filePrefix);
    }
    private String createFileName(final String filePrefix){
        int index = getFileIndex(filePrefix);
        return directoryPath+File.separator+filePrefix+"."+String.format(Locale.getDefault(), "%03d", index)+FILE_EXTENSION;
    }
    private int getFileIndex(final String filePrefix){
        int index=-1;
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.getName().startsWith(filePrefix)) return true;
                return false;
            }
        };
        List<File> files = FileUtils.listFilesInDirWithFilter(directoryPath, fileFilter);
        for(int i = 0;i<files.size();i++){
            String f = files.get(i).getName();
            int v = Integer.parseInt(f.substring(f.length()-9, f.length()-5));
            if(v>index) index=v;
        }
        return index+1;
    }
*/

}
