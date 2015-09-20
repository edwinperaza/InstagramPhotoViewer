package com.example.edwinmperazaduran.instagramphotoviewer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "8a2e83656fbc481dacb8dc576b6e49f7";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        photos = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);
        lvPhotos.setAdapter(aPhotos);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });
        fetchPopularPhotos();
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        //Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        //Trigger to Get the request
        client.get(url, null, new JsonHttpResponseHandler(){
            //onSuccess (worked, 200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    // Remember to CLEAR OUT old items before appending in the new ones
                    aPhotos.clear();
                    for (int i=0; i < photosJSON.length(); i++){
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        //extract the caption only if the caption is not null
                        if (photoJSON.optJSONObject("caption") != null) {
                            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                            photo.setCreatedTime(photoJSON.getJSONObject("caption").getLong("created_time"));
                        }
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));
                        photo.setProfileUrl(photoJSON.getJSONObject("user").getString("profile_picture"));
                        photo.setCommentCount(photoJSON.getJSONObject("comments").getInt("count"));
                        //extract comments only if is not null
                        if (photoJSON.getJSONObject("comments") != null){
                            JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                            for (int x=0; x < commentsJSON.length() && commentsJSON != null; x++ ){
                                InstagramComment comment = new InstagramComment();
                                JSONObject data = commentsJSON.getJSONObject(x);
                                comment.setText(data.getString("text"));
                                comment.setCommentUserName(data.getJSONObject("from").getString("username"));
                                photo.comments.add(comment);
                            }
                        }
                        photos.add(photo);
                        swipeContainer.setRefreshing(false);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
