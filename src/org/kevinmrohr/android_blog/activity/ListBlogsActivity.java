package org.kevinmrohr.android_blog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.fragments.BlogDetailFragment;
import org.kevinmrohr.android_blog.fragments.BlogListFrag;
import org.kevinmrohr.android_blog.model.BlogPost;

public class ListBlogsActivity extends Activity implements BlogListFrag.BlogClickListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  @Override public void onClick(BlogPost blogPost) {
    BlogDetailFragment detailFrag = (BlogDetailFragment) getFragmentManager().findFragmentById(R.id.blogDetailFrag);
    if (detailFrag != null && detailFrag.isInLayout()) {
      detailFrag.update(blogPost);
    } else {
      Intent i = new Intent(this, BlogViewActivity.class);
      i.putExtra("blogPost", blogPost);
      startActivity(i);
      finish();
    }
  }
}
