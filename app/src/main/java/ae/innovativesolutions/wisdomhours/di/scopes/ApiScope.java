package ae.innovativesolutions.wisdomhours.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by zuhaib.ahmad on 12/22/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiScope {
}