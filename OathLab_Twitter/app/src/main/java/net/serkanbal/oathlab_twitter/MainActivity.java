package net.serkanbal.oathlab_twitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String CONSUMER_KEY = "gKOs6rrJhWoH73ZHTjyy33QFH";
    public static final String CONSUMER_SECRET = "0LF56WARaf02v1KilCfBnpCZAsG2HBAnPAdLQu377FO7BA7vVn";
    public String mScreenName = "MargotRobbie";
    List<TwitterObject> mList = new ArrayList<>();
    EditText mScreenNameEntry;
    Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBearerToken(encodeKeys(CONSUMER_KEY, CONSUMER_SECRET));

        mSearch = (Button) findViewById(R.id.search_button);
        mScreenNameEntry = (EditText) findViewById(R.id.search_query);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScreenName = mScreenNameEntry.getText().toString();
                getBearerToken(encodeKeys(CONSUMER_KEY, CONSUMER_SECRET));
            }
        });

    }


    // Encodes the consumer key and secret to create the basic authorization key
    private String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");

            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
            return new String(encodedBytes);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public void getBearerToken(String encoded) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        final Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + encoded)
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .post(requestBody)
                .url("https://api.twitter.com/oauth2/token")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("on Response " + response);
                } else {
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        String bearerToken = obj.getString("access_token");
                        Log.d("Access_Token", "Your Access Token is: " + bearerToken);

                        getTweets(bearerToken, mScreenName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getTweets(String bearerToken, String screenName) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + bearerToken)
                .url("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" +
                        screenName + "&count=20")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) {
                    throw new IOException("Problem at getting tweets" + response);
                } else {
                    try {
                        JSONArray rootArray = new JSONArray(response.body().string());
                        mList.clear();
                        for (int i = 0; i < rootArray.length(); i++) {
                            JSONObject obj = rootArray.getJSONObject(i);
                            String timestamp = obj.getString("created_at");
                            String text = obj.getString("text");
                            mList.add(new TwitterObject(timestamp, text));
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                                        LinearLayoutManager.VERTICAL,false);

                                recyclerview.setLayoutManager(linearLayoutManager);

                                RecyclerView.Adapter adapter = new TwitterAdapter(mList);
                                recyclerview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
