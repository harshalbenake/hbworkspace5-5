package com.example.harshalbenake.ocrtessarct;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int sRC_Document = 1000;
    private final String sdCardPath = Environment.getExternalStorageDirectory() + "/";
    private TessOCR mTessOCR;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTessOCR = new TessOCR(MainActivity.this);

        mTextView = (TextView) findViewById(R.id.textview);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCustomFileBrowserActivity();


               /* Bitmap bitmap = BitmapFactory.decodeFile(sdCardPath + "3.jpg");
                String strResult = tessOCR.getOCRResult(bitmap);
                System.out.println("strResult: " + strResult);
                textView.setText(strResult);*/
            }
        });
    }

    /**
     * Intent start CustomFileBrowserActivity.
     **/
    public void startCustomFileBrowserActivity() {
        Intent intent = new Intent(MainActivity.this, CustomFileBrowser.class);
        startActivityForResult(intent, sRC_Document);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case sRC_Document:
                if (resultCode == RESULT_OK) {
                    try {
                        final String filePath = intent.getStringExtra("data");
                        if (filePath != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            String strResult = mTessOCR.getOCRResult(bitmap);
                            mTextView.setText("Result Data: \n\n" + strResult);
                        } else {
                            //invalid file
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
