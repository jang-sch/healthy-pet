package finalproject.mwisehart.youtubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandaloneActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch(view.getId()){
            case R.id.btnPlayVideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(this, YoutubeActivity.GOOGLE_API_KEY, YoutubeActivity.YOUTUBE_VIDEO_ID);
                break;

            case R.id.btnPlayList:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this, YoutubeActivity.GOOGLE_API_KEY, YoutubeActivity.YOUTUBE_PLAYLIST);
                break;

            default:

        }
        if(intent != null) {
            startActivity(intent);
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);

        //might need to cast the buttons
        //Button btnPlayVideo = (Button) findViewById(R.id.btnPlayVideo);
        Button btnPlayVideo = (Button) findViewById(R.id.btnPlayVideo);
        Button btnPlayList = (Button) findViewById(R.id.btnPlayList);

        btnPlayVideo.setOnClickListener(this);
        btnPlayList.setOnClickListener(this);
    }
}
