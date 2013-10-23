package org.kevinmrohr.android_blog.util;

import org.kevinmrohr.android_blog.model.BlogPost;
import org.kevinmrohr.android_blog.model.BlogPostRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krohr
 * Date: 10/21/13
 * Time: 2:50 PM
 */
public class BlogListUtil {

  public static List<BlogPostRow> prependHeader(List<BlogPost> blogPosts) {
    List<BlogPostRow> blogPostRows = new ArrayList<BlogPostRow>();
    blogPostRows.add(new BlogPostRow() {});
    blogPostRows.addAll(blogPosts);
    return blogPostRows;
  }
}
