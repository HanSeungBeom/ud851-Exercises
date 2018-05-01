package bumbums.l04exercise;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickOpenWebpageButton(View view) {
        String urlAsString = "http://www.udacity.com";
        openWebPage(urlAsString);
    }

    public void onClickOpenAddressButton(View view) {
        String addressString = "Dobongro 15 40, Seoul";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .query(addressString);
        Uri addressUri = builder.build();

        showMap(addressUri);
    }

    public void onClickShareTextButton(View view) {
        String textToShare = "I'm bumbums";
        shareText(textToShare);
    }

    public void openWebPage(String url){
        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
    public void showMap(Uri uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if(intent.resolveActivity(getPackageManager())!= null)
            startActivity(intent);
    }
    public void shareText(String textToShare){
        String mimeType = "text/plain";
        String title = "Learning How to Share";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }
}
