// package com.immdream.extractnews.commons;
//
// import cn.hutool.json.JSONArray;
// import com.alibaba.druid.support.json.JSONUtils;
// import com.alibaba.fastjson.JSONObject;
// import com.immdream.extractnews.service.INewsService;
// import com.immdream.model.domain.news.News;
// import org.apache.commons.lang.StringUtils;
// import org.jsoup.Connection;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.select.Elements;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import java.io.IOException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.*;
//
// /**
//  * 爬取今日头条
//  * <p>
//  * news com.immdream.extractnews.commons
//  *
//  * @author immDream
//  * @date 2023/05/22/10:52
//  * @since 1.8
//  */
// public class ExtractNews163 {
//
//     @Autowired
//     private INewsService newsService;
//
//     public static void main(String[] args) throws Exception {
//         //爬取条数,10的倍数，网易新闻每10条预留大约2个广告位，所以爬取新闻的真实条数大约为80%
//         int deep = 30;
//         //爬取宽度，0:首页，1:社会，2:国内，3:国际，4:历史
//         int width = 1;
//
//         //网易新闻类型
//         String[] typeArray={"BBM54PGAwangning","BCR1UC1Qwangning","BD29LPUBwangning","BD29MJTVwangning","C275ML7Gwangning"};
//         String type = typeArray[width];
//
//         //网易新闻列表url
//         String url1 = "http://3g.163.com/touch/reconstruct/article/list/";
//         //网易新闻内容url
//         String url2 = "http://3g.163.com/news/article/";
//
//
//         List<String> ids = new ArrayList<>();
//
//         //根据url1，爬取条数，新闻类型获取新闻docid
//         ids = getIds(url1,deep,type);
//         //根据url2，新闻docid获取内容并存储到MongoDB
//         getContent(url2,ids);
//     }
//
//     //设置爬取深度，循环多次获取docid
//     private static List<String> getIds(String url1,int num,String type) {
//         List<String> id = new ArrayList<>();
//         List<String> ids = new ArrayList<>();
//         for (int i=0;i<=num;i+=10){
//             id = getDocid(url1,i,type);
//             ids.addAll(id);
//         }
//         return ids;
//     }
//
//
//     //根据内容url2获取新闻信息并进行存储
//     private static void getContent(String url2, List<String> ids) {
//         System.out.println("存储开始！！");
//         String url = null;
//         Connection connection = Jsoup.connect(url2);
//         int i = 1;
//         for (;i<ids.size();i++){
//             url = url2+ids.get(i)+".html";
//             connection = Jsoup.connect(url);
//             try {
//                 Document document = connection.get();
//                 //获取新闻标题
//                 Elements title = document.select("[class=title]");
//                 //获取新闻来源和文章发布时间
//                 Elements articleInfo = document.select("[class=info]");
//                 Elements src = articleInfo.select("[class=source js-source]");
//                 Elements time = articleInfo.select("[class=time js-time]");
//                 //获取新闻内容
//                 Elements contentEle = document.select("[class=page js-page on]");
//
//                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                 Date date = formatter.parse(time.html());
//
//                 News news = new News();
//                 news.setId(UUID.randomUUID().toString());
//                 news.setNewsTitle(src.html());
//                 news.setNewsLink(src.html());
//                 news.setNewsContent(contentEle.html());
//                 news.setCreateTime(date);
//
//             } catch (IOException | ParseException e) {
//                 e.printStackTrace();
//             }
//         }
//         System.out.println("本次共计存储"+i*0.8+"条数据");
//     }
//
//     //根据新闻列表url，获取新闻docid,并把docid存储到list中
//     private static List<String> getDocid(String url,int num,String type) {
//         String json = null;
//         List<String> id=new ArrayList<>();
//         Map map=null;
//         JSONArray parseArray=null;
//         String jsonStrM="";
//         json = JSONUtils.loadJson(url+type+"/"+num+"-10.html");
//         String jsonStr = StringUtils.substringBeforeLast(json, ")");
//         String jsonStrO = StringUtils.substringAfter(jsonStr,"artiList(");
//         Map parse = (Map) JSONObject.parse(jsonStrO);
//         parseArray = (JSONArray) parse.get(type);
//         for(int j=0;j<parseArray.size();j++){
//             map = (Map)parseArray.get(j);
//             id.add((String) map.get("docid"));
//         }
//         return id;
//     }
// }
