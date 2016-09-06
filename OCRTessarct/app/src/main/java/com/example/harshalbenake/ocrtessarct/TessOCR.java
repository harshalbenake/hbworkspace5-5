package com.example.harshalbenake.ocrtessarct;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class TessOCR {
    private TessBaseAPI mTess;
    Context mContext;

    public TessOCR(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext=context;
        setupOCR();
        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists())
            dir.mkdirs();
        mTess.init(datapath, "eng");
    }


    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        String result=mTess.getUTF8Text();
      //  mTess.end();
//        if(result!=null) {
            return result.replaceAll("[^A-Za-z0-9 ]", "");
//        }else{
//            return "";
//        }
    }

    public void onDestroy() {
        mTess.end();
    }

    public void setupOCR(){

        File folder = new File(Environment.getExternalStorageDirectory() + "/tesseract/tessdata");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File saving = new File(folder, "eng.traineddata");
        try {
            saving.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream stream = null;
        try {
            stream = mContext.getAssets().open("eng.traineddata", AssetManager.ACCESS_STREAMING);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (stream != null){
            copyInputStreamToFile(stream, saving);
        }
    }

    private void copyInputStreamToFile( InputStream in, File file ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyAssets() {
        AssetManager assetManager = mContext.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (Exception e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                System.out.println("filename: "+filename);
                in = assetManager.open(filename);

                String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
                File dir = new File(datapath + "tessdata/");
                if (!dir.exists())
                    dir.mkdirs();

                File outFile = new File(dir+filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(Exception e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        // NOOP
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}