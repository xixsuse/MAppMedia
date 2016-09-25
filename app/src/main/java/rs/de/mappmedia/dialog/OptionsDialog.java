package rs.de.mappmedia.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import rs.de.mappmedia.database.models.Media;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog
 * Class:      OptionsDialog
 */

public class OptionsDialog implements DialogInterface.OnClickListener {

    private static final int OPTION_EDIT_INDEX = 0;
    private static final int OPTION_REMOVE_INDEX = 1;

    private AlertDialog.Builder builder;
    private OnOptionSelectedListener optionSelectedListener;

    private Media media;

    public OptionsDialog(Context context, Media media) {
        this.media = media;

        builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
    }

    public void setTitle(int titleId) {
        builder.setTitle(titleId);
    }

    public void setItems(int itemsId) {
        builder.setItems(itemsId, this);
    }

    public void setOptionSelectedListener(OnOptionSelectedListener optionSelectedListener) {
        this.optionSelectedListener = optionSelectedListener;
    }

    public void show() {
        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case OPTION_EDIT_INDEX:
                if(optionSelectedListener != null) {
                    optionSelectedListener.onEditSelected(builder.getContext(), media);
                }
                break;
            case OPTION_REMOVE_INDEX:
                if(optionSelectedListener != null) {
                    optionSelectedListener.onRemoveSelected(builder.getContext(), media);
                }
                break;
        }
    }

    public static interface OnOptionSelectedListener {

        public void onEditSelected(Context context, Media media);
        public void onRemoveSelected(Context context, Media media);

    }

}
