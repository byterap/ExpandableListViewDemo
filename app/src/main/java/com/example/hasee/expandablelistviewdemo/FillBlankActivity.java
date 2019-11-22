package com.example.hasee.expandablelistviewdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillBlankActivity extends AppCompatActivity {

//    private Button mBtnShowContent;
//    private TextView mTvResult;
    private int count;
    private int current;//当前显示的题目
    private boolean wrongMode;

    private String tip0,tip;

    //题目信息初始化
    String question = "";//题目
    String index = "";//挖空索引序列
    int index_length = 0;//挖空索引长度
    String answer="";
    int answer_length=0;
    String explaination="";

    String[] ans;


    @BindView(R.id.fbv_content)
    FillBlankView fbvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blank);
        Button mBtnPrevious= (Button) findViewById(R.id.btn_previous);
        Button mBtnNext= (Button) findViewById(R.id.btn_next);

        Button mBtnShowContent = (Button) findViewById(R.id.btn_show_content);
        final TextView mTvResult= (TextView) findViewById(R.id.tv_result);
//        String FromUnit = "古诗";
//        String FromLesson = "悯农";

        Intent intent=getIntent();
        String FromUnit=intent.getStringExtra("unit");//来源章
        String FromLesson=intent.getStringExtra("lesson");//来源节
        DBserviceFillBlank dBserviceFillBlank=new DBserviceFillBlank();
        final List<Question> list=dBserviceFillBlank.getQuestions(FromUnit,FromLesson);
        count=list.size();

        current=0;
        wrongMode=false;

        //数据测试

        try{
            Question q = list.get(0);
            question = q.question;
            index = q.index;
            index_length = q.index_length;
            answer = q.answer1;
            answer_length = q.answer_length;
            explaination = q.explaination;
        }catch (IndexOutOfBoundsException e){
            String ToastStr="<font color='#f0134d'>"+"空数据异常，请返回！！！"+"</font>";
            Toast toast = Toast.makeText(this, Html.fromHtml(ToastStr), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            mTvResult.setVisibility(View.INVISIBLE);
            mBtnNext.setVisibility(View.INVISIBLE);
            mBtnPrevious.setVisibility(View.INVISIBLE);
            mBtnShowContent.setVisibility(View.INVISIBLE);

        }
        try {
            ans = answerProcess(answer, answer_length);
        }catch (StringIndexOutOfBoundsException e){

        }



        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < count - 1) {
                    current++;
                    Question q = list.get(current);
                    question=q.question;
                    index = q.index;
                    index_length = q.index_length;
                    answer = q.answer1;
                    answer_length = q.answer_length;
                    explaination=q.explaination;
                    mTvResult.setText("");
                }else if (current==count-1){
                    new AlertDialog.Builder(FillBlankActivity.this)
                            .setTitle("提示")
                            .setMessage("已经到达最后一题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FillBlankActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();

                }
//

                initData();
                ans = answerProcess(answer, answer_length);
            }
        });

        mBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0) {
                    current--;
                    Question q = list.get(current);
                    question=q.question;
                    index = q.index;
                    index_length = q.index_length;
                    answer = q.answer1;
                    answer_length = q.answer_length;
                    explaination=q.explaination;
                    mTvResult.setText("");

                    }else if (current==0){
                    new AlertDialog.Builder(FillBlankActivity.this)
                            .setTitle("提示")
                            .setMessage("已经无法前进，这是第一题！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    FillBlankActivity.this.getApplication();
                                    Toast.makeText(FillBlankActivity.this,"继续答题", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
                initData();
                ans = answerProcess(answer, answer_length);
            }
        });

        //textview加滚动条
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());




        ButterKnife.bind(this);

        initData();

        mBtnShowContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> answerList = fbvContent.getAnswerList();

                int answerList_length = answerList.size();
                String[] answerList_List = new String[answerList_length];
                int empty = 0;
                ArrayList<Integer> arrayList=new ArrayList();

               //判断用户有几个空未作答
                for (int i = 0; i < answerList_length; i++) {
                    if (answerList.get(i).equals("")) {
                        empty += 1;
                    }
                    answerList_List[i] = answerList.get(i);
                }
                //判断哪个空错误
                for (int i = 0; i < answerList_length; i++) {
                    if (!answerList.get(i).equals(ans[i])) {
                        arrayList.add(i+1);
                        
                    }
                    tip0=arrayList.toString().substring(1).substring(0,arrayList.toString().substring(1).length()-1);
                    tip="第"+tip0+"个空错误";
                    answerList_List[i] = answerList.get(i);
                }
                if (Arrays.equals(answerList_List, ans)) {
                    mTvResult.setText("回答正确");
                } else if (empty > 0) {
                    mTvResult.setText("您有" + empty + "个选项未作答，请检查！");
                } else {
                    mTvResult.setText("回答错误\n"+"错误信息："+tip+"\n"+"解析："+explaination);

                }

            }
        });


    }

    /**
     * 将字符串形式的挖空索引序列转换为整型数组
     *
     * @param index        挖空索引序列
     * @param index_length 挖空索引长度
     * @return 挖空索引序列整型数组
     */
    public int[] indexProcess(String index, int index_length) {
        int[] indexList = new int[index_length];
        try {
            String index1 = index.substring(1);//去掉首字符
            String index2 = index1.substring(0, index1.length() - 1);//去掉尾字符
            String[] indexList_s = index2.split(",");

            for (int i = 0; i < index_length; i++) {
                Integer integer = Integer.valueOf(indexList_s[i]);
                indexList[i] = integer;
            }

        }catch (StringIndexOutOfBoundsException e){

        }
        return indexList;
    }

    /**
     * 将字符串形式的答案序列转换为字符串数组
     *
     * @param answer        答案序列
     * @param answer_length 答案个数（挖空个数）
     * @return 答案序列字符串数组
     */
    public String[] answerProcess(String answer, int answer_length) {
        String[] answerList_final = new String[answer_length];
        try {
            String answer1 = answer.substring(1);//去掉首字符
            String answer2 = answer1.substring(0, answer1.length() - 1);//去掉尾字符
            String[] answerList = answer2.split(",");

            System.arraycopy(answerList, 0, answerList_final, 0, answer_length);

        }catch (StringIndexOutOfBoundsException e){

        }

        return answerList_final;
    }


    private void initData() {
        String content = question;//数据库传入题目

        int[] result = indexProcess(index, index_length);

        // 答案范围集合
        List<AnswerRange> rangeList = new ArrayList<>();

        //根据挖空索引值自动挖空
        for (int i = 0; i < index_length; i += 2) {
            rangeList.add(new AnswerRange(result[i], result[i + 1]));
        }


        fbvContent.setData(content, rangeList);
    }

}
