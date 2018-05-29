package ae.innovativesolutions.wisdomhours.ui.signup;

import android.content.Context;
import android.widget.Toast;

import ae.innovativesolutions.wisdomhours.data.DataManager;
import ae.innovativesolutions.wisdomhours.ui.base.BasePresenter;
import ae.innovativesolutions.wisdomhours.utils.rx.SchedulerProvider;

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

class SignUpPresenter extends BasePresenter<SignUpView> {

	private Context context;

	public SignUpPresenter(Context context, DataManager dataManager, SchedulerProvider scheduler) {
		super(dataManager, scheduler);
		this.context = context;
	}

	@Override
	public void onStart() {
		super.onStart();
		Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show();
	}
}
