package com.example.harshalbenake.circularlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import circlemenulayout.CircleMenuLayout;
import circlemenulayout.CircleMenuLayout.OnMenuItemClickListener;

/**
 * <pre>
 * @author zhy
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleActivity extends Activity {

    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[]{"Item 0", "Item 1", "Item 2", "Item 3",
            "Item 4", "Item 5", "Item 6"};
    private int[] mItemImgs = new int[]{
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher};
    private TextView mtv_circlemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cicularmenu2);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mtv_circlemenu = (TextView) findViewById(R.id.tv_circlemenu);

        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public void itemClick(final View view, int pos) {
                mtv_circlemenu.setText(mItemTexts[pos]);
                Animation rotateAnimation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
                rotateAnimation.setDuration(250);
                view.startAnimation(rotateAnimation);
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(CircleActivity.this, "Middle Item Clicked", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
