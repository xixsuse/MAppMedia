package rs.de.mappmedia.dialog.inputs;

import rs.de.mappmedia.database.models.Media;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.dialog.inputs
 * Class:      ValueFiller
 */

public interface ValueFiller {

    public static final byte TITLE_BIT = 1;
    public static final byte LOCATION_BIT = 2;
    public static final byte TYPE_BIT = 4;
    public static final byte ARTIST_BIT = 8;
    public static final byte GENRE_BIT = 16;
    public static final byte RELEASE_YEAR_BIT = 32;
    public static final byte AGE_RESTRICTION_BIT = 64;
    public static final byte RUNNING_TIME_BIT = -128;
    public static final byte ALL_INPUTS_SET = -1;

    public boolean setTitleOutput(String title);
    public boolean setLocationOutput(String location);
    public boolean setTypeOutput(String type);
    public boolean setArtistOutput(String artist);
    public boolean setGenreOutput(String genre);
    public boolean setReleaseYearOutput(String releaseYear);
    public boolean setAgeRestrictionOutput(String ageRestriction);
    public boolean setRunningTimeOutput(String runningTime);

    public String getTitleInput();
    public String getLocationInput();
    public String getTypeInput();
    public String getArtistInput();
    public String getGenreInput();
    public String getReleaseYearInput();
    public String getAgeRestrictionInput();
    public String getRunningTimeInput();

    public boolean isMediaStored();
    public boolean store();
    public boolean update();

}
