package com.example.hasee.expandablelistviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtnSingleChoice;
    private Button mBtnFillBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSingleChoice=findViewById(R.id.btn_singlechoice);
        mBtnSingleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SingleChoiceContentActivity.class);
                startActivity(intent);
            }
        });

    }
}
