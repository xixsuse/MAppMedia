package rs.de.mappmedia.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.listeners.OnUpdateListener;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog
 * Class:      RemoveDialog
 */

public class RemoveDialog implements DialogInterface.OnClickListener {

    private AlertDialog.Builder builder;
    private OnUpdateListener updateListener;

    private Media mediaToRemove;

    public RemoveDialog(Context context, Media mediaToRemove) {
        this.mediaToRemove = mediaToRemove;

        builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
    }

    public void setUpdateListener(OnUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setTitle(int titleId) {
        builder.setTitle(titleId);
    }

    public void setMessage(int messageId) {
        builder.setMessage(String.format(builder.getContext().getString(messageId), mediaToRemove.getTitle()));
    }

    public void setPositiveButton(int positiveButtonTitleId) {
        builder.setPositiveButton(positiveButtonTitleId, this);
    }

    public void setNegativeButton(int negativeButtonTitleId) {
        builder.setNegativeButton(negativeButtonTitleId, this);
    }

    public void show() {
        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_POSITIVE:
                removeMedia();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    private void removeMedia() {
        DatabaseAccess.localMediaDelete(mediaToRemove);
        if(updateListener != null)
            updateListener.onUpdate();
    }


}
