// Generated code from Butter Knife. Do not modify!
package org.kevinmrohr.android_blog.activity;

import android.view.View;
import butterknife.Views.Finder;

public class BlogViewActivity$$ViewInjector {
  public static void inject(Finder finder, final org.kevinmrohr.android_blog.activity.BlogViewActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131034115);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034115' for field 'date' was not found. If this field binding is optional add '@Optional'.");
    }
    target.date = (android.widget.TextView) view;
    view = finder.findById(source, 2131034116);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034116' for field 'title' was not found. If this field binding is optional add '@Optional'.");
    }
    target.title = (android.widget.EditText) view;
    view = finder.findById(source, 2131034117);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034117' for field 'content' was not found. If this field binding is optional add '@Optional'.");
    }
    target.content = (android.widget.EditText) view;
    view = finder.findById(source, 2131034119);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034119' for method 'onSaveClick' was not found. If this method binding is optional add '@Optional'.");
    }
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        target.onSaveClick((android.view.View) view);
      }
    });
    view = finder.findById(source, 2131034118);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034118' for method 'onBackClick' was not found. If this method binding is optional add '@Optional'.");
    }
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        target.onBackClick((android.view.View) view);
      }
    });
  }

  public static void reset(org.kevinmrohr.android_blog.activity.BlogViewActivity target) {
    target.date = null;
    target.title = null;
    target.content = null;
  }
}
