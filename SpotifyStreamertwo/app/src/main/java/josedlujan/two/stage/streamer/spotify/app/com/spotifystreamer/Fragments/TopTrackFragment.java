package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities.MainActivity;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities.MediaPlayerActivity;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Adapters.TopTrackSearch;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.ParcelableArray;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by jose on 28/09/15.
 */
public class TopTrackFragment extends Fragment implements MediaPlayerFragment.CallbacksOnTracks{
    SearchTopTracks myCurrentTask;
    public static List<Track> myTopTrackList;
    public ArrayList<ArrayList<String>> data;
    public static TopTrackSearch topTrackSearch;
    public String artistName;

    //private CallbacksOnTracks myCallbacksOnTracks;

    private static TopTrackSearch mTopTrackSearch;
    ListView listView;
    ParcelableArray parcelableArray;

    String id;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.toptrack_fragment,container,false);
        Bundle bundle = getArguments();

       if (bundle!=null){
           id = (String) bundle.get("id");
           artistName = (String) bundle.get("name");
           searchTopTracks();
       }

        Toast.makeText(getActivity().getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        listView = (ListView)rootView.findViewById(R.id.listtoptrack);


       // id =  getActivity().getIntent().getStringExtra("id");

        return rootView;
    }

    public void onResume(){
        super.onResume();

       // getFragmentManager().findFragmentByTag("fragment_toptrack");
    }

    public void onPause(){
        super.onPause();
     //   getFragmentManager().findFragmentByTag("fragment_toptrack");
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        /*try{
            myCallbacksOnTracks = (CallbacksOnTracks) activity;

        }catch (ClassCastException e ){
        throw  new ClassCastException(activity.toString()+"must implement CallBacksOnTracks");
        }
*/
    }

    public void searchTopTracks(){
        myCurrentTask = new SearchTopTracks();
        myCurrentTask.execute(id);
    }

    @Override
    public void onTrackSelected(int position) {

    }


    public class SearchTopTracks extends AsyncTask<String, Void, List<Track>> {

        @Override
        protected List<Track> doInBackground(String... params) {
            Map<String, Object> options = new HashMap<>();
            options.put("country", "US");

            try{
                SpotifyApi api = new SpotifyApi();
                SpotifyService spo = api.getService();
                Tracks tracks = spo.getArtistTopTrack(id,options);

                return tracks.tracks;




            }catch (Exception e){
                e.printStackTrace();
                return null;
            }


        }
        protected  void onPostExecute(List<Track> trackList){
            super.onPostExecute(trackList);
            if(trackList == null){
                Toast.makeText(getActivity().getApplicationContext(), R.string.toptrack_search_network, Toast.LENGTH_SHORT).show();
            }

            if(trackList.size() == 0)
                Toast.makeText(getActivity().getApplicationContext(),R.string.toptrack_search,Toast.LENGTH_SHORT).show();


            myTopTrackList = trackList;

            fillTopTracks();



        }
        public void fillTopTracks(){

            //TopTrackSearch topTrackSearch = new TopTrackSearch(getActivity().getApplicationContext());
            topTrackSearch = new TopTrackSearch(getActivity().getApplicationContext());
            topTrackSearch.setMyTopTrackList(myTopTrackList);
            listView.setAdapter(topTrackSearch);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   MediaPlayerFragment fragment = MediaPlayerFragment.getInstance(TopTrackFragment.this);
 // LL                  boolean var = ((MainActivity)getActivity()).getmTwoPane(); //UUUUU
                   //if(getActivity())
                    //String artistName =
                    //topTrackSearch.getItem(position).get
                    Bundle bundle = new Bundle();

                    bundle.putInt("position", position);
                    bundle.putString("name",artistName);
                  // int pos = position;
                   // bundle.putInt("position",pos);
                    //String trackId = getTopTrackSearch().getItem(position).id;
     /*Buena               bundle.putString("trackId",topTrackSearch.getItem(position).id);
                   bundle.putString("artistName", topTrackSearch.getItem(position).name);
                   bundle.putString("previewUrl",topTrackSearch.getItem(position).preview_url);
                   bundle.putLong("trackDuration", topTrackSearch.getItem(position).duration_ms);
    buena*/

                 /*   final Track track = getItem(position);
                    if((track.album.images != null) && (track.album.images.size()>0)){
                        for (Image currentImage : track.album.images) {
                            if (currentImage.width >= 200) {
                                image = currentImage;
                            }
                        }*/
                 //   Image image =  topTrackSearch.getItem(position).album.images;


                    if(getResources().getBoolean(R.bool.large_layout)){
                        FragmentManager fm = getFragmentManager();
                        MediaPlayerFragment fragment = new MediaPlayerFragment();
                        fragment.setArguments(bundle);
                        fragment.setRetainInstance(true);
                        fragment.show(fm, "dialog");
                    }else{
                          Intent intent = new Intent(getActivity(),MediaPlayerActivity.class);
                            intent.putExtra("position",position);
                            intent.putExtra("name",artistName);
                          startActivity(intent);
                    }

                  //  Intent intent = new Intent(getActivity(),MediaPlayerActivity.class);
                  //  startActivity(intent);

              /*     Bundle bundle = new Bundle();
                    bundle.putString("name","justin");
                    FragmentManager fm = getFragmentManager();
                   // MediaPlayerFragment fragment = new MediaPlayerFragment();
                    fragment.setArguments(bundle);
                    fragment.setRetainInstance(true);
                    fragment.show(fm, "dialog");

              */
                   // myCallbacksOnTracks.onTrackSelected(position);
                    //myCallbacksOnTracks.onTrackSelected(mArtist,mTracks,position);

                    // Intent intent = new Intent(getActivity().getApplicationContext(),MusicPlayerFragment.class);
                    // String preview = TopTrackSearch.getItem(position).preview_url;
                   // Bundle args = new Bundle();

//                     String trackId = getTopTrackSearch().getItem(position).id;


            //1        Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
            // 1       startActivity(intent);
                  //  ((Callbacks)getActivity()).onTrackSelected(position);
                  /*
                    ((Callbacks)getActivity()).onTrackSelected(position);
                  */
                   // String trackId = TopTrackSearch.getItem(position).preview_url;


                    /*Bundle bundle = new Bundle();
                    bundle.putString("trackId",trackId);
                    bundle.putInt("position",position);
                    FragmentManager fm = getFragmentManager();

                    MediaPlayerFragment fragment = new MediaPlayerFragment();
                    fragment.setArguments(bundle);
                    fragment.setRetainInstance(true);
                    fragment.show(fm, "dialog");*/


                /*    Tracks tracks = new Tracks();
                    tracks.tracks = myTopTrackSearch.getTracks();

                    Intent intent = new Intent(getActivity(),MediaPlayerActivity.class);
                    intent.putExtra(MediaPlayerFragment.TOP_TRACKS_KEY, (Parcelable) tracks);
                    startActivity(intent);
                */



                    //Trac

                    //  args.putInt("position", position);


                    //(  args.putString(TopTrackSearch.getItem(position).preview_url);


                /*    FragmentManager fm = getFragmentManager();

                    MusicPlayerFragment fragment = new MusicPlayerFragment();
                    fragment.setArguments(args);
                    fragment.setRetainInstance(true);
                    fragment.show(fm, "dialog");
                    */

                }
            });



        }
    }


}