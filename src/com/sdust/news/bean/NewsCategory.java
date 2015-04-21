package com.sdust.news.bean;

import java.util.List;

public class NewsCategory {
    public  NewCatgoryItem  data;
    public  int retcode;
    public class NewCatgoryItem{
    	public String countcommenturl;
    	public String more;
    	public List<News> news;
    	public String title;
    	public List<Topic> topic;
    	public List<TopNews> topnews;
    }
    public class News{
    	public boolean comment;
    	public String commentlist;
    	public String commenturl;
    	public int id;
    	public String listimage;
    	public String pubdate;
    	public String title;
    	public String type;
    	public String url;
    	public boolean is_read;
    	
    }
    public class Topic{
    	public String description;
    	public int id;
    	public String listimage;
    	public String sort;
    	public String title;
    	public String url;
    }
    public class TopNews{
    	public boolean comment;
    	public String commentlist;
    	public String commenturl;
    	public int id;
    	public String pubdate;
    	public String title;
    	public String topimage;
    	public String type;
    	public String url;
    	
    	
    	
    	
    }
}


























