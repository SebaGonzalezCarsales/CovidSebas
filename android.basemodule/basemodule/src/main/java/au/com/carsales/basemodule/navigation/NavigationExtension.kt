package au.com.carsales.basemodule.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.R

/**
 * Created by anibalbastias on 2019-08-09.
 */

fun BaseModuleFragment.navigateFragmentDetails(navDetailHost: Int,
                                               actionNav: Int,
                                               bundle: Bundle? = null,
                                               isRoot: Boolean? = true) {
    if (context?.resources?.getBoolean(R.bool.isTablet)!!) {
        childFragmentManager.navigateFragment(navDetailHost, actionNav, bundle, isRoot)
    } else {
        Navigation.findNavController(view!!).navigate(actionNav, bundle)
    }
}

fun FragmentManager.navigateFragment(navHost: Int,
                                     actionNav: Int,
                                     bundle: Bundle? = null,
                                     isDetail: Boolean? = false) {
    try {
        val navHostFragment: NavHostFragment = findFragmentById(navHost) as NavHostFragment?
                ?: return

        navHostFragment.navController.apply {
            if (isDetail!!) navigateUp()
            navigate(actionNav, bundle)
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun BaseModuleFragment.popFragment(fragmentManager: FragmentManager, navHost: Int) {
    try {
        if (context?.resources?.getBoolean(R.bool.isTablet)!!) {
            val navHostFragment: NavHostFragment = fragmentManager.findFragmentById(navHost) as NavHostFragment?
                    ?: return
            navHostFragment.navController.navigateUp()
        } else {
            Navigation.findNavController(view!!).navigateUp()
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}