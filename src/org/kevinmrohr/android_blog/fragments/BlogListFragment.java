package org.kevinmrohr.android_blog.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import dagger.ObjectGraph;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.kevinmrohr.android_blog.R;
import org.kevinmrohr.android_blog.adapter.BlogListAdapter;
import org.kevinmrohr.android_blog.model.BlogPost;
import org.kevinmrohr.android_blog.module.ObjectMappingModule;

import javax.inject.Inject;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
import static org.kevinmrohr.android_blog.fragments.BlogListFragment.SortColumn.*;

/**
 * User: krohr
 * Date: 10/24/13
 * Time: 12:56 PM
 */
public class BlogListFragment extends Fragment {
  @Inject ObjectMapper mapper;
  @InjectView(R.id.blogposts) ListView blogPostsListView;

  public static final String SORT_COL_KEY = "sortCol";
  public static final String SORT_ASC_KEY = "sortAsc";
  public static final String SELECTED_IDX_KEY = "selectedBlogIndex";
  private RequestQueue mRequestQueue;
  private BlogClickListener listener;

  private static final Map<SortColumn, Comparator<BlogPost>> blogSorters = new HashMap<SortColumn, Comparator<BlogPost>>();
  private boolean sortAsc = true;
  private SortColumn sortCol = DATE;

  private int selectedBlogIndex = 0;
  private List<BlogPost> blogPosts;
  private BlogListAdapter blogListAdapter;

  static {
    blogSorters.put(DATE, new DateComparator());
    blogSorters.put(SUMMARY, new ContentComparator());
    blogSorters.put(TITLE, new TitleComparator());
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bloglistfrag, container, false);

    ObjectGraph.create(new ObjectMappingModule()).inject(this);
    Views.inject(this, view);

    if (savedInstanceState != null && savedInstanceState.get(SORT_COL_KEY) != null) {
      sortCol = (SortColumn) savedInstanceState.getSerializable(SORT_COL_KEY);
    }
    if (savedInstanceState != null && savedInstanceState.get(SORT_ASC_KEY) != null) {
      sortAsc = savedInstanceState.getBoolean(SORT_ASC_KEY);
    }
    if (savedInstanceState != null && savedInstanceState.get(SELECTED_IDX_KEY) != null) {
      selectedBlogIndex = savedInstanceState.getInt(SELECTED_IDX_KEY);
    }

    //Initiate the Volley request queue
    mRequestQueue = Volley.newRequestQueue(getActivity());

    blogListAdapter = new BlogListAdapter(this.getActivity(), new ArrayList<BlogPost>());
    blogPostsListView.setAdapter(blogListAdapter);

    initBlogListClickListener();
    getBlogPosts();
    return view;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof BlogClickListener) {
      listener = (BlogClickListener) activity;
    } else {
      throw new RuntimeException("activity must be a BlogClickListener!");
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(SORT_COL_KEY, sortCol);
    outState.putBoolean(SORT_ASC_KEY, sortAsc);
    outState.putInt(SELECTED_IDX_KEY, selectedBlogIndex);
  }

  @Override public void onStop() {
    super.onStop();
    mRequestQueue.cancelAll(this);
  }

  @OnClick(R.id.dateHeading)
  public void sortByDate(View view) {
    sortBlogs(DATE);
  }

  @OnClick(R.id.titleHeading)
  public void sortByTitle(View view) {
    sortBlogs(TITLE);
  }

  @OnClick(R.id.summaryHeading)
  public void sortBySummary() {
    sortBlogs(SUMMARY);
  }

  private void getBlogPosts() {
    mRequestQueue.add(new JsonArrayRequest(getString(R.string.rest_base_url) + "/BlogPosts.json",
        new Response.Listener<JSONArray>() {
          @Override public void onResponse(JSONArray response) {
            try {
              blogPosts = mapper.readValue(response.toString(), new TypeReference<List<BlogPost>>() {});

              sort(blogPosts, sortAsc ? blogSorters.get(sortCol) : reverseOrder(blogSorters.get(sortCol)));
              blogListAdapter.setData(blogPosts);
              if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                blogPostsListView.setSelection(selectedBlogIndex);
                listener.onClick(blogPosts.get(selectedBlogIndex));
              }
            } catch (Exception e) {
              throw new RuntimeException("Failed!", e);
            }
          }
        },
        new Response.ErrorListener() {
          @Override public void onErrorResponse(VolleyError error) {
            Log.e("ListBlogsActivity.onCreate()", "Volley failed to get BlogPosts! " + error.toString());
          }
        }
    ));
  }

  private void initBlogListClickListener() {
    blogPostsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Position 0 is the header. Don't do anything if that is clicked.
        Log.d("ItemClickListener", view.toString());

        //subtract 1 because the header is the first one, which is not accounted for in the blogPosts list.
        BlogPost bp = blogPosts.get(position);

        listener.onClick(bp);
      }
    });
  }

  public interface BlogClickListener {
    void onClick(BlogPost blogPost);
  }

  private void sortBlogs(SortColumn selectedColumn) {
    if (sortCol == selectedColumn) {
      //reverse the sort
      sortAsc = !sortAsc;
    } else {
      sortCol = selectedColumn;
      sortAsc = true;
    }
    sort(blogPosts, sortAsc ? blogSorters.get(sortCol) : reverseOrder(blogSorters.get(sortCol)));
    blogListAdapter.setData(blogPosts);
  }

  public static enum SortColumn {
    DATE, TITLE, SUMMARY
  }

  static class DateComparator implements Comparator<BlogPost> {
    @Override public int compare(BlogPost lhs, BlogPost rhs) {
      return lhs.date.compareTo(rhs.date);
    }
  }

  static class TitleComparator implements Comparator<BlogPost> {
    @Override public int compare(BlogPost lhs, BlogPost rhs) {
      return lhs.title.compareTo(rhs.title);
    }
  }

  static class ContentComparator implements Comparator<BlogPost> {
    @Override public int compare(BlogPost lhs, BlogPost rhs) {
      return lhs.content.compareTo(rhs.content);
    }
  }
}
