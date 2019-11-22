package com.example.hasee.expandablelistviewdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBserviceFillBlank {
    private SQLiteDatabase db;
    private String Unit;
    private String Lesson;

    public DBserviceFillBlank(){
        db= SQLiteDatabase.openDatabase("/data/data/com.example.hasee.expandablelistviewdemo/databases/question.db",null, SQLiteDatabase.OPEN_READONLY);


    }

    public List<Question> getQuestions(String unit, String lesson){
        this.Unit=unit;
        this.Lesson=lesson;
        List<Question> list=new ArrayList<Question>();
        String FinalUnit="\""+this.Unit+"\"";
        String FinalLesson="\""+this.Lesson+"\"";
        Cursor cursor=db.rawQuery("select * from fillblank where unit="+FinalUnit+" AND lesson="+FinalLesson,null);
//        Cursor cursor=db.rawQuery("select * from question",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int count=cursor.getCount();

            //使用循环将cursor中的每一条记录都生成一个Question对象，并将其添加到List中
            for (int i=0;i<count;i++){
                cursor.moveToPosition(i);
                Question question=new Question();
                question.ID=cursor.getInt(cursor.getColumnIndex("ID"));
                question.question=cursor.getString(cursor.getColumnIndex("question"));
                question.index=cursor.getString(cursor.getColumnIndex("index"));
                question.index_length=cursor.getInt(cursor.getColumnIndex("index_length"));
                question.answer1=cursor.getString(cursor.getColumnIndex("answer"));
                question.answer_length=cursor.getInt(cursor.getColumnIndex("answer_length"));
                question.explaination=cursor.getString(cursor.getColumnIndex("explaination"));
                question.unit=cursor.getString(cursor.getColumnIndex("unit"));
                question.lesson=cursor.getString(cursor.getColumnIndex("lesson"));

//                question.selectedAnswer=-1;//表示未选择任何对象
                list.add(question);


            }


        }
        return list;
    }

}
