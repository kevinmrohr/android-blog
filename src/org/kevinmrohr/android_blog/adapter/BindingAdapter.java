package org.kevinmrohr.android_blog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * An implementation of {@link android.widget.BaseAdapter} which uses the new/bind pattern and
 * view holder pattern for its views.
 * <p/>
 * Inspired by/baesd on Jake Wharton's BindingAdapter:
 * https://gist.github.com/JakeWharton/5423616
 * <p/>
 * Pulled from Patrick Hammond's https://gist.github.com/patrickhammond/6094827
 */
public abstract class BindingAdapter<T, S> extends BaseAdapter {
  private final Context context;
  private final LayoutInflater inflater;

  public BindingAdapter(Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  public Context getContext() {
    return context;
  }

  @Override public abstract T getItem(int position);

  @Override public final View getView(int position, View view, ViewGroup container) {
    if (view == null) {
      view = newView(inflater, position, container);
      if (view == null) {
        throw new IllegalStateException("newView result must not be null.");
      }

      associateViewHolder(view, position);
    }

    bindView(getItem(position), position, view, getViewHolder(view));
    return view;
  }

  /**
   * Create a new instance of a view for the specified position.
   */
  public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

  /**
   * If your ViewHolder implementation looks something like this:
   * <pre>
   * {@code
   * static class ViewHolder {
   *     final TextView textView;
   *
   *     ViewHolder(View view) {
   *         textView = (TextView) view.findViewById(R.id.textView);
   *     }
   * }
   * </pre>
   * <p/>
   * This method only needs this as its implementation:
   * <pre>
   * {@code
   * return new ViewHolder(view);
   * }
   * </pre>
   * <p/>
   * If implementations do not need/want a view holder, just return <code>null</code>.
   */
  public abstract S buildViewHolder(View view, int position);

  /**
   * Bind the data for the specified {@code position} to the view using a
   * {@code viewHolder} created from {@link #buildViewHolder(android.view.View, int)}.
   */
  public abstract void bindView(T item, int position, View view, S vh);

  @Override public final View getDropDownView(int position, View view, ViewGroup container) {
    if (view == null) {
      view = newDropDownView(inflater, position, container);
      if (view == null) {
        throw new IllegalStateException("newDropDownView result must not be null.");
      }

      associateViewHolder(view, position);
    }

    bindDropDownView(getItem(position), position, view, getViewHolder(view));
    return view;
  }

  private void associateViewHolder(View view, int position) {
    S viewHolder = buildViewHolder(view, position);
    view.setTag(viewHolder);
  }

  @SuppressWarnings("unchecked")
  private S getViewHolder(View view) {
    return (S) view.getTag();
  }

  /**
   * Create a new instance of a drop-down view for the specified position.
   */
  public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
    return newView(inflater, position, container);
  }

  /**
   * Bind the data for the specified {@code position} to the drop-down view.
   */
  public void bindDropDownView(T item, int position, View view, S viewHolder) {
    bindView(item, position, view, viewHolder);
  }
}
