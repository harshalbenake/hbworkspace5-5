package com.example.harshalbenake.ocrtessarct;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create custom file browser.
 * Created by <b>Harshal Benake</b> on 19/11/15.
 */
public class CustomFileBrowser extends ListActivity {
    private List<String> item = null;
    private List<String> path = null;
    private String root = "/sdcard";
    private TextView myPath;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_filebrowser);
        myPath = (TextView) findViewById(R.id.path);
        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        getDir(root+"/"+getResources().getString(R.string.app_name));
        getDir(root);
    }

    private void getDir(String dirPath) {
        myPath.setText("FilePath: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();
        if (!dirPath.equals(root)) {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            path.add(file.getPath());
            if (file.isDirectory())
                item.add(file.getName() + "/");
            else
                item.add(file.getName());
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
                R.layout.row_item_filebrowser, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(path.get(position));
        if (file.isDirectory()) {
            if (file.canRead())
                getDir(path.get(position));
            else {
            }
        } else {
            Intent intent = new Intent();
            intent.putExtra("data",file.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}