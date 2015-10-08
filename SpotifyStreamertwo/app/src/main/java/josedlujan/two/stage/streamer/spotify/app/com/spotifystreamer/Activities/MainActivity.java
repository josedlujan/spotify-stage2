package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments.SearchFragment;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments.TopTrackFragment;


public class MainActivity extends AppCompatActivity implements SearchFragment.Callbacks {
    public boolean mTwoPane;
    private static final String TOPLIST_TAG = "TOPLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.top_track_list)!=null){
            mTwoPane= true;

            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.top_track_list, new TopTrackFragment(),TOPLIST_TAG)
                        .commit();
            }
        }else{
            mTwoPane = false;
        }


    }

    public boolean getmTwoPane(){
        return this.mTwoPane;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    @Override
    public void onItemSelected(String id, String name) {
        if (mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name", name);
            TopTrackFragment topTrackFragment = new TopTrackFragment();
            topTrackFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.top_track_list,topTrackFragment).
                    commit(); }
        else {
            Intent intent = new Intent(this, TopTrackActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

   /* @Override
    public void onItemSelected(String id, String name) {
       /* if (mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name", name);
            TopTrackFragment topTrackFragment = new TopTrackFragment();
            topTrackFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.top_track_list,topTrackFragment).
                    commit();

        }else{
            Intent intent = new Intent(this,TopTrackActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("name",name);
            startActivity(intent);
        }
        Toast.makeText(getApplicationContext(),"h",Toast.LENGTH_LONG).show();
    }*/
}
