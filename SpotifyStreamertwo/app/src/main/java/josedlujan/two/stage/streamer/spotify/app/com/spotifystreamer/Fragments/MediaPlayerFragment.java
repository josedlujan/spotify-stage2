package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Streamer;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jose on 04/10/15.
 */
public class MediaPlayerFragment extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Runnable {
    public static final String TOP_TRACKS_KEY = "josedlujan.two.stage.streamer.spotify.app.com.top.tracks";
    String id;
    int position,timerA=0,timerB=0;
    TextView artistname,albumname,time,realtime,trackname;
    ImageView albumart,previus,play,next;
    SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public List<Track> MTopTrackList;
    private CallbacksOnTracks myCallbacksOnTracks;
    String trackId,artistName,previewUrl;
    long duration;
    boolean playflag,playflagA = false;
    boolean playing= true;
    Thread thread;
    private Handler handler = new Handler();
    boolean threadStop = false;
    boolean threadPause = false;
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser)
                mediaPlayer.seekTo(progress);


        time.setText("0:" + timerA + timerB);

        if (timerB == 9) {
            timerA = timerA + 1;
            timerB = 0;
        } else {
            timerB = timerB + 1;

        }


/*FO        if (timerB == 9) {
            timerA = timerA + 1;
            timerB = 0;
        } else {
            timerB = timerB + 1;


            time.post(new Runnable() {
                @Override
                public void run() {
                    time.setText("0:" + timerA + timerB);
                }
            });
        }
        FO*/
      //  thread = new Thread(){
        //    @Override
          //  public void run(){

            //private Runnable update = new Runnable() {
              //  @Override
               // public void run() {
      //  final Handler handler = new Handler();
       // handler.postDelayed(new Runnable() {
         ////   @Override
          //  public void run() {

        //    }
      //  },100);

                //}
           // };

        //  }


        //};

       // thread.start();


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void run() {
        int currentPosition=0;
        int total = mediaPlayer.getDuration();
        while (mediaPlayer!=null && currentPosition<total){
            try {
                if (threadStop){
                   Thread.currentThread().interrupt();
                    threadStop=false;
                }

               /* if(threadPause){
                    Thread.currentThread().wait();
                }*/
                    Thread.sleep(1000);
                    Log.d("currenPosition", String.valueOf(playing));

                    //seekBar.setProgress(currentPosition);

                currentPosition = mediaPlayer.getCurrentPosition();
                if(!playing) {
                    currentPosition = 0;
                    //playing=true;
                }
/*
                if(!playing){
                    currentPosition=0;
                    //seekBar.setProgress(currentPosition);
                    playing=true;
                }*/
            }catch (InterruptedException e){
                return;
            }catch (Exception e){

            }

            seekBar.setProgress(currentPosition);


        }

        /*if(!playing){
            currentPosition=0;
            seekBar.setProgress(currentPosition);
            playing=true;
        }*/


    }


    public interface CallbacksOnTracks {
        //  public void onTrackSelected(Artist artist, List<Track> tracks, int position);
        public void onTrackSelected(int position);
    }


    public static MediaPlayerFragment getInstance(CallbacksOnTracks call){
            MediaPlayerFragment fragment = new MediaPlayerFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.mediaplayer_fragment,container,false);

        artistname = (TextView) rootView.findViewById(R.id.artistname);
        albumname = (TextView) rootView.findViewById(R.id.albumname);
        trackname = (TextView) rootView.findViewById(R.id.trackname);
        time = (TextView) rootView.findViewById(R.id.time);
        realtime = (TextView) rootView.findViewById(R.id.realtime);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekbar);

        albumart = (ImageView) rootView.findViewById(R.id.albumartwork);
        previus = (ImageView) rootView.findViewById(R.id.previous);
        play = (ImageView) rootView.findViewById(R.id.play);
        next = (ImageView) rootView.findViewById(R.id.next);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        realtime.setText("0:30");


            mediaPlayer.stop();


        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previus.setOnClickListener(this);

        Bundle bundle =  getArguments();
        if (bundle!=null) {
            position = bundle.getInt("position");
            artistName = bundle.getString("name");

        }

        artistname.setText(artistName);

        changeUI();
        playMusic();
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:

               // playing=true;
                playMusic();
                changeUI();
                break;

            case R.id.next:
              // long var = Thread.currentThread().getId();
               // Log.d("ID",String.valueOf(var));
               // Thread.currentThread().interrupt();
                threadStop=true;  //PARAR HILO PRINCIPAL
              //  Thread.interrupted();
                if(TopTrackFragment.topTrackSearch.getCount()== position+1){

                    Toast.makeText(getActivity(),"No more tracks", Toast.LENGTH_SHORT).show();
                    playflag=true;
                }else{

                   // seekBar.setProgress(0);
                    position =position+1;
                    //playing=true;
                    mediaPlayer.stop();

                    //playflag=true;
                    playflag=true;
                    playing=false;
                   // playflag=true;
                    timerA=0;
                    timerB=0;
                    playflagA=false;
                    playMusic();
                    //threadStop=false;
                    changeUI();

                }

                break;

            case R.id.previous:
               // Thread.interrupted();
                threadStop=true;   //Parar hilo actual
                if(position-1<0){
                    Toast.makeText(getActivity(),"No more tracks",Toast.LENGTH_SHORT).show();
                    playflag=true;
                }else{
                    //Thread.interrupted();
                    position =position-1;
                    //playing=true;
                    mediaPlayer.stop();
                    playflag=true;
                    playing=false;
                    playMusic();

                    changeUI();
                }

                break;



        }
    }

    public void playMusic(){

        //final MediaPlayer mediaPlayer = new MediaPlayer();

       // if (mediaPlayer.isPlaying()){
        //    mediaPlayer.pause();
       // }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
     /*   mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
            }
        });*/

        try{
            mediaPlayer.setDataSource( TopTrackFragment.topTrackSearch.getItem(position).preview_url);
            mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
        }catch (Exception e) {

        }


 //final      if(!playflag){

        if(!playflag){
            mediaPlayer.start();

           playflag=true;
           play.setImageResource(R.drawable.ic_media_pause);
          // seekBar.setProgress(0);
           seekBar.setMax(30000);
           playing=true; //aun no se


     //       Log.d("Playflag",String.valueOf(playflag));
      //      Log.d("playing",String.valueOf(playing));
       //     Log.d("playflagA",String.valueOf(playflagA));

            if(!playflagA) {
                new Thread(this).start();
                playflagA=true;
            }

       }else{
           mediaPlayer.pause();
            threadPause=true;
           //mediaPlayer.release();
           play.setImageResource(R.drawable.ic_media_play);
           playflag=false;
       }

    }


    public  void changeUI() {

        artistname.setText(artistName);
        albumname.setText(TopTrackFragment.topTrackSearch.getItem(position).album.name.toString());
        trackname.setText(TopTrackFragment.topTrackSearch.getItem(position).name.toString());


        Image image = null;


        if ((TopTrackFragment.topTrackSearch.getItem(position).album.images != null) &&
                (TopTrackFragment.topTrackSearch.getItem(position).album.images.size() > 0)) {
            for (Image currentImage : TopTrackFragment.topTrackSearch.getItem(position).album.images) {
                if (currentImage.width >= 400) {
                    image = currentImage;
                }
            }

            if (image != null) {
                Picasso.with(getActivity().getApplicationContext())
                        .load(image.url)
                        .into(albumart);
            } else {
                albumart.setImageBitmap(null);
            }


        }


    }
}
