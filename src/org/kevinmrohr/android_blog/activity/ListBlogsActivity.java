package org.kevinmrohr.android_blog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
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
import org.kevinmrohr.android_blog.module.ListBlogsModule;
import org.kevinmrohr.android_blog.util.BlogListUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.kevinmrohr.android_blog.util.BlogListUtil.prependHeader;

public class ListBlogsActivity extends Activity {
  @Inject ObjectMapper mapper;
  @InjectView(R.id.blogposts) ListView blogPostsListView;

  private RequestQueue mRequestQueue;
  private List<BlogPost> blogPosts;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    //Put Dagger and Butterknife into action
    ObjectGraph.create(new ListBlogsModule()).inject(this);  //Dagger
    Views.inject(this);                                      //Butterknife

    //Initiate the Volley request queue
    mRequestQueue = Volley.newRequestQueue(this);

    BlogListAdapter blogListAdapter = new BlogListAdapter(this, prependHeader(new ArrayList<BlogPost>()));
    blogPostsListView.setAdapter(blogListAdapter);

    getBlogPosts(blogListAdapter);

    initBlogListClickListener();
  }

  private void getBlogPosts(final BlogListAdapter blogListAdapter) {
    mRequestQueue.add(new JsonArrayRequest(getString(R.string.rest_base_url) + "/BlogPosts.json",
        new Response.Listener<JSONArray>() {
          @Override public void onResponse(JSONArray response) {
            try {
              blogPosts = mapper.readValue(response.toString(), new TypeReference<List<BlogPost>>() {});
              blogListAdapter.setData(BlogListUtil.prependHeader(blogPosts));
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
        if (position > 0) {
          Log.d("ItemClickListener", view.toString());

          //subtract 1 because the header is the first one, which is not accounted for in the blogPosts list.
          BlogPost bp = blogPosts.get(position - 1);
          Intent i = new Intent(view.getContext(), BlogViewActivity.class);
          //Add the selected blog post to the intent
          i.putExtra("blogPost", bp);

          startActivity(i);
          finish();
        }
      }
    });
  }

  @Override protected void onStop() {
    super.onStop();
    mRequestQueue.cancelAll(this);
  }
}
