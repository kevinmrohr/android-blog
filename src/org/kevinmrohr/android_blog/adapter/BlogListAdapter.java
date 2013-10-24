package org.kevinmrohr.android_blog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.model.BlogPost;

import java.util.ArrayList;
import java.util.List;

public class BlogListAdapter extends BindingAdapter<BlogPost, BlogListAdapter.DetailViewHolder> {
  private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd");
  private static final int MAX_SUMMARY_LEN = 100;
  private List<BlogPost> blogPostRows = new ArrayList<BlogPost>();

  public BlogListAdapter(Context context, List<BlogPost> blogPostRows) {
    super(context);
    this.blogPostRows = blogPostRows;
  }

  public void setData(List<BlogPost> data) {
    if (blogPostRows != null) {
      blogPostRows.clear();
    } else {
      blogPostRows = new ArrayList<BlogPost>();
    }
    if (data != null) {
      blogPostRows.addAll(data);
    }
    notifyDataSetChanged();
  }

  @Override public View newView(LayoutInflater inflater, int position, ViewGroup container) {
    return inflater.inflate(R.layout.blogpostdetail, null);
  }

  @Override public DetailViewHolder buildViewHolder(View view, int position) {
    return new DetailViewHolder(view);
  }

  @Override public void bindView(BlogPost item, int position, View view, DetailViewHolder vh) {
    vh.blogDate.setText(dtf.print(item.date));

    vh.blogTitle.setText(item.title);

    String summary = item.content.substring(0, Math.min(MAX_SUMMARY_LEN, item.content.length()));
    vh.blogSummary.setText(summary);
  }

  @Override
  public int getCount() {
    return blogPostRows.size();
  }

  @Override
  public BlogPost getItem(int i) {
    return blogPostRows.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  static class DetailViewHolder {
    TextView blogDate;
    TextView blogTitle;
    TextView blogSummary;

    DetailViewHolder(View view) {
      blogDate = (TextView) view.findViewById(R.id.blogdate);
      blogTitle = (TextView) view.findViewById(R.id.blogtitle);
      blogSummary = (TextView) view.findViewById(R.id.blogsummary);
    }
  }
}
