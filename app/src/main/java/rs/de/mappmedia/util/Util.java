package rs.de.mappmedia.util;


import rs.de.mappmedia.R;
import static rs.de.mappmedia.database.models.Media.AGE_RESTRICTION_0;
import static rs.de.mappmedia.database.models.Media.AGE_RESTRICTION_6;
import static rs.de.mappmedia.database.models.Media.AGE_RESTRICTION_12;
import static rs.de.mappmedia.database.models.Media.AGE_RESTRICTION_16;
import static rs.de.mappmedia.database.models.Media.AGE_RESTRICTION_18;

/**
 * 2016 created by Rene
 * Project:    MAppMedia
 * Package:    rs.de.mappmedia.util
 * Class:      Util
 */

public final class Util {

    /**
     * Interprets the passed age restriction value and returns the color
     * that the representing restriction label has. If the age restriction isn't
     * found a default color is returned.
     *
     * @param ageRestriction - the age restriction value to interpret
     * @return the color identifier for the color to display
     *
     */
    public static int interpretMediaAgeRestrictionValue(int ageRestriction) {
        int color;
        switch(ageRestriction) {
            case AGE_RESTRICTION_0:
                color = R.color.color_age_restriction_0;
                break;
            case AGE_RESTRICTION_6:
                color = R.color.color_age_restriction_6;
                break;
            case AGE_RESTRICTION_12:
                color = R.color.color_age_restriction_12;
                break;
            case AGE_RESTRICTION_16:
                color = R.color.color_age_restriction_16;
                break;
            case AGE_RESTRICTION_18:
                color = R.color.color_age_restriction_18;
                break;
            default:
                color = R.color.color_age_restriction_0;
                break;
        }
        return color;
    }


}
