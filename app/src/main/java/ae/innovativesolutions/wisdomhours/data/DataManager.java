

package ae.innovativesolutions.wisdomhours.data;

import ae.innovativesolutions.wisdomhours.data.local.db.DbHelper;
import ae.innovativesolutions.wisdomhours.data.local.prefs.PreferencesHelper;
import ae.innovativesolutions.wisdomhours.data.remote.ApiHelper;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
}
