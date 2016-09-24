package rs.de.mappmedia.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import rs.de.mappmedia.R;
import rs.de.mappmedia.database.models.Movie;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog
 * Class:      VersatileMediaDialog
 */

public class VersatileMediaDialog implements DialogInterface.OnClickListener {

    private AlertDialog.Builder builder;

    private EditText titleEditText;
    private Spinner typeSpinner;
    private EditText locationEditText;
    private EditText artistEditText;
    private EditText genreEditText;
    private EditText releaseYearEditText;
    private Spinner ageRestrictionSpinner;
    private EditText runningTimeEditText;

    private ValueFiller valueFiller;

    public VersatileMediaDialog(Context context) {
        builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = inflater.inflate(R.layout.layout_versatile_media_dialog, null, false);

        titleEditText = (EditText) mainView.findViewById(R.id.edittext_input_title);
        typeSpinner = (Spinner) mainView.findViewById(R.id.spinner_input_type);
        locationEditText = (EditText) mainView.findViewById(R.id.edittext_input_location);
        artistEditText = (EditText) mainView.findViewById(R.id.edittext_input_artist);
        genreEditText = (EditText) mainView.findViewById(R.id.edittext_input_genre);
        releaseYearEditText = (EditText) mainView.findViewById(R.id.edittext_input_release_year);
        ageRestrictionSpinner = (Spinner) mainView.findViewById(R.id.spinner_input_age_restriction);
        runningTimeEditText = (EditText) mainView.findViewById(R.id.edittext_input_running_time);

        builder.setView(mainView);
    }

    public void setTitle(int titleId) {
        builder.setTitle(titleId);
    }

    public void setPositiveButton(int positiveButtonTitleId) {
        builder.setPositiveButton(positiveButtonTitleId, this);
    }

    public void setNegativeButton(int negativeButtonTitleId) {
        builder.setNegativeButton(negativeButtonTitleId, this);
    }

    public void show(ValueFiller valueFiller) {
        fillInputMask(valueFiller);
        builder.show();
    }

    private void fillInputMask(ValueFiller valueFiller) {
        if(valueFiller != null) {
            this.valueFiller = valueFiller;
            titleEditText.setText(valueFiller.getTitleInput());
            selectSpinnerItem(typeSpinner, valueFiller.getTypeInput());
            locationEditText.setText(valueFiller.getLocationInput());
            artistEditText.setText(valueFiller.getArtistInput());
            genreEditText.setText(valueFiller.getGenreInput());
            releaseYearEditText.setText(valueFiller.getReleaseYearInput());
            selectSpinnerItem(ageRestrictionSpinner, valueFiller.getAgeRestrictionInput());
            runningTimeEditText.setText(valueFiller.getRunningTimeInput());
        }
    }

    private void storeInputMask() {
        if(valueFiller != null) {
            valueFiller.setTitleOutput(titleEditText.getText().toString());
            valueFiller.setTypeOutput(typeSpinner.getSelectedItem().toString());
            valueFiller.setLocationOutput(locationEditText.getText().toString());
            valueFiller.setArtistOutput(artistEditText.getText().toString());
            valueFiller.setGenreOutput(genreEditText.getText().toString());
            valueFiller.setReleaseYearOutput(releaseYearEditText.getText().toString());
            valueFiller.setAgeRestrictionOutput(ageRestrictionSpinner.getSelectedItem().toString());
            valueFiller.setRunningTimeOutput(runningTimeEditText.getText().toString());
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_POSITIVE:
                /**
                 * TODO checkInputMask vielleicht ist der Film bereits in der Datenbank
                 * TODO kurzen Abgleich machen und eventuelle Doppelte filtern
                 */
                storeInputMask();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    public static interface ValueFiller {

        public void setTitleOutput(String title);
        public void setLocationOutput(String location);
        public void setTypeOutput(String type);
        public void setArtistOutput(String artist);
        public void setGenreOutput(String genre);
        public void setReleaseYearOutput(String releaseYear);
        public void setAgeRestrictionOutput(String ageRestriction);
        public void setRunningTimeOutput(String runningTimeOutput);

        public String getTitleInput();
        public String getLocationInput();
        public String getTypeInput();
        public String getArtistInput();
        public String getGenreInput();
        public String getReleaseYearInput();
        public String getAgeRestrictionInput();
        public String getRunningTimeInput();

    }

    public static class MovieValueFiller implements ValueFiller {

        private Movie movie;

        public MovieValueFiller(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void setTitleOutput(String title) {
            movie.setTitle(title);
        }

        @Override
        public void setTypeOutput(String type) {
            movie.setType(type);
        }

        @Override
        public void setLocationOutput(String location) {
            movie.setLocation(location);
        }

        @Override
        public void setArtistOutput(String artist) {
            movie.setDirector(artist);
        }

        @Override
        public void setGenreOutput(String genre) {
            movie.setGenre(genre);
        }

        @Override
        public void setReleaseYearOutput(String releaseYear) {
            movie.setReleaseYear(Integer.parseInt(releaseYear));
        }

        @Override
        public void setAgeRestrictionOutput(String ageRestriction) {
            movie.setAgeRestriction(Integer.parseInt(ageRestriction));
        }

        @Override
        public void setRunningTimeOutput(String runningTimeOutput) {
            movie.setRunningTime(Integer.parseInt(runningTimeOutput));
        }

        @Override
        public String getTitleInput() {
            return movie.getTitle();
        }

        @Override
        public String getLocationInput() {
            return movie.getLocation();
        }

        @Override
        public String getTypeInput() {
            return movie.getType();
        }

        @Override
        public String getArtistInput() {
            return movie.getDirector();
        }

        @Override
        public String getGenreInput() {
            return movie.getGenre();
        }

        @Override
        public String getAgeRestrictionInput() {
            return String.valueOf(movie.getAgeRestriction());
        }

        @Override
        public String getReleaseYearInput() {
            return String.valueOf(movie.getReleaseYear());
        }

        @Override
        public String getRunningTimeInput() {
            return String.valueOf(movie.getRunningTime());
        }
    }

}
