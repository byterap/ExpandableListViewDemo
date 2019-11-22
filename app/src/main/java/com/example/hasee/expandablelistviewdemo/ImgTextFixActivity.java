package com.example.hasee.expandablelistviewdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hasee.expandablelistviewdemo.gson.Data;
import com.example.hasee.expandablelistviewdemo.gson.News;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImgTextFixActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lvNews;
    //声明Adapter作为listview的填充
    private NewsAdapter adapter;
    private List<Data> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_text_fix);
        lvNews = (ListView)findViewById(R.id.lvNews);
        dataList = new ArrayList<Data>();
        adapter = new NewsAdapter(this, dataList);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(this);
//        sendRequestWithOKHttp();
        getJsonString("news.json",this);
    }

    //获取api数据
    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://v.juhe.cn/toutiao/index?type=top&key=468a538795ca302846f994e7559df8a7")
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("测试：", responseData);
                    parseJsonWithGson(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //获取本地json数据
    private void getJsonString(String fileName,Context context){

        StringBuilder stringBuilder=new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            String responseData=stringBuilder.toString();
//            Log.d("测试1：", responseData);
            parseJsonWithGson(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseJsonWithGson(String jsonData){
        Gson gson = new Gson();
        News news = gson.fromJson(jsonData, News.class);
        List<Data> list = news.getResult().getData();
        for (int i=0; i<list.size(); i++){
            String uniquekey = list.get(i).getUniqueKey();
            String title = list.get(i).getTitle();
            String date = list.get(i).getDate();
            String category = list.get(i).getCategory();
            String author_name = list.get(i).getAuthorName();
            String content_url = list.get(i).getUrl();
            String pic_url = list.get(i).getThumbnail_pic_s();
//            System.out.println("标题："+title);
//            System.out.println("日期："+date);
//            System.out.println("作者："+author_name);
//            System.out.println("网址："+content_url);
//            System.out.println("图片："+pic_url);
            dataList.add(new Data(uniquekey, title, date, category, author_name, content_url, pic_url));
        }
        //更新Adapter(务必在主线程中更新UI!!!)
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data data = dataList.get(position);
        Intent intent = new Intent(this, BrowseNewsActivity.class);
        intent.putExtra("content_url", data.getUrl());
        startActivity(intent);
    }
}
