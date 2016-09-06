package com.example.harshalbenake.pptviewer;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.itsrts.pptviewer.PPTViewer;

public class MainActivity extends Activity {

    PPTViewer pptViewer;
    public static final String sdCardPath = Environment.getExternalStorageDirectory() + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pptViewer = (PPTViewer) findViewById(R.id.pptviewer);
        String path = sdCardPath +"Bestowal" + "/" + "PPT1.ppt";
        pptViewer.setNext_img(R.drawable.next).setPrev_img(R.drawable.prev)
                .setSettings_img(R.drawable.settings)
                .setZoomin_img(R.drawable.zoomin)
                .setZoomout_img(R.drawable.zoomout);
        pptViewer.loadPPT(this, path);

    }
}
