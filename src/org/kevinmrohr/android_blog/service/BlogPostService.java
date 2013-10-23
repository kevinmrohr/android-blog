package org.kevinmrohr.android_blog.service;

import org.joda.time.DateTime;
import org.kevinmrohr.android_blog.model.BlogPost;

import java.util.Arrays;
import java.util.List;

public class BlogPostService {

  public List<BlogPost> getPosts() {
    //TODO: Could you imagine if this was a real service and actually hit a restful data service endpoint?
    return Arrays.asList(
        new BlogPost("Android with maven",
            "blah blah blah maven rocks blah blah android this that the other thing.",
            new DateTime().withDate(2013, 9, 20)),
        new BlogPost("Dynamic ListView loading static ListView",
            "Whole lotta talking, not saying much.",
            new DateTime().withDate(2013, 9, 21))
    );
  }
}
