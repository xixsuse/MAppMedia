package rs.de.mappmedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import rs.de.mappmedia.R;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.adapters
 * Class:      MediaListViewAdapter
 */

public class MediaListViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public MediaListViewAdapter(Context context) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return "Test";
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
        return convertView;
    }
}
