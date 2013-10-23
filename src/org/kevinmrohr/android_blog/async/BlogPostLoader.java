package org.kevinmrohr.android_blog.async;

import android.content.AsyncTaskLoader;
import android.content.Context;
import org.kevinmrohr.android_blog.model.BlogPost;
import org.kevinmrohr.android_blog.service.BlogPostService;

import java.util.List;

public class BlogPostLoader extends AsyncTaskLoader<List<BlogPost>> {
  private BlogPostService service = new BlogPostService();

  public BlogPostLoader(Context context) {
    super(context);
  }

  @Override public List<BlogPost> loadInBackground() {
    return service.getPosts();
  }
}
