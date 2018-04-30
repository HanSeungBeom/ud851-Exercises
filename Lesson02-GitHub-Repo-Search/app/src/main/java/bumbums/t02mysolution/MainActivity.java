package bumbums.t02mysolution;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import bumbums.t02mysolution.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GitAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_git);
        ArrayList<GitRep> datas = new ArrayList<>();
        mAdapter = new GitAdapter(this, datas);
        URL githubSearchUrl = NetworkUtils.buildUrl("ExoPlayer");

        new GithubQueryTask().execute(githubSearchUrl);
    }
    public class GithubQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            Log.d(TAG,"onPreExcute..");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            Log.d(TAG,"doInBackground..");

            URL searchUrl = params[0];
            String githubSearchResults = null;
            try{
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return githubSearchResults;

/*            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
            return null;*/
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG,"onPostExcute");
            Log.d(TAG,"result:"+s);

            super.onPostExecute(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatwasClickedId = item.getItemId();
        if(itemThatwasClickedId == R.id.action_search){
            Log.d(TAG,"menu clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
