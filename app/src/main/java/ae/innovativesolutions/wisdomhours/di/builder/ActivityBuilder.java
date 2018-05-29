package ae.innovativesolutions.wisdomhours.di.builder;

import ae.innovativesolutions.wisdomhours.ui.signup.SignUpActivity;
import ae.innovativesolutions.wisdomhours.ui.signup.SignUpModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

@Module
public abstract class ActivityBuilder {

	@ContributesAndroidInjector(modules = {SignUpModule.class})
	abstract SignUpActivity bindSignUpActivity();

}