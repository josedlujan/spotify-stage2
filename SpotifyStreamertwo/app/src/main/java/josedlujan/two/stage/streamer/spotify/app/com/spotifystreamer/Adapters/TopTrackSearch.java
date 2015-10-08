package josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.nio.channels.spi.AbstractSelectionKey;
import java.util.ArrayList;
import java.util.List;


import josedlujan.two.stage.streamer.spotify.app.com.spotifystreamer.R;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by jose on 04/07/15.
 */
public class TopTrackSearch extends BaseAdapter {
    Context myContext;
    LayoutInflater mylayoutInflater;
    List<Track> myTopTrackList;


    public TopTrackSearch(Context context){
        super();
        myContext = context;
        mylayoutInflater = LayoutInflater.from(context);
        myTopTrackList = new ArrayList<>();



    }


    public void setMyTopTrackList(List<Track> trackList){
        myTopTrackList = trackList;
        notifyDataSetChanged();
    }

    public int getCount(){
        return myTopTrackList.size();
    }

    public Track getItem(int position){
        if(myTopTrackList.size()==0){
            return  null;
        }
        return myTopTrackList.get(position);
    }

    public long getItemId(int position){
        return 0;
    }

    public List<Track> getTracks(){
        return myTopTrackList;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView != null){
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = mylayoutInflater.inflate(R.layout.list_item_toptrack,parent,false);
            holder = new ViewHolder();
            holder.trackname = (TextView)convertView.findViewById(R.id.trackname);
            holder.albumname = (TextView)convertView.findViewById(R.id.albumname);
        //    holder.container = (ViewGroup) convertView.findViewById(R.id.list_item_toptrack);
            //
            holder.imageView = (ImageView)convertView.findViewById(R.id.thumbnailToptrack);

            convertView.setTag(holder);

        }

        final Track track = getItem(position);

        Image image = null;


        if((track.album.images != null) && (track.album.images.size()>0)){
            for (Image currentImage : track.album.images) {
                if (currentImage.width >= 200) {
                    image = currentImage;
                }
            }

            /*for (Image currentImage: artist.images){
                if (currentImage.width>=200){
                    image= currentImage;
                }
            }*/
        }

        if(image != null){
            Picasso.with(myContext)
                    .load(image.url)
                    .into(holder.imageView);
        }else{
            holder.imageView.setImageBitmap(null);
        }

        holder.trackname.setText(track.name);
        holder.albumname.setText(track.album.name);

       /* holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             myTrackListener.onTrackClick(position,track.artists.get(0).name,track.name,track.album.name,track.preview_url);
            }
        });*/

        return convertView;
    }

    static class ViewHolder{
        TextView trackname;
        TextView albumname;
        ViewGroup container;


        //TextView
        ImageView imageView;
    }

}
