package rs.de.mappmedia.database.models;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.database.models
 * Class:      Movie
 */

public class Movie extends Media implements Parcelable {

    public static final String TABLE_NAME = Movie.class.getSimpleName();

    public static final String COLUMN_DIRECTOR = "director";

    public static final String[] ALL_COLUMNS = {
        COLUMN_ID, COLUMN_TITLE, COLUMN_LOCATION, COLUMN_TYPE, COLUMN_DIRECTOR,
            COLUMN_GENRE, COLUMN_RELEASE_YEAR, COLUMN_AGE_RESTRICTION, COLUMN_RUNNING_TIME
    };

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_LOCATION + " TEXT NOT NULL, " +
            COLUMN_TYPE + " TEXT NOT NULL, " +
            COLUMN_DIRECTOR + " TEXT NULL, " +
            COLUMN_GENRE + " TEXT NULL, " +
            COLUMN_RELEASE_YEAR + " INTEGER NULL, " +
            COLUMN_AGE_RESTRICTION + " INTEGER NULL, " +
            COLUMN_RUNNING_TIME + " INTEGER NULL );";

    private String director;

    private Movie(long id) {
        super(id);
    }

    public static Movie newInstance() {
        return new Movie(NOT_REGISTERED_ID);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        //NOT NULL VALUES
        contentValues.put(Movie.COLUMN_TITLE, title);
        contentValues.put(Movie.COLUMN_LOCATION, location);
        contentValues.put(Movie.COLUMN_TYPE, type);

        //NULL VALUES
        contentValues.put(Movie.COLUMN_DIRECTOR, director);
        contentValues.put(Movie.COLUMN_GENRE, genre);
        contentValues.put(Movie.COLUMN_RELEASE_YEAR, releaseYear);
        contentValues.put(Movie.COLUMN_AGE_RESTRICTION, ageRestriction);
        contentValues.put(Movie.COLUMN_RUNNING_TIME, runningTime);

        return contentValues;
    }

    @Override
    public void loadByCursor(Cursor data) {
        for(String currentColumnName : data.getColumnNames()) {
            setAttributeByCursor(data, currentColumnName);
        }
    }

    @Override
    public boolean setAttributeByCursor(Cursor data, String currentColumnName) {
        boolean set = super.setAttributeByCursor(data, currentColumnName);
        if(!set) {
            int columnIndex = data.getColumnIndex(currentColumnName);
            if(columnIndex != NO_SUCH_COLUMN) {
                switch(currentColumnName) {
                    case COLUMN_DIRECTOR:
                        director = data.getString(columnIndex);
                        break;
                }
            }
        }
        return true;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public static ArrayList<Media> buildList(Cursor movieCursor) {
        ArrayList<Media> mediaItems = new ArrayList<>();
        movieCursor.moveToFirst();
        while(!movieCursor.isAfterLast()) {
            Movie movie = Movie.newInstance();
            movie.loadByCursor(movieCursor);
            mediaItems.add(movie);
            movieCursor.moveToNext();
        }
        movieCursor.close();
        return mediaItems;
    }

    private Movie(Parcel in) {
        super(in.readLong());
        director = in.readString();
        genre = in.readString();
        releaseYear = in.readInt();
        ageRestriction = in.readInt();
        runningTime = in.readInt();
        location = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 8;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(director);
        dest.writeString(genre);
        dest.writeInt(releaseYear);
        dest.writeInt(ageRestriction);
        dest.writeInt(runningTime);
        dest.writeString(location);
    }
}
