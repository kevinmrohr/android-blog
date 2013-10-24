package org.kevinmrohr.android_blog.activity;

import android.app.Activity;
import android.os.Bundle;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.fragments.BlogDetailFragment;
import org.kevinmrohr.android_blog.model.BlogPost;

public class BlogViewActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.blogview);

    BlogPost blogPost = (BlogPost) getIntent().getSerializableExtra("blogPost");

    BlogDetailFragment detailFragment = (BlogDetailFragment) getFragmentManager().findFragmentById(R.id.blogDetailFrag);
    if (detailFragment != null && detailFragment.isInLayout()) {
      detailFragment.update(blogPost);
    } else {
      throw new RuntimeException("Something is very wrong. How can the detail activity not have the detail fragment!?!? Busted!");
    }
  }

}
