package com.example.harshalbenake.androidtreeview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import activity.MainActivity;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        LinearLayout main_container=(LinearLayout)findViewById(R.id.main_container);

        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("ParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        TreeNode child2 = new TreeNode("ChildNode2");
        parent.addChildren(child0, child1,child2);
        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(DemoActivity.this, root);
        main_container.addView(tView.getView());

        parent.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Toast.makeText(DemoActivity.this,"ParentNode",Toast.LENGTH_SHORT).show();
            }
        });

        child0.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Toast.makeText(DemoActivity.this,"ChildNode0",Toast.LENGTH_SHORT).show();
            }
        });

        child1.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Toast.makeText(DemoActivity.this,"ChildNode1",Toast.LENGTH_SHORT).show();
            }
        });

        child2.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Toast.makeText(DemoActivity.this,"ChildNode2",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
