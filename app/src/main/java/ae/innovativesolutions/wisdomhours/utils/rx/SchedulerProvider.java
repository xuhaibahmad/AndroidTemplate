

package ae.innovativesolutions.wisdomhours.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
