package rs.de.mappmedia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import rs.de.mappmedia.database.models.Media;
import rs.de.mappmedia.database.models.Movie;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.database
 * Class:      DatabaseSource
 */

public class DatabaseAccess {

    /**
     * Defines the tag for logging stuff
     */
    public static final String TAG = DatabaseAccess.class.getSimpleName();

    /**
     * Defines the value of the insert id when data isn't
     * correctly inserted to the current loaded database
     */
    private static final int STATUS_NO_INSERT_DONE = -1;

    /**
     * Defines the value of the affected rows when data
     * isn't correctly deleted or updated from the current loaded
     * database
     */
    private static final int STATUS_NO_ROWS_DELETED = 0, STATUS_NO_ROWS_UPDATED = 0;

    /**
     * Holds the singleton instance of this class
     */
    private static DatabaseAccess instance;

    /**
     * Holds the database helper that helps to open
     * local stored database that were directly created
     * on the device
     */
    private DatabaseHelper localDatabaseHelper;

    /**
     * Holds the instance of the current loaded database.
     * It is either a local stored database or a database
     * stored in the private application storage downloaded
     * via FTP
     */
    private SQLiteDatabase database;

    /**
     * Holds the external directory for databases that were
     * downloaded via FTP onto the device
     */
    private File externalDatabaseDirectory;

    /**
     * The private constructor of this class, that insures
     * that only one instance is created via static getInstance
     * method.
     *
     * @param context - context of the application
     */
    private DatabaseAccess(Context context) {
        localDatabaseHelper = new DatabaseHelper(context);
        externalDatabaseDirectory = context.getFilesDir();
    }

    /**
     * Creates the singleton instance of this class and returns
     * it. In the case of the instance is already created, only
     * the part of returning is done.
     *
     * @param context - context of the application
     * @return the singleton instance
     */
    public static DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
            Log.d(TAG, "Created DatabaseAccess instance");
        }
        return instance;
    }

    /**
     * Static wrapper method of insertMedia method.
     *
     * @param media - the media to insert
     * @return whether or not the media was inserted
     */
    public static boolean localMediaInsert(Media media) {
        if(instance != null) {
            instance.open(true, null);
            boolean inserted = instance.insertMedia(media);
            instance.close();
            return inserted;
        }
        return false;
    }

    /**
     * Static wrapper method of updateMedia method.
     *
     * @param media - the media to update
     * @return whether or not the media was updated
     */
    public static boolean localMediaUpdate(Media media) {
        if(instance != null) {
            instance.open(true, null);
            boolean updated = instance.updateMedia(media);
            instance.close();
            return updated;
        }
        return false;
    }

    /**
     * Static wrapper method of deleteMedia method.
     *
     * @param media - the media to delete
     * @return whether or not the media was deleted
     */
    public static boolean localMediaDelete(Media media) {
        if(instance != null) {
            instance.open(true, null);
            boolean deleted = instance.deleteMedia(media);
            instance.close();
            return deleted;
        }
        return false;
    }

    /**
     * Static wrapper method of searchMedia method.
     *
     * @param query - the query to search for
     * @param mediaSearchType - the type of media to search for
     * @return a list containing the media objects that fits the passed query and type
     */
    public static ArrayList<Media> localMediaSearch(String query, int mediaSearchType) {
        ArrayList<Media> mediaSearchResult = null;
        if(instance != null) {
            instance.open(true, null);
            mediaSearchResult = instance.searchMedia(query, mediaSearchType);
            instance.close();
        }
        return mediaSearchResult;
    }

    /**
     * Static wrapper method of getAllMedia method.
     *
     * @param mediaSearchType - the type of media to get all data from
     * @return a list containing all media objects of the specified type
     */
    public static ArrayList<Media> localAllMedia(int mediaSearchType) {
        ArrayList<Media> mediaSearchResult = null;
        if(instance != null) {
            instance.open(true, null);
            mediaSearchResult = instance.getAllMedia(mediaSearchType);
            instance.close();
        }
        return mediaSearchResult;
    }


    /**
     * Opens a locally stored or external stored database.
     *
     * @param localStored - whether or not the database is stored in the local database directory
     * @param databaseName - the name of the database to open, only necessary in case of localStored being set to false
     */
    public void open(boolean localStored, String databaseName) {
        if(localStored) {
            database = localDatabaseHelper.getWritableDatabase();
            Log.d(TAG, "Opened local stored database");
        } else {
            File databaseFile = new File(externalDatabaseDirectory, databaseName);
            database = SQLiteDatabase.openDatabase(databaseFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            Log.d(TAG, "Opened external stored database");
        }
    }

    /**
     * Closes the current open database instance.
     */
    public void close() {
        if(database != null) {
            database.close();
            Log.d(TAG, "Database closed");
        }
    }

    /**
     * Returns whether or not a database file is loaded.
     *
     * @return if database file is loaded
     */
    public boolean isDatabaseOpen() {
        return database != null;
    }

    /**
     * Inserts a media object to the database.
     *
     * @param media - the media object to add
     * @return returns whether or not the insert was successful
     */
    public boolean insertMedia(Media media) {
        long insertId = STATUS_NO_INSERT_DONE;
        if(!database.isReadOnly()) {
            if(media instanceof Movie) {
                Movie movie = (Movie)media;
                if(movie.getId() == Media.NOT_REGISTERED_ID) {
                    ContentValues movieContentValues = movie.getContentValues();
                    insertId = database.insert(Movie.TABLE_NAME, null, movieContentValues);
                    Log.d(TAG, "Data with id " + insertId + " inserted");
                }
            }
        }
        if(insertId != STATUS_NO_INSERT_DONE) {
            media.setId(insertId);
        }
        return insertId != STATUS_NO_INSERT_DONE;
    }

    /**
     * Updates a media object of the database.
     *
     * @param media - the media object to update
     * @return returns whether or not the update was successful
     */
    public boolean updateMedia(Media media) {
        int affectedRows = STATUS_NO_ROWS_UPDATED;
        if(!database.isReadOnly()) {
            long id = media.getId();
            if(media instanceof Movie) {
                Movie movie = (Movie)media;
                if(id != Media.NOT_REGISTERED_ID) {
                    ContentValues movieContentValues = movie.getContentValues();
                    ContentValues whereStatements = new ContentValues();
                    whereStatements.put(Movie.COLUMN_ID, id);
                    String whereClause = DatabaseHelper.whereClause(whereStatements, DatabaseHelper.OPERATOR_EQUALS, null);
                    String[] whereArguments = DatabaseHelper.whereArguments(whereStatements, false);
                    affectedRows = database.update(Movie.TABLE_NAME, movieContentValues, whereClause, whereArguments);
                }
            }
            Log.d(TAG, "Data with id " + id + " updated");
        }
        return affectedRows != STATUS_NO_ROWS_UPDATED;
    }

    /**
     * Removes a media object from the database.
     * @param media - the media object to remove
     * @return returns whether or not the deletion was successful
     */
    public boolean deleteMedia(Media media) {
        int affectedRows = 0;
        if(!database.isReadOnly()) {
            long id = media.getId();
            if(id != Media.NOT_REGISTERED_ID) {
                if(media instanceof Movie) {
                    ContentValues whereStatements = new ContentValues();
                    whereStatements.put(Movie.COLUMN_ID, id);
                    String whereClause = DatabaseHelper.whereClause(whereStatements, DatabaseHelper.OPERATOR_EQUALS, null);
                    String[] whereArguments = DatabaseHelper.whereArguments(whereStatements, false);
                    affectedRows = database.delete(Movie.TABLE_NAME, whereClause, whereArguments);
                }
                Log.d(TAG, "Data with id " + id + " deleted");
            }
        }
        return affectedRows != STATUS_NO_ROWS_DELETED;
    }

    /**
     * Searches for media that fits to the passed input query and media search type.
     *
     * @param query - the input query from user
     * @param mediaSearchType - the type of media to search for
     * @return returns a list containing the media objects that fits to specified parameters
     */
    public ArrayList<Media> searchMedia(String query, int mediaSearchType) {
        ArrayList<Media> mediaItems = null;
        ContentValues whereStatements = new ContentValues();
        whereStatements.put(Media.COLUMN_TITLE, query);
        String whereClause = DatabaseHelper.whereClause(whereStatements,
                DatabaseHelper.OPERATOR_LIKE, null);
        String[] whereArguments = DatabaseHelper.whereArguments(whereStatements, true);
        Cursor mediaCursor = null;
        switch(mediaSearchType) {
            case Media.TYPE_MOVIE:
                mediaCursor = database.query(Movie.TABLE_NAME, null, whereClause, whereArguments, null, null, Movie.COLUMN_TITLE);
                mediaItems = Movie.buildList(mediaCursor);
                break;
        }
        Log.d(TAG, "Search result count : " + mediaItems.size());
        return mediaItems;
    }

    /**
     * Returns all media objects of a certain specified type.
     * @param mediaSearchType - the type of the media objects should be from
     * @return a list of all media objects of a certain type
     */
    public ArrayList<Media> getAllMedia(int mediaSearchType) {
        ArrayList<Media> mediaItems = null;
        switch(mediaSearchType) {
            case Media.TYPE_MOVIE:
                Cursor movieCursor = database.query(Movie.TABLE_NAME, null, null, null, null, null, Movie.COLUMN_TITLE);
                mediaItems = Movie.buildList(movieCursor);
                break;
        }
        Log.d(TAG, "Get all count : " + mediaItems.size());
        return mediaItems;
    }

}
