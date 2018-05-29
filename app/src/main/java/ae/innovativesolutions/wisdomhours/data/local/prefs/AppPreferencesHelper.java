

package ae.innovativesolutions.wisdomhours.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import ae.innovativesolutions.wisdomhours.di.scopes.PreferenceScope;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

public class AppPreferencesHelper implements PreferencesHelper {

	private final SharedPreferences mPrefs;

	@Inject
	public AppPreferencesHelper(Context context, @PreferenceScope String prefFileName) {
		mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
	}
}
