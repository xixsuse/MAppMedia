package rs.de.mappmedia.database.models;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.database
 * Class:      DAO
 */

public abstract class Media {

    public static final long NOT_REGISTERED = -1;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";

    protected long id;
    protected String title;

    public Media(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
