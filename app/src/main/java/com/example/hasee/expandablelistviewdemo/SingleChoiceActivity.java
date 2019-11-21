package com.example.hasee.expandablelistviewdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class SingleChoiceActivity extends AppCompatActivity {
    private int count;
    private int current;//当前显示的题目

    private boolean wrongMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        Bundle bundle=getIntent().getExtras();
//        String itemname=bundle.getString("FromTag");
//        Toast.makeText(this,itemname,Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent=getIntent();
        String FromUnit=intent.getStringExtra("unit");//来源章
        String FromLesson=intent.getStringExtra("lesson");//来源节

        DBservice dbService=new DBservice();
        final List<Question> list=dbService.getQuestions(FromUnit,FromLesson);


        count=list.size();

        current=0;
        wrongMode=false;

        final TextView tv_question=findViewById(R.id.question);
        final RadioButton[] radioButtons=new RadioButton[4];

        radioButtons[0]=findViewById(R.id.answerA);
        radioButtons[1]=findViewById(R.id.answerB);
        radioButtons[2]=findViewById(R.id.answerC);
        radioButtons[3]=findViewById(R.id.answerD);

        Button btn_next=findViewById(R.id.btn_next);
        Button btn_previous=findViewById(R.id.btn_previous);

        final TextView tv_explaination=findViewById(R.id.explaination);
        final RadioGroup radioGroup=findViewById(R.id.radioGroup);

        try {
            Question q=list.get(0);
            tv_question.setText(q.question);
            tv_explaination.setText(q.explaination);
            radioButtons[0].setText(q.answerA);
            radioButtons[1].setText(q.answerB);
            radioButtons[2].setText(q.answerC);
            radioButtons[3].setText(q.answerD);
        }catch (IndexOutOfBoundsException e){
//            Intent intent=new Intent(ExamActivity.this,InfoActivity.class);
//            startActivity(intent);

            String ToastStr="<font color='#f0134d'>"+"空数据异常，请返回！！！"+"</font>";
            Toast toast = Toast.makeText(this, Html.fromHtml(ToastStr), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            radioGroup.setVisibility(View.INVISIBLE);
            btn_next.setVisibility(View.INVISIBLE);
            btn_previous.setVisibility(View.INVISIBLE);
            tv_question.setText("");


        }



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < count - 1) {
                    current++;
                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    tv_explaination.setText(q.explaination);

                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
                else if(current == count - 1 && wrongMode == true)
                {
                    new AlertDialog.Builder(SingleChoiceActivity.this)
                            .setTitle("提示")
                            .setMessage("已经到达最后一题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SingleChoiceActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
                else
                {
                    final List<Integer> wrongList = checkAnswer(list);
                    if(wrongList.size() == 0)
                    {
                        new AlertDialog.Builder(SingleChoiceActivity.this)
                                .setTitle("提示")
                                .setMessage("恭喜你全部回答正确！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SingleChoiceActivity.this.finish();
                                    }
                                })
                                .show();
                    }else {
                        new AlertDialog.Builder(SingleChoiceActivity.this)
                                .setTitle("提示")
                                .setMessage("您答对了" + (list.size() - wrongList.size()) +
                                        "道题目，答错了" + wrongList.size() + "道题目。是否查看错题？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        wrongMode = true;
                                        List<Question> newList = new ArrayList<Question>();
                                        for (int i = 0; i < wrongList.size(); i++) {
                                            newList.add(list.get(wrongList.get(i)));
                                        }
                                        list.clear();
                                        for (int i = 0; i < newList.size(); i++) {
                                            list.add(newList.get(i));
                                        }

                                        current = 0;
                                        count = list.size();

                                        Question q = list.get(current);
                                        tv_question.setText(q.question);
                                        radioButtons[0].setText(q.answerA);
                                        radioButtons[1].setText(q.answerB);
                                        radioButtons[2].setText(q.answerC);
                                        radioButtons[3].setText(q.answerD);
                                        tv_explaination.setText(q.explaination);
                                        tv_explaination.setVisibility(View.VISIBLE);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       SingleChoiceActivity.this.finish();
                                    }
                                })
                                .show();
                    }

                }
            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current > 0) {
                    current--;
                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    tv_explaination.setText(q.explaination);

                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(int i = 0; i < 4; i++)
                {
                    if(radioButtons[i].isChecked() == true)
                    {
                        list.get(current).selectedAnswer = i;
                        break;
                    }
                }
            }
        });
    }


    //该方法用来保存回答错误的题目的下标
    private List<Integer> checkAnswer(List<Question> list)
    {
        List<Integer> wrongList = new ArrayList<Integer>();
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).answer != list.get(i).selectedAnswer)
            {
                wrongList.add(i);
            }
        }
        return wrongList;
    }
}
