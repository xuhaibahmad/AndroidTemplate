package ae.innovativesolutions.wisdomhours.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ae.innovativesolutions.wisdomhours.data.local.db.DbHelper;
import ae.innovativesolutions.wisdomhours.data.local.prefs.PreferencesHelper;
import ae.innovativesolutions.wisdomhours.data.remote.ApiHelper;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }
}
