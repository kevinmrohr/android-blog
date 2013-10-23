// Generated code from Butter Knife. Do not modify!
package org.kevinmrohr.android_blog.activity;

import android.view.View;
import butterknife.Views.Finder;

public class ListBlogsActivity$$ViewInjector {
  public static void inject(Finder finder, final org.kevinmrohr.android_blog.activity.ListBlogsActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131034120);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034120' for field 'blogPostsListView' was not found. If this field binding is optional add '@Optional'.");
    }
    target.blogPostsListView = (android.widget.ListView) view;
  }

  public static void reset(org.kevinmrohr.android_blog.activity.ListBlogsActivity target) {
    target.blogPostsListView = null;
  }
}
