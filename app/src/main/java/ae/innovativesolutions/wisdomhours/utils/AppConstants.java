

package ae.innovativesolutions.wisdomhours.utils;

import ae.innovativesolutions.wisdomhours.BuildConfig;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

@SuppressWarnings({"WeakerAccess", "ArraysAsListWithZeroOrOneArgument", "unused"})
public final class AppConstants {

	private AppConstants() {
		// This utility class is not publicly instantiable
	}

	// @formatter:off
    public static final String TAG 							= BuildConfig.APP_NAME;
    public static final String PREFERENCES_NAME				= TAG + ".prefs";
    public static final String LOG_FILE_NAME                = TAG + "-Log.txt";
    public static final String DIRECTORY_NAME               = TAG + " Log";
    public static final String DB_NAME                      = TAG + ".db";

    public static final boolean TEST_BUILD                  = false;

    public static final String BASE_URL                     = "http://www.roayahnews.com/";
    public static final String LOGO_URL                     = BASE_URL + "PublicStyle/logo.png";

    public static final int REQUEST_PERMISSION_ALL			= 0;

    public static final String STATUS_CODE_SUCCESS          = "success";
    public static final String STATUS_CODE_FAILED           = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR     = 0;

    public static final long NULL_INDEX = -1L;

    public static final String TIMESTAMP_FORMAT             = "yyyyMMdd_HHmmss";

    public static final int FEED_ITEM_TYPE_HEADER           = 0;
    public static final int FEED_ITEM_TYPE_NEWS             = 1;

    public static class DatabaseTables {
    }

    // @formatter:on
}
