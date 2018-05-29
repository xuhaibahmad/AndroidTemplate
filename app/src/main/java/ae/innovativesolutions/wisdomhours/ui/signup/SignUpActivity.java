package ae.innovativesolutions.wisdomhours.ui.signup;

import android.os.Bundle;

import javax.inject.Inject;

import ae.innovativesolutions.wisdomhours.R;
import ae.innovativesolutions.wisdomhours.ui.base.BaseActivity;

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

public class SignUpActivity extends BaseActivity<SignUpPresenter> implements SignUpView {

	@Inject SignUpPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	protected void onResume() {
		super.onResume();

		presenter.setView(this);
		presenter.onStart();
	}

	/**
	 * @return layout resource id
	 */
	@Override
	public int getLayoutId() {
		return R.layout.activity_sign_up;
	}
}
