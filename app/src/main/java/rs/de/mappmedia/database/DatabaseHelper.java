package rs.de.mappmedia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Set;

import rs.de.mappmedia.database.models.Movie;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.database
 * Class:      DatabaseOpenHelper
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Defines the operator equals for directly comparison of table column values
     */
    public static final String OPERATOR_EQUALS = "=";

    /**
     * Defines the operator like for matching sequences of table column values
     */
    public static final String OPERATOR_LIKE = "LIKE";

    /**
     * Defines the connector and for connecting multiple where statements that
     * should all be true in order to retrieve a result
     */
    public static final String CONNECTOR_AND = "AND";

    /**
     * Defines the connector or for connecting multiple where statements where
     * only one of the statements should be true in order to retrieve a result
     */
    public static final String CONNECTOR_OR = "OR";

    /**
     * Defines the local stored database name
     */
    public static final String DATABASE_NAME = "media_database.db";

    /**
     * Defines the local stored database version
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * The constructor of DatabaseHelper class
     * @param context - context of the application
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database by executing all CREATE queries of all tables.
     * That method is only executed once.
     *
     * @param db - the database where to create the tables on
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Movie.CREATE_TABLE);
    }

    /**
     * Updates the database by executing the DROP queries of all tables and executes
     * the onCreate in order to recreate the database in the new version.
     *
     * @param db - the database where to drop the tables on
     * @param oldVersion - the old version identifier
     * @param newVersion - the new version identifier
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Movie.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Builds the whereClause string needed for database queries by combining each passed column
     * with a specified operator and wild card for the argument. If there are more than one column,
     * the columns get connected in between with the specified connector.
     *
     * @param whereStatements - the columns that should be involved in the later created whereClause
     * @param operator - the operator for comparison type of the column value
     * @param connector - the connector for connecting each whereStatement
     *
     * @return the built whereClause string for database queries
     */
    public static String whereClause(ContentValues whereStatements, String operator, String connector) {
        StringBuilder builder = new StringBuilder();
        Set<String> columns = whereStatements.keySet();
        int index = 0;
        for(String column : columns) {
            builder.append(column);
            builder.append(" ");
            builder.append(operator);
            builder.append(" ");
            builder.append("?");
            if(columns.size() > 1 && index != columns.size() - 1) {
                builder.append(" ");
                builder.append(connector);
                builder.append(" ");
            }
            index++;
        }
        return builder.toString();
    }

    /**
     * Retrieves the arguments from the passed whereStatements for database queries.
     * In case of the use of the LIKE operator the wild card for matching sequences is added.
     *
     * @param whereStatements - the whereStatements where to retrieve the arguments from
     * @param likeOperatorUsed - whether or not the LIKE operator was used in the whereStatements
     *
     * @return a list of all arguments for database queries
     */
    public static String[] whereArguments(ContentValues whereStatements, boolean likeOperatorUsed) {
        String[] arguments = new String[whereStatements.size()];
        int index = 0;
        for(String column : whereStatements.keySet()) {
            if(likeOperatorUsed)
                arguments[index] = "%" + whereStatements.get(column).toString() + "%";
            else
                arguments[index] = whereStatements.get(column).toString();
            index++;
        }
        return arguments;
    }

}
