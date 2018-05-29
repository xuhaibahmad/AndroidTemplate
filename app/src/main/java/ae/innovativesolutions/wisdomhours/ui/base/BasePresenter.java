

package ae.innovativesolutions.wisdomhours.ui.base;

import ae.innovativesolutions.wisdomhours.data.DataManager;
import ae.innovativesolutions.wisdomhours.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

public abstract class BasePresenter<N> {

    private N mView;
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private boolean mIsLoading = false;

    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void setView(N view) {
        this.mView = view;
    }

    public N getView() {
        return mView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public boolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    protected void onCleared() {
        mCompositeDisposable.dispose();
    }

    public void onStart(){}
}
