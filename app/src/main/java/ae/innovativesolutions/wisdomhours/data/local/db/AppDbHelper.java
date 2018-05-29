

package ae.innovativesolutions.wisdomhours.data.local.db;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }
}
