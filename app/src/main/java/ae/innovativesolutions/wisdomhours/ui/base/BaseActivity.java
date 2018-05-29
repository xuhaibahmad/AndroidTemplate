

package ae.innovativesolutions.wisdomhours.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;

import ae.innovativesolutions.wisdomhours.R;
import ae.innovativesolutions.wisdomhours.utils.CommonUtils;
import ae.innovativesolutions.wisdomhours.utils.NetworkUtils;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 * <p>
 * Base class for all activity instances
 */

public abstract class BaseActivity<V extends BasePresenter> extends AppCompatActivity implements BaseFragment.Callback {

	// TODO
	// this can probably depend on isLoading variable of BasePresenter,
	// since its going to be common for all the activities
	private ProgressDialog mProgressDialog;

	private V mPresenter;
	private Unbinder binder;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		performDependencyInjection();
		super.onCreate(savedInstanceState);
		performDataBinding();
	}

	private void performDataBinding() {
		setStatusBarColor();
		setContentView(getLayoutId());
		binder = ButterKnife.bind(this);
		mPresenter = mPresenter == null ? getPresenter() : mPresenter;
	}

	protected void setStatusBarColor() {
		Window window = getWindow();

		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

		// finally change the color
		window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return mPresenter;
	}

	@Override
	protected void onDestroy() {
		binder.unbind();
		if (mPresenter != null) {
			mPresenter.onCleared();
		}
		super.onDestroy();
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void requestPermissionsSafely(String[] permissions, int requestCode) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			requestPermissions(permissions, requestCode);
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	public boolean hasPermission(String permission) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
				checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}

	@Override
	public void onFragmentAttached() {

	}

	@Override
	public void onFragmentDetached(String tag) {

	}

	public void hideKeyboard() {
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager)
					getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
		}
	}

	public boolean isNetworkConnected() {
		return NetworkUtils.isNetworkConnected(getApplicationContext());
	}

	public void showLoading() {
		hideLoading();
		mProgressDialog = CommonUtils.showLoadingDialog(this);
	}

	public void hideLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.cancel();
		}
	}

	protected IconDrawable getIcon(Icon icon) {
		return getIcon(icon, R.color.light_gray, 16);
	}

	protected IconDrawable getIcon(Icon icon, int color) {
		return getIcon(icon, color, 16);
	}

	protected IconDrawable getIcon(Icon icon, int color, int size) {
		return new IconDrawable(this, icon)
				.colorRes(color)
				.sizeDp(size);
	}

	public void openActivityOnTokenExpire() {
		// TODO Implement if needed
	}

	public V getPresenter() {
		return (V) getLastNonConfigurationInstance();
	}

	/**
	 * @return layout resource id
	 */
	public abstract
	@LayoutRes
	int getLayoutId();

	public void performDependencyInjection() {
		AndroidInjection.inject(this);
	}

}

