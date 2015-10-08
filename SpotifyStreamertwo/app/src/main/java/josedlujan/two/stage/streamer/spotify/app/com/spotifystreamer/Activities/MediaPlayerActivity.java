package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments.MediaPlayerFragment;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments.TopTrackFragment;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;

/**
 * Created by jose on 04/10/15.
 */
public class MediaPlayerActivity extends AppCompatActivity  {
    boolean largeLayout;
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        largeLayout = getResources().getBoolean(R.bool.large_layout);


        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            int position = extras.getInt("position");
            String name = extras.getString("name");


            bundle.putInt("position", position);
            bundle.putString("name", name);
        }


         MediaPlayerFragment fragment = new MediaPlayerFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_mediaplayer,fragment)
                    .commit();
  //      showMediaPlayer();

    }


   /* @Override
    public void onTrackSelected(int position) {
           /* MediaPlayerFragment fragment = new MediaPlayerFragment();
           // fragment.setArguments(intent);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_mediaplayer,fragment)
                    .commit();
    }*/

    public void showMediaPlayer(){
        Bundle intent = getIntent().getExtras();
        FragmentManager fm = getSupportFragmentManager();

        MediaPlayerFragment fragment = new MediaPlayerFragment();
       // MediaPlayerFragment fragment = MediaPlayerFragment.newInstance();

        // fragment.setArguments(intent);







  /*      if(largeLayout){
          // fragment.show(getActi fm,"fragment");


        }else{
        //    getSupportFragmentManager().beginTransaction()

        //            .add(R.id.container_mediaplayer, fragment)
         //           .addToBackStack(null)
          //          .commit();

        }
*/

    }
}
