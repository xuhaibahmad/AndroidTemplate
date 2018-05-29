package com.zuhaibahmad.template.di.components;

import javax.inject.Singleton;

import com.zuhaibahmad.template.App;
import com.zuhaibahmad.template.di.builder.ActivityBuilder;
import com.zuhaibahmad.template.di.modules.AppModule;
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