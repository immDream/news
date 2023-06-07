package com.immdream.extractnews.commons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExtractNews1 {

    private final static String url_prefix = "http://top.news.sina.com.cn/ws/GetTopDataList.php?top_type=day&top_cat=www_www_all_suda_suda&top_time=";
    private final static String url_suffix = "&top_show_num=100&top_order=DESC&js_var=all_1_data01";
    private final static String start_Date = "2023-01-01";
    private final static SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
    private final static String end_Date = s.format(Calendar.getInstance().getTime());
    private final static String filePath = "D:\\project\\img\\news.txt";

    public static void main(String[] args) {
        DateIncrease();
    }

    public static void writeFile(String content) {
        FileWriter fileWriter = null;

        File file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void DateIncrease() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //起始日期
        try {
            Date startDate = sdf.parse(start_Date);
            //结束日期
            Date endDate = sdf.parse(end_Date);
            Date tempDate = startDate;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            //循环
            while(tempDate.getTime() < endDate.getTime()) {
                tempDate = calendar.getTime();
                String date = sdf.format(tempDate);
                date = date.replace("-", "");
                //2020-03-28	20200328
                String url = url_prefix + date + url_suffix;
                doGet(url);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void doGet(String url) {
        //访问url，获取内容
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(document);
        //筛选出有用数据
        String body = document.select("body").html();
        //System.out.println(body);
        String subString = body.substring(19, body.length()-1);
        //System.out.println(subString);
        JSONObject json = JSONObject.parseObject(subString);
        //System.out.println(json);
        JSONArray jsonArray = json.getJSONArray("data");
        for(Object object : jsonArray) {
            JSONObject news = (JSONObject)object;
            //System.out.println(news);
            String title = news.getString("title");
            String date = news.getString("create_date");
            //System.out.println(date+" "+title);
            writeFile(date+"\t"+title+"\n");
        }
    }
}
