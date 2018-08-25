
package com.example.lengary_l.wanandroidtodo.util

import android.text.TextUtils

/**
 * Created by CoderLengary
 */

fun String.isNotValid(): Boolean = (TextUtils.isEmpty(this)||this.contains(" "))

