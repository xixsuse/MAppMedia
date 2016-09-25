package rs.de.mappmedia.dialog.inputs;

import rs.de.mappmedia.database.DatabaseAccess;
import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.database.models.Movie;
import rs.de.mappmedia.listeners.OnUpdateListener;
import rs.de.mappmedia.util.Util;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog.inputs
 * Class:      MovieValueFiller
 */

public class MovieValueFiller implements ValueFiller {

    private Movie movie;
    private OnUpdateListener updateListener;

    public MovieValueFiller(Movie movie, OnUpdateListener updateListener) {
        this.movie = movie;
        this.updateListener = updateListener;
    }

    @Override
    public boolean setTitleOutput(String title) {
        if(title != null && title.length() > 0) {
            movie.setTitle(title);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTypeOutput(String type) {
        movie.setType(type);
        return true;
    }

    @Override
    public boolean setLocationOutput(String location) {
        if(location != null && location.length() > 0) {
            movie.setLocation(location);
            return true;
        }
        return false;
    }

    @Override
    public boolean setArtistOutput(String artist) {
        if(artist != null && artist.length() > 0) {
            movie.setDirector(artist);
        }
        return true;
    }

    @Override
    public boolean setGenreOutput(String genre) {
        if(genre != null && genre.length() > 0) {
            movie.setGenre(genre);
        }
        return true;
    }

    @Override
    public boolean setReleaseYearOutput(String releaseYear) {
        movie.setReleaseYear(Util.parseInteger(releaseYear, 0));
        return true;
    }

    @Override
    public boolean setAgeRestrictionOutput(String ageRestriction) {
        movie.setAgeRestriction(Util.parseInteger(ageRestriction, Media.NO_AGE_RESTRICTION));
        return true;
    }

    @Override
    public boolean setRunningTimeOutput(String runningTime) {
        movie.setRunningTime(Util.parseInteger(runningTime, 0));
        return true;
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

    @Override
    public boolean isMediaStored() {
        return movie.getId() != Movie.NOT_REGISTERED_ID;
    }

    @Override
    public boolean store() {
        boolean inserted = DatabaseAccess.localMediaInsert(movie);
        if(updateListener != null && inserted) {
            updateListener.onUpdate();
        }
        return inserted;
    }

    @Override
    public boolean update() {
        boolean updated = DatabaseAccess.localMediaUpdate(movie);
        if(updateListener != null && updated) {
            updateListener.onUpdate();
        }
        return updated;
    }


}
