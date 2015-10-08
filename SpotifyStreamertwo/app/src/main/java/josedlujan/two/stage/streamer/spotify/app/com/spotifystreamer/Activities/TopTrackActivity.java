package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;


import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments.TopTrackFragment;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jose on 29/09/15.
 */
public class TopTrackActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toptrack);

        Bundle intent = getIntent().getExtras();


        if (savedInstanceState == null ){
            Bundle bundle = new Bundle();
            getSupportActionBar().setSubtitle(intent.getString("name"));

            TopTrackFragment fragment = new TopTrackFragment();

            fragment.setArguments(intent);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_track_list, fragment)
                    .commit();
        }

       // Toast.makeText(this,"jjjj",Toast.LENGTH_SHORT).show();
    }



   /* @Override
    public void onTrackSelected( int position) {
        Intent intent = new Intent(this,MediaPlayerActivity.class);
        startActivity(intent);
    }*/
}
