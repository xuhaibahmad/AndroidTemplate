package com.zuhaibahmad.template.data

import java.util.*

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 *
 * Data models used in the app
 */

data class User(val name: String, val email: String, val timestampJoined: HashMap<String, Any>)
