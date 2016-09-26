package rs.de.mappmedia.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rs.de.mappmedia.R;
import rs.de.mappmedia.adapters.MediaListViewAdapter;
import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.database.models.Movie;
import rs.de.mappmedia.dialog.OptionsDialog;
import rs.de.mappmedia.dialog.RemoveDialog;
import rs.de.mappmedia.dialog.VersatileMediaDialog;
import rs.de.mappmedia.dialog.inputs.MovieValueFiller;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.listeners
 * Class:      MediaListViewItemListener
 */

public class MediaListViewItemListener implements View.OnClickListener, View.OnLongClickListener,
        OptionsDialog.OnOptionSelectedListener {


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
     * @param v - the view where the action occurred
     */
    @Override
    public void onClick(View v) {
        /**
         * TODO Info Dialog mit den Daten anzeigen
         */
        Toast.makeText(v.getContext(), "Hier werden die Daten angezeigt", Toast.LENGTH_SHORT).show();
    }


    /**
     * Is called when the user long clicked on a certain item that the adapter holds.
     *
     * @param v - the view where the action occurred
     * @return whether or not the long click was handled
     */
    @Override
    public boolean onLongClick(View v) {
        Media media = (Media)v.getTag(R.id.tag_media_item);
        OptionsDialog optionsDialog = new OptionsDialog(v.getContext(), media);
        optionsDialog.setOptionSelectedListener(this);
        optionsDialog.setTitle(R.string.main_media_item_dialog_title);
        optionsDialog.setItems(R.array.main_media_item_dialog_options);
        optionsDialog.show();
        return true;
    }

    /**
     *
     * @param context
     * @param media
     */
    @Override
    public void onEditSelected(Context context, Media media) {
        VersatileMediaDialog dialog = new VersatileMediaDialog(context);
        dialog.setNegativeButton(R.string.versatile_dialog_neg_button);
        if(media instanceof Movie) {
            Movie movie = (Movie)media;
            dialog.setTitle(R.string.versatile_dialog_edit_movie_title);
            dialog.setPositiveButton(R.string.versatile_dialog_edit_movie_pos_button);
            dialog.show(new MovieValueFiller(movie, mediaListViewAdapter.getUpdateListener()));
        }
    }

    /**
     *
     * @param context
     * @param media
     */
    @Override
    public void onRemoveSelected(Context context, Media media) {
        RemoveDialog removeDialog = new RemoveDialog(context, media);
        removeDialog.setUpdateListener(mediaListViewAdapter.getUpdateListener());
        removeDialog.setTitle(R.string.main_media_remove_dialog_title);
        removeDialog.setMessage(R.string.main_media_remove_dialog_message);
        removeDialog.setPositiveButton(R.string.main_media_remove_dialog_pos_button);
        removeDialog.setNegativeButton(R.string.main_media_remove_dialog_neg_button);
        removeDialog.show();
    }

}
