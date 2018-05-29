package ae.innovativesolutions.wisdomhours.di.components;

import javax.inject.Singleton;

import ae.innovativesolutions.wisdomhours.App;
import ae.innovativesolutions.wisdomhours.di.builder.ActivityBuilder;
import ae.innovativesolutions.wisdomhours.di.modules.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
		AndroidSupportInjectionModule.class,
		ActivityBuilder.class,
		AppModule.class
})
public interface AppComponent {

	@Component.Builder
	interface Builder {
		@BindsInstance
		Builder application(App application);
		AppComponent build();
	}

	void inject(App app);
}