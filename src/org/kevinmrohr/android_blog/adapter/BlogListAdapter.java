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
import org.kevinmrohr.android_blog.model.BlogPostRow;

import java.util.ArrayList;
import java.util.List;

public class BlogListAdapter extends BindingAdapter<BlogPostRow, BlogListAdapter.DetailViewHolder> {
  private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd");
  private static final int MAX_SUMMARY_LEN = 100;
  private List<BlogPostRow> blogPostRows = new ArrayList<BlogPostRow>();

  public BlogListAdapter(Context context, List<BlogPostRow> blogPostRows) {
    super(context);
    this.blogPostRows = blogPostRows;
  }

  public void setData(List<BlogPostRow> data) {
    if (blogPostRows != null) {
      blogPostRows.clear();
    } else {
      blogPostRows = new ArrayList<BlogPostRow>();
    }
    if (data != null) {
      blogPostRows.addAll(data);
    }
    notifyDataSetChanged();
  }

  @Override public View newView(LayoutInflater inflater, int position, ViewGroup container) {
    return (getItem(position) instanceof BlogPost) ?
        inflater.inflate(R.layout.blogpostdetail, null) : inflater.inflate(R.layout.blogpostheader, null);
  }

  @Override public DetailViewHolder buildViewHolder(View view, int position) {
    return (getItem(position) instanceof BlogPost) ? new DetailViewHolder(view) : null;
  }

  @Override public void bindView(BlogPostRow item, int position, View view, DetailViewHolder vh) {
    if (item instanceof BlogPost) {
      BlogPost post = (BlogPost) item;
      vh.blogDate.setText(dtf.print(post.date));

      vh.blogTitle.setText(post.title);

      String summary = post.content.substring(0, Math.min(MAX_SUMMARY_LEN, post.content.length()));
      vh.blogSummary.setText(summary);
    }
  }

  @Override public int getItemViewType(int position) {
    return (getItem(position) instanceof BlogPost) ? ViewType.DETAIL_ITEM.ordinal() : ViewType.HEADER_ITEM.ordinal();
  }

  @Override public int getViewTypeCount() {
    return ViewType.values().length;
  }

  @Override
  public int getCount() {
    return blogPostRows.size();
  }

  @Override
  public BlogPostRow getItem(int i) {
    return blogPostRows.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  public enum ViewType {
    DETAIL_ITEM, HEADER_ITEM
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
