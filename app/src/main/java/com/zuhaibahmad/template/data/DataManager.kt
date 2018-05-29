

package com.zuhaibahmad.template.data

import com.zuhaibahmad.template.data.local.db.DbHelper
import com.zuhaibahmad.template.data.local.prefs.PreferencesHelper
import com.zuhaibahmad.template.data.remote.ApiHelper
import com.zuhaibahmad.template.utils.OpenClass
import io.reactivex.Completable

/**
 * Created by Zuhaib on 9/17/2017.
 */
@OpenClass
interface DataManager : DbHelper, PreferencesHelper, ApiHelper {
}
