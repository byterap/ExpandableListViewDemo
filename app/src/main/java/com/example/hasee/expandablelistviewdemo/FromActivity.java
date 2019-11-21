package com.example.hasee.expandablelistviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FromActivity extends AppCompatActivity {
    private static final String TAG ="From_Ma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        TextView mTvFrom = findViewById(R.id.tv_from);
        Intent intent=getIntent();
        String string=intent.getStringExtra("unit");//来源章
        String string1=intent.getStringExtra("lesson");//来源节
        Log.d(TAG,string);
        Log.d(TAG,string1);
//        mTvFrom.setText(string);
//        mTvFrom.setText(intent.getStringExtra("data"));
    }
}
