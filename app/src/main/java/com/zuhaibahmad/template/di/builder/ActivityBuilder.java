package com.zuhaibahmad.template.di.builder;

import com.zuhaibahmad.template.features.main.MainActivity;
import com.zuhaibahmad.template.features.main.MainModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

@Module
public abstract class ActivityBuilder {

	@ContributesAndroidInjector(modules = {MainModule.class})
	abstract MainActivity bindSignUpActivity();

}