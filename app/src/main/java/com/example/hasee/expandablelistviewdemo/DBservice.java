package com.example.hasee.expandablelistviewdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBservice {
    private SQLiteDatabase db;
    private String Unit;
    private String Lesson;

    public DBservice(){
        db= SQLiteDatabase.openDatabase("/data/data/com.example.hasee.expandablelistviewdemo/databases/question.db",null, SQLiteDatabase.OPEN_READONLY);


    }

    public List<Question> getQuestions(String unit,String lesson){
        this.Unit=unit;
        this.Lesson=lesson;
        List<Question> list=new ArrayList<Question>();
        String FinalUnit="\""+this.Unit+"\"";
        String FinalLesson="\""+this.Lesson+"\"";
        Cursor cursor=db.rawQuery("select * from englishquestion where unit="+FinalUnit+" AND lesson="+FinalLesson,null);
//        Cursor cursor=db.rawQuery("select * from question",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            int count=cursor.getCount();

            //使用循环将cursor中的每一条记录都生成一个Question对象，并将其添加到List中
            for (int i=0;i<count;i++){
                cursor.moveToPosition(i);
                Question question=new Question();
                question.question=cursor.getString(cursor.getColumnIndex("question"));
                question.answerA=cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB=cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC=cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD=cursor.getString(cursor.getColumnIndex("answerD"));
                question.answer=cursor.getInt(cursor.getColumnIndex("answer"));
                question.unit=cursor.getString(cursor.getColumnIndex("unit"));
                question.lesson=cursor.getString(cursor.getColumnIndex("lesson"));
                question.ID=cursor.getInt(cursor.getColumnIndex("ID"));
                question.explaination=cursor.getString(cursor.getColumnIndex("explaination"));

                question.selectedAnswer=-1;//表示未选择任何对象
                list.add(question);


            }


        }
        return list;
    }

}
