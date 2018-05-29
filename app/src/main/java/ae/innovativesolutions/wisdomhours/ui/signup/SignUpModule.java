package ae.innovativesolutions.wisdomhours.ui.signup;

import android.content.Context;

import ae.innovativesolutions.wisdomhours.data.DataManager;
import ae.innovativesolutions.wisdomhours.utils.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

@Module
public class SignUpModule {

	@Provides
	SignUpPresenter provideSignUpPresenter(Context context, DataManager dataManager,
	                                       SchedulerProvider schedulerProvider) {
		return new SignUpPresenter(context, dataManager, schedulerProvider);
	}


}
