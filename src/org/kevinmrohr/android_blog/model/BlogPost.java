package org.kevinmrohr.android_blog.model;

import org.joda.time.DateTime;

import java.io.Serializable;

public class BlogPost implements Serializable {
  public DateTime date;
  public String title;
  public String content;

  public BlogPost() {}

  public BlogPost(String title, String content, DateTime date) {
    this.title = title;
    this.content = content;
    this.date = date;
  }
}