package org.kevinmrohr.android_blog.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.activity.ListBlogsActivity;
import org.kevinmrohr.android_blog.model.BlogPost;

/**
 * User: krohr
 * Date: 10/24/13
 * Time: 1:09 PM
 */
public class BlogDetailFragment extends Fragment {
  public static final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss");
  @InjectView(R.id.blogviewtitle) EditText title;
  @InjectView(R.id.blogviewdate) TextView date;
  @InjectView(R.id.blogviewcontent) EditText content;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.blogviewfrag, container, false);

    Views.inject(this, view);

    return view;
  }

  public void update(BlogPost blogPost) {
    title.setText(blogPost.content);
    date.setText(dtf.print(blogPost.date));
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
    getActivity().finish();
  }
}
