package rs.de.mappmedia.listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaListViewAdapter;
import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      MediaListViewItemListener
 */

public class MediaListViewItemListener implements View.OnClickListener {

    /**
     * Holds the context instance of the application
     */
    private Context context;

    /**
     * Holds the list view adapter instance for media items.
     */
    private MediaListViewAdapter mediaListViewAdapter;

    /**
     * The constructor of MediaListViewItemListener.
     *
     * @param context - context of the application
     * @param mediaListViewAdapter - the list view adapter that holds the clickable items
     */
    public MediaListViewItemListener(Context context, MediaListViewAdapter mediaListViewAdapter) {
        this.context = context;
        this.mediaListViewAdapter = mediaListViewAdapter;
    }

    /**
     * Is called when the user clicked on a certain item that the adapter holds.
     * @param v - the view where the action occured
     */
    @Override
    public void onClick(View v) {
        Media mediaItem = (Media)v.getTag(R.id.tag_media_item);

    }


}
