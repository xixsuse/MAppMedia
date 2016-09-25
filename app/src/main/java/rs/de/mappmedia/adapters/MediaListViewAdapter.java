package rs.de.mappmedia.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rs.de.mappmedia.R;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.listeners.MediaListViewItemListener;
import rs.de.mappmedia.listeners.OnUpdateListener;
import rs.de.mappmedia.util.Util;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.adapters
 * Class:      MediaListViewAdapter
 */

public class MediaListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private MediaListViewItemListener itemListener;
    private ArrayList<Media> resultMediaItems;

    private OnUpdateListener updateListener;

    public MediaListViewAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemListener = new MediaListViewItemListener(context, this);
        resultMediaItems = new ArrayList<>();
    }

    public void setResultMediaItems(ArrayList<Media> resultMediaItems) {
        if(resultMediaItems != null) {
            this.resultMediaItems = resultMediaItems;
            notifyDataSetChanged();
        }
    }

    public void setUpdateListener(OnUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public OnUpdateListener getUpdateListener() {
        return updateListener;
    }

    public void update() {
        if(updateListener != null) {
            updateListener.onUpdate();
        }
    }

    @Override
    public int getCount() {
        return resultMediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return resultMediaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_media_list_item, parent, false);
        }
        convertView.setOnClickListener(itemListener);
        convertView.setOnLongClickListener(itemListener);

        Media currentMediaItem = resultMediaItems.get(position);
        convertView.setTag(R.id.tag_media_item, currentMediaItem);

        TextView mediaTitleTextView = (TextView)convertView.findViewById(R.id.textview_media_title);
        mediaTitleTextView.setText(currentMediaItem.getTitle());

        TextView mediaLocationTextView = (TextView)convertView.findViewById(R.id.textview_media_location);
        mediaLocationTextView.setText(currentMediaItem.getLocation());

        TextView mediaTypeTextView = (TextView)convertView.findViewById(R.id.textview_media_type);
        mediaTypeTextView.setText(currentMediaItem.getType());

        TextView mediaAgeRestrictionTextView = (TextView)convertView.findViewById(R.id.textview_media_age_restriction);
        int ageRestriction = currentMediaItem.getAgeRestriction();
        if(ageRestriction >= 0) {
            mediaAgeRestrictionTextView.setTextColor(
                    ContextCompat.getColor(context, Util.interpretMediaAgeRestrictionValue(ageRestriction)));
            mediaAgeRestrictionTextView.setText(String.format(context.getString(R.string.main_age_restriction_value), ageRestriction));
        } else {
            mediaAgeRestrictionTextView.setText(R.string.all_no_value_set);
        }

        TextView mediaRunningTimeTextView = (TextView)convertView.findViewById(R.id.textview_media_running_time);
        int runningTime = currentMediaItem.getRunningTime();
        if(runningTime > 0) {
            mediaRunningTimeTextView.setText(String.format(context.getString(R.string.main_running_time_value), runningTime));
        } else {
            mediaRunningTimeTextView.setText(R.string.all_no_value_set);
        }

        return convertView;
    }

}
