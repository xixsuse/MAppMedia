package rs.de.mappmedia.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import rs.de.mappmedia.R;
import rs.de.mappmedia.dialog.inputs.ValueFiller;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog
 * Class:      VersatileMediaDialog
 */

public class VersatileMediaDialog  {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private EditText titleEditText;
    private Spinner typeSpinner;
    private EditText locationEditText;
    private EditText artistEditText;
    private Spinner genreSpinner;
    private EditText releaseYearEditText;
    private Spinner ageRestrictionSpinner;
    private EditText runningTimeEditText;

    private ValueFiller valueFiller;

    public VersatileMediaDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = inflater.inflate(R.layout.layout_versatile_media_dialog, null, false);

        titleEditText = (EditText) mainView.findViewById(R.id.edittext_input_title);
        typeSpinner = (Spinner) mainView.findViewById(R.id.spinner_input_type);
        locationEditText = (EditText) mainView.findViewById(R.id.edittext_input_location);
        artistEditText = (EditText) mainView.findViewById(R.id.edittext_input_artist);
        genreSpinner = (Spinner) mainView.findViewById(R.id.spinner_input_genre);
        releaseYearEditText = (EditText) mainView.findViewById(R.id.edittext_input_release_year);
        ageRestrictionSpinner = (Spinner) mainView.findViewById(R.id.spinner_input_age_restriction);
        runningTimeEditText = (EditText) mainView.findViewById(R.id.edittext_input_running_time);

        builder.setView(mainView);
    }

    public void setTitle(int titleId) {
        builder.setTitle(titleId);
    }

    public void setPositiveButton(int positiveButtonTitleId) {
        builder.setPositiveButton(positiveButtonTitleId, null);
    }

    public void setNegativeButton(int negativeButtonTitleId) {
        builder.setNegativeButton(negativeButtonTitleId, null);
    }

    public void show(ValueFiller valueFiller) {
        fillInputMask(valueFiller);
        dialog = builder.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if(positiveButton != null)
            positiveButton.setOnClickListener(new PositiveButtonListener(this));
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if(negativeButton != null)
            negativeButton.setOnClickListener(new NegativeButtonListener(this));
    }

    public void close() {
        dialog.cancel();
    }

    private void fillInputMask(ValueFiller valueFiller) {
        if(valueFiller != null) {
            this.valueFiller = valueFiller;
            titleEditText.setText(valueFiller.getTitleInput());
            selectSpinnerItem(typeSpinner, valueFiller.getTypeInput());
            locationEditText.setText(valueFiller.getLocationInput());
            artistEditText.setText(valueFiller.getArtistInput());
            selectSpinnerItem(genreSpinner, valueFiller.getGenreInput());
            releaseYearEditText.setText(valueFiller.getReleaseYearInput());
            selectSpinnerItem(ageRestrictionSpinner, valueFiller.getAgeRestrictionInput());
            runningTimeEditText.setText(valueFiller.getRunningTimeInput());
        }
    }

    private void selectSpinnerItem(Spinner spinner, String itemToSelect) {
        for(int i = 0; i < spinner.getCount(); i++) {
            String item = spinner.getItemAtPosition(i).toString();
            if(item.equals(itemToSelect)) {
                spinner.setSelection(i);
                return;
            }
        }
        spinner.setSelection(0);
    }

    private byte storeInputMask() {
        byte inputsSet = 0;
        if(valueFiller != null) {
            inputsSet |= (valueFiller.setTitleOutput(titleEditText.getText().toString()))
                    ? ValueFiller.TITLE_BIT : 0;
            inputsSet |= (valueFiller.setTypeOutput(typeSpinner.getSelectedItem().toString()))
                    ? ValueFiller.TYPE_BIT : 0;
            inputsSet |= (valueFiller.setLocationOutput(locationEditText.getText().toString()))
                    ? ValueFiller.LOCATION_BIT : 0;
            inputsSet |= (valueFiller.setArtistOutput(artistEditText.getText().toString()))
                    ? ValueFiller.ARTIST_BIT : 0;
            inputsSet |= (valueFiller.setGenreOutput(genreSpinner.getSelectedItem().toString()))
                    ? ValueFiller.GENRE_BIT : 0;
            inputsSet |= (valueFiller.setReleaseYearOutput(releaseYearEditText.getText().toString()))
                    ? ValueFiller.RELEASE_YEAR_BIT : 0;
            inputsSet |= (valueFiller.setAgeRestrictionOutput(ageRestrictionSpinner.getSelectedItem().toString()))
                    ? ValueFiller.AGE_RESTRICTION_BIT : 0;
            inputsSet |= (valueFiller.setRunningTimeOutput(runningTimeEditText.getText().toString()))
                    ? ValueFiller.RUNNING_TIME_BIT : 0;
        }
        return inputsSet;
    }

    private boolean isInputSet(byte inputsSet, byte bitMask) {
        return (inputsSet & bitMask) == bitMask;
    }


    private static class PositiveButtonListener implements View.OnClickListener {

        private VersatileMediaDialog vmd;

        private PositiveButtonListener(VersatileMediaDialog vmd) {
            this.vmd = vmd;
        }

        @Override
        public void onClick(View v) {
            byte inputsSet = vmd.storeInputMask();
            if(inputsSet != ValueFiller.ALL_INPUTS_SET) {
                if(!vmd.isInputSet(inputsSet, ValueFiller.TITLE_BIT)) {
                    vmd.titleEditText.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.TYPE_BIT)) {
                    vmd.typeSpinner.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.LOCATION_BIT)) {
                    vmd.locationEditText.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.ARTIST_BIT)) {
                    vmd.artistEditText.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.GENRE_BIT)) {
                    vmd.genreSpinner.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.RELEASE_YEAR_BIT)) {
                    vmd.releaseYearEditText.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.AGE_RESTRICTION_BIT)) {
                    vmd.ageRestrictionSpinner.setBackgroundColor(Color.RED);
                }
                if(!vmd.isInputSet(inputsSet, ValueFiller.RUNNING_TIME_BIT)) {
                    vmd.runningTimeEditText.setBackgroundColor(Color.RED);
                }
            } else {
                if(!vmd.valueFiller.isMediaStored()) {
                    vmd.valueFiller.store();
                } else {
                    vmd.valueFiller.update();
                }
                vmd.close();
            }
        }

    }

    private static class NegativeButtonListener implements View.OnClickListener {

        private VersatileMediaDialog versatileMediaDialog;

        private NegativeButtonListener(VersatileMediaDialog versatileMediaDialog) {
            this.versatileMediaDialog = versatileMediaDialog;
        }

        @Override
        public void onClick(View v) {
            versatileMediaDialog.close();
        }

    }

}
