package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Activities.MainActivity;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Adapters.ArtistSearch;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by jose on 28/09/15.
 */
public class SearchFragment extends Fragment {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText editText;
    ListView listView,myListView;
    SearchArtistTask myCurrentTask;
    List<Artist> myArtists,myArtistsP;
    Callbacks callbacks = null;
    public static String artistName;

    public interface  Callbacks{
       public void onItemSelected(String id,String name);
    }

    //Callbacks mCallback = DummyCallbacks;

   /* private static Callbacks DummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id, String name) {

        }
    };*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        editText = (EditText) rootView.findViewById(R.id.search);
        listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        //Toast.makeText(getActivity().getApplicationContext(),"kk",Toast.LENGTH_SHORT).show();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchArtist();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }


    public void onDestroy(){
        super.onDestroy();
    }

    public void onPause(){
        super.onPause();
        getFragmentManager().findFragmentByTag("search_fragment");
    }

    public   void onResume(){
        super.onResume();
        getFragmentManager().findFragmentByTag("search_fragment");
    }

    public void onAttach(AppCompatActivity activity){
        super.onAttach(activity);
       /* try{
           mCallback = (Callbacks) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+"must implement OnItemSelected");
        }*/

 //       if(!(activity instanceof Callbacks)){
 //           throw new IllegalStateException("Activity must implements fragments callbacks");
 //       }
       // mCallback = (Callbacks) activity;
    }

    public void onDetach(){
        super.onDetach();
       // mCallback = DummyCallbacks;
    }


    public void searchArtist(){

        myCurrentTask= new SearchArtistTask();
        myCurrentTask.execute(editText.getText().toString());
    }



    public class SearchArtistTask extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(String... params) {
            String query = params[0];

            try {
                Thread.sleep(400);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

            if(isCancelled() || query.length()==0)
                return  new ArrayList<>();

            try {
                SpotifyApi spo = new SpotifyApi();
                SpotifyService spotifyService = spo.getService();
                ArtistsPager results = spotifyService.searchArtists(query);
                List<Artist> artists = results.artists.items;

                for(int i=0;i<artists.size();i++){
                    Artist artist = artists.get(i);
                    Log.i(LOG_TAG, i + "" + artist.name);
                }

                return results.artists.items;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);

            if(artists == null)
                Toast.makeText(getActivity().getApplicationContext(),R.string.artist_search_network, Toast.LENGTH_SHORT).show();

            if((artists.size() == 0) && (editText.getText().length()>0))
                Toast.makeText(getActivity().getApplicationContext(),R.string.artist_search,Toast.LENGTH_SHORT).show();


            myArtists = artists;
            fillListView();
        }
    }

    public void fillListView(){

        final ArtistSearch artistSearch = new ArtistSearch(getActivity().getApplicationContext());
        artistSearch.setArtisList(myArtists);

        listView.setAdapter(artistSearch);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistSearch.getItem(position);
                view.setSelected(true);
                ((Callbacks)getActivity()).onItemSelected(artist.id,artist.name);
              //  callbacks.onItemSelected("1uNFoZAHBGtllmzznpCI3s","jojo");
      //         callbacks.onItemSelected(artist.id, artist.name);
//                Toast.makeText(getActivity().getApplicationContext(),"h",Toast.LENGTH_LONG).show();



               /* Intent intent = new Intent(getActivity().getApplicationContext(),TopTrackActivity.class);
                intent.putExtra("id",artist.id);
                intent.putExtra("name", artist.name);
                Bundle bundle = new Bundle();
                bundle.putString("id",artist.id);
                bundle.putString("name",artist.name);
                //mListener.

                startActivity(intent);

                //if(mtw)*/
            }
        });

    }
}
