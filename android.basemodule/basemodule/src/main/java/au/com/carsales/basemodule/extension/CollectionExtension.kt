package au.com.carsales.basemodule.extension

/**
 * Created by Matias Arancibia on 25 Nov, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */

fun <T> Collection<T>?.isNotEmptyAndNotNull(): Boolean {
    return this?.isNotEmpty() ?: return false
}