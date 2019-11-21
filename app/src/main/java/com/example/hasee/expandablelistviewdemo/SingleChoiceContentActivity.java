package com.example.hasee.expandablelistviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class SingleChoiceContentActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    public String[] groupString = {"Unit1 School", "Unit2 Face", "Unit3 Animals", "Unit4 Numbers", "Unit5 Colours", "Unit6 Fruit"};
    public String[][] childString = {
            {"Lesson1", "Lesson2", "Lesson3"},
            {"Lesson1", "Lesson2", "Lesson3"},
            {"Lesson1", "Lesson2", "Lesson3"},
            {"Lesson1", "Lesson2", "Lesson3"},
            {"Lesson1", "Lesson2", "Lesson3"},
            {"Lesson1", "Lesson2", "Lesson3"}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_choice);

        expandableListView = (ExpandableListView)findViewById(R.id.expend_list);
        expandableListView.setAdapter(new MyExtendableListViewAdapter());


        //设置分组的监听
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                Toast.makeText(getApplicationContext(), groupString[groupPosition], Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //设置子项布局监听
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(getApplicationContext(), groupString[groupPosition]+" "+childString[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
//                return true;
//
//            }
//        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(SingleChoiceContentActivity.this, SingleChoiceActivity.class);

                intent.putExtra("unit",groupString[groupPosition]);
                intent.putExtra("lesson",childString[groupPosition][childPosition]);


                startActivity(intent);
                return true;

            }
        });

//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                int count = new MyExtendableListViewAdapter().getGroupCount();
//
//                for (int i = 0; i < count; i++) {
//                    expandableListView.expandGroup(i);
//                }
//            }
//        });

        //控制他只能打开一个组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = new MyExtendableListViewAdapter().getGroupCount();
                for(int i = 0;i < count;i++){
                    if (i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }
}
