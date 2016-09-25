package rs.de.mappmedia.database.models;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.database
 * Class:      DAO
 */

public abstract class Media {

    public static final int NO_SUCH_COLUMN = -1;
    public static final long NOT_REGISTERED_ID = -1;

    public static final int TYPE_MOVIE = 0;
    public static final int[] ALL_TYPES = {
        TYPE_MOVIE
    };

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_RELEASE_YEAR = "release_year";
    public static final String COLUMN_AGE_RESTRICTION = "age_restriction";
    public static final String COLUMN_RUNNING_TIME = "running_time";


    public static final String[] ALL_COLUMNS = {
            COLUMN_ID, COLUMN_TITLE, COLUMN_LOCATION, COLUMN_TYPE,
            COLUMN_GENRE, COLUMN_RELEASE_YEAR, COLUMN_AGE_RESTRICTION, COLUMN_RUNNING_TIME
    };

    public static final int NO_AGE_RESTRICTION = -1;
    public static final int AGE_RESTRICTION_0 = 0;
    public static final int AGE_RESTRICTION_6 = 6;
    public static final int AGE_RESTRICTION_12 = 12;
    public static final int AGE_RESTRICTION_16 = 16;
    public static final int AGE_RESTRICTION_18 = 18;

    protected long id;
    protected String title;
    protected String type;
    protected String location;
    protected String genre;
    protected int releaseYear;
    protected int ageRestriction;
    protected int runningTime;


    public Media(long id) {
        this.id = id;
    }

    public abstract ContentValues getContentValues();

    public abstract void loadByCursor(Cursor data);

    protected boolean areNotNullValuesSet() {
        return title != null && type != null && location != null;
    }

    protected boolean setAttributeByCursor(Cursor data, String currentColumnName) {
        int columnIndex = data.getColumnIndex(currentColumnName);
        if(columnIndex != NO_SUCH_COLUMN) {
            switch(currentColumnName) {
                case COLUMN_ID:
                    id = data.getLong(columnIndex);
                    return true;
                case COLUMN_TITLE:
                    title = data.getString(columnIndex);
                    return true;
                case COLUMN_LOCATION:
                    location = data.getString(columnIndex);
                    return true;
                case COLUMN_TYPE:
                    type = data.getString(columnIndex);
                    return true;
                case COLUMN_GENRE:
                    genre = data.getString(columnIndex);
                    return true;
                case COLUMN_RELEASE_YEAR:
                    releaseYear = data.getInt(columnIndex);
                    return true;
                case COLUMN_AGE_RESTRICTION:
                    ageRestriction = data.getInt(columnIndex);
                    return true;
                case COLUMN_RUNNING_TIME:
                    runningTime = data.getInt(columnIndex);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public String getLocation() {
        return location;
    }


}
