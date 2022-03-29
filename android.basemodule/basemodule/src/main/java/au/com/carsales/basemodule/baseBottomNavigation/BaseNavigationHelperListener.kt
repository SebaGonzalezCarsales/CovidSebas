package au.com.carsales.basemodule.baseBottomNavigation

import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import au.com.carsales.basemodule.extension.empty

interface BaseNavigationHelperListener {

    /**
     * Replace fragments to stack
     */
    fun replaceFragment(fragment: BaseModuleFragment) {}

    /**
     * Get BottomNavigation Item
     */
    fun getBottomNavigationItem(position: Int): BottomNavigationItemView? {
        return null
    }

    /**
     * Add fragments to stack
     */
    fun pushFragment(fragment: BaseModuleFragment, forCurrentTab: String? = null) {}
    fun pushFragmentWithReplace(fragment: BaseModuleFragment, forCurrentTab: String? = null) {}

    /**
     * Add first fragment to stack.
     */
    fun pushFragmentRootDetail(fragment: BaseModuleFragment) {}

    /**
     * Add New root fragment with your navigation in tablet or phone
     */
    fun addFragmentRootToNavigation(fragment: BaseModuleFragment) {}

    /**
     * Add fragment without tag if is necessary
     */
    fun addFragment(fragment: BaseModuleFragment, onBackTag: String?) {}

    /**
     * Return true if the fragment is into stack or false
     */
    fun isShowingFragment(fragment: BaseModuleFragment): Boolean {
        return true
    }
    /**
     * Return true if the fragment is into stack or false
     */
    fun isShowingFragment(fragmentTagName: String): Boolean {
        return true
    }

    /**
     * Change position menu navigation
     */
    fun changeCurrentPosition(position: Int) {}

    fun navigateDrivenByAPI(action: ActionViewData?) {}

    /**
     * Remove fragment
     */
    fun removeFragment(fragment: BaseModuleFragment, currentForRemoveAll: String? = null) {}

    /**
     * Remove all stack fragment less first root fragment
     */
    fun removeAllCurrentStack(currentForRemoveAll: String? = null) {}


    /**
     * Remove all stack fragment up to the indicated Fragment
     */

    fun removeAllStackFragmentsUpTo(fragment: BaseModuleFragment?, currentForRemoveUpTo: String? = null) {}


    /**
     * get last fragment added
     */
    fun getLastFragmentAdded(): androidx.fragment.app.Fragment? {
        return null
    }

    fun getListBaseFragment(): List<BaseModuleFragment> {
        return emptyList()
    }

    fun getInstanceFragmentInStack(fragment: BaseModuleFragment): BaseModuleFragment? {
        return null
    }

    fun lasElemetofStack(): String {
        return String.empty()
    }

    fun popFromStack()

    fun removeFragmentsUpTo(tagName: String) {}

    fun showOrUpdateBadgeBottomItem(positionBottomItem: Int, count: Int) {}

    fun removeBadgeBottomItem(positionBottomItem: Int) {}

    fun getCurrentTabForNavigation(): String {
        return ""
    }

    fun checkIfFullScreen(currentFragment: BaseModuleFragment) {}

    fun getBottomNavigationView(): BottomNavigationView? {
        return null
    }
}
