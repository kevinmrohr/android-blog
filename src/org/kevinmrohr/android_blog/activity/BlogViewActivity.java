package org.kevinmrohr.android_blog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.model.BlogPost;

public class BlogViewActivity extends Activity {
  public static final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss");
  @InjectView(R.id.blogviewdate) TextView date;
  @InjectView(R.id.blogviewtitle) EditText title;
  @InjectView(R.id.blogviewcontent) EditText content;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.blogview);

    Views.inject(this);

    BlogPost blogPost = (BlogPost) getIntent().getSerializableExtra("blogPost");

    date.setText(dtf.print(blogPost.date));
    title.setText(blogPost.title);
    content.setText(blogPost.content);
  }

  @OnClick(R.id.saveButton)
  public void onSaveClick(View view) {
    Log.d("onSaveClick", "Saved blog!");
  }

  @OnClick(R.id.backButton)
  public void onBackClick(View view) {
    Intent i = new Intent(view.getContext(), ListBlogsActivity.class);
    startActivity(i);
    finish();
  }

}
