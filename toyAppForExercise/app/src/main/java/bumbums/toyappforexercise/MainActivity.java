package bumbums.toyappforexercise;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import bumbums.toyappforexercise.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private EditText mInput;
    private TextView mResultView;
    private ProgressBar mProgressBar;
    private static AsyncTaskLoader mLoader;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SEARCH_RESULT = "search_result";
    private static final String SEARCH_QUERY_URL = "search_query_url";
    private static final int GITHUB_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText)findViewById(R.id.et_search);
        mResultView = (TextView)findViewById(R.id.tv_result);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading);

        /* this is for data loss get data from savedInstanceState */

        if(savedInstanceState != null){
            String savedString = savedInstanceState.getString(SEARCH_RESULT);
            Log.d(TAG,savedString);
            mResultView.setText(savedString);
        }

    }

    public void searchInGithub(String input){
        URL url = NetworkUtils.buildUrl(input);
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_QUERY_URL,url.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader githubSearchLoader = loaderManager.getLoader(GITHUB_LOADER);

        if(githubSearchLoader == null){
            Log.d(TAG,"gitgubSearchLoader is null");
            loaderManager.initLoader(GITHUB_LOADER,bundle,this);
        }
        else{
            Log.d(TAG,"gitgubSearchLoader is not null");
            loaderManager.restartLoader(GITHUB_LOADER,bundle,this);
        }

    }

    public static class GitHubAsyncLoader extends AsyncTaskLoader<String>{
        private Bundle bundle;

        public GitHubAsyncLoader(Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        protected void onStartLoading() {
            Log.d(TAG,"onStartLoading..");
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            Log.d(TAG,"loadInBackground..");
            if(bundle == null)
                return null;

            String url = bundle.getString(SEARCH_QUERY_URL);
            if(url == null || TextUtils.isEmpty(url))
                return null;

            try {
                URL githubURL = new URL(url);
                return NetworkUtils.getResponseFromHttpUrl(githubURL);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String result = mResultView.getText().toString();
        outState.putCharSequence(SEARCH_RESULT,result);
    }

    @Override
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
        Log.d(TAG,"onCreateLoader..");

        mProgressBar.setVisibility(View.VISIBLE);
        return new GitHubAsyncLoader(this,bundle);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String result) {
        Log.d(TAG,"onLoadFinished..");
        mProgressBar.setVisibility(View.INVISIBLE);
        if(result != null && !result.equals(""))
            mResultView.setText(result);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.d(TAG,"onLoadReset..");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            String input = mInput.getText().toString();
            searchInGithub(input);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
