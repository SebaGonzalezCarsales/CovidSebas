package au.com.carsales.basemodule

import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import au.com.carsales.basemodule.extension.lastElement
import au.com.carsales.basemodule.extension.pop
import au.com.carsales.basemodule.extension.push
import au.com.carsales.basemodule.util.checkSafeCommit
import au.com.carsales.basemodule.util.checkSafeRemoveFragment

/**
 * This base activity is for all navigation with replace fragment in your transaction (for example Sell Module)
 * There is not compatible with the new navigation
 */
abstract class BaseModuleNavigationWithReplaceFragmentActivity : BaseModuleActivity() {


    abstract fun layoutContainerId(): Int
    override fun layoutId(): Int = layoutContainerId()

    abstract fun frameContainer(): FrameLayout


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 1) {

            if (!supportFragmentManager.popBackStackImmediate(
                            mStacksFragment[currentTab]!!.lastElement(),
                            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)) {
                finish()
            } else {
                mStacksFragment[currentTab]!!.pop()
                //Filter the classes that extends of BaseFragment
                val newFragments = baseNavigationHelper?.getBaseFragments()
                if (newFragments!!.isEmpty()) {
                    finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun setNewFragment(newCurrentTab: String) {

        baseNavigationHelper?.hideAndShowFragments(newCurrentTab)
    }

    override fun replaceFragment(fragment: BaseModuleFragment) {
        if (!mStacksFragment[currentTab]!!.contains(fragment.tagName())) {
            mStacksFragment[currentTab]!!.push(fragment.tagName())
        }

        baseNavigationHelper!!.replaceFragmentWithoutTag(
                fragment,
                frameContainer().id, mStacksFragment[currentTab]!!.lastElement()
        )
    }

    override fun pushFragmentWithReplace(fragment: BaseModuleFragment, forCurrentTab: String?) {

        mStacksFragment[currentTab]!!.push(mStacksFragment[currentTab]?.lastElement() + "/" + fragment.tagName())

        baseNavigationHelper!!.replaceFragment(
                fragment,
                frameContainer().id, mStacksFragment[currentTab]!!.lastElement()
        )
    }

    override fun pushFragment(fragment: BaseModuleFragment, forCurrentTab: String?) {

//        if (!mStacksFragment[currentTab]!!.contains(mStacksFragment[currentTab]?.lastElement() + "/" + fragment.tagName())) {
        mStacksFragment[currentTab]!!.push(mStacksFragment[currentTab]?.lastElement() + "/" + fragment.tagName())
//        }

        baseNavigationHelper!!.replaceFragment(
                fragment,
                frameContainer().id, mStacksFragment[currentTab]!!.lastElement()
        )
    }

    override fun pushFragmentRootDetail(fragment: BaseModuleFragment) {

        currentTab = fragment.tagName()
        mStacksFragment[currentTab] = mutableListOf()
        mStacksFragment[currentTab]!!.push(currentTab)

        baseNavigationHelper?.replaceRootDetailFragment(
                fragment,
                frameContainer().id,
                mStacksFragment[currentTab]?.lastElement()!!
        )
    }

    override fun removeAllCurrentStack(currentForRemoveAll: String?) {

        mStacksFragment[currentTab]?.forEachIndexed { index, fragmentTag ->
            if (index != 0) {
                val fragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
                if (fragmentByTag != null && (fragmentByTag is BaseModuleFragment)) {
                    removeFragment(fragmentByTag)
                }

            }
        }
    }

    override fun removeAllStackFragmentsUpTo(fragment: BaseModuleFragment?, currentForRemoveUpTo: String?) {

        if (fragment != null) {
            mStacksFragment[currentTab]?.reversed()?.forEach {
                val fragmentTag = it
                val fragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
                if (fragmentByTag != null && fragmentByTag is BaseModuleFragment) {
                    if (fragmentByTag.tagIntoNavigation != fragment.tagIntoNavigation) {
                        removeFragment(fragmentByTag)
                    }
                }
            }
        }

    }

    override fun addFragment(fragment: BaseModuleFragment, onBackTag: String?) {
        mStacksFragment[currentTab]!!.push(onBackTag!!)
        baseNavigationHelper?.replaceFragmentWithoutTag(
                fragment,
                frameContainer().id,
                mStacksFragment[currentTab]?.lastElement()!!
        )
    }

    override fun isShowingFragment(fragment: BaseModuleFragment): Boolean {
        return if (baseNavigationHelper != null) {
            return baseNavigationHelper!!.isFragmentShowing(fragment)
        } else {
            true
        }
    }

    override fun removeFragment(fragment: BaseModuleFragment, currentForRemoveAll: String?) {
        supportFragmentManager.checkSafeRemoveFragment {
            val tagFragment = fragment.tagIntoNavigation
            mStacksFragment[currentTab]!!.remove(tagFragment)
            supportFragmentManager.popBackStackImmediate(tagFragment, 1)
        }
    }


    override fun getLastFragmentAdded(): androidx.fragment.app.Fragment? {
        val stack = mStacksFragment[currentTab]
        stack?.lastOrNull {
            if (it != currentTab) {
                return supportFragmentManager.findFragmentByTag(it)
            }
            return null
        }
        return null
    }

    override fun removeFragmentsUpTo(tagName: String) {
        if (supportFragmentManager.backStackEntryCount > 1) {

            if (!supportFragmentManager.popBackStackImmediate(
                            tagName,
                            0
                    )
            ) {
                finish()

            } else {
                do {
                    mStacksFragment[currentTab]!!.pop()
                } while (!mStacksFragment[currentTab]!!.lastElement().equals(tagName))


                //Filter the classes that extends of BaseFragment
                val newFragments = baseNavigationHelper?.getBaseFragments()
                if (newFragments!!.isEmpty()) {
                    finish()
                }
            }
        } else {
            finish()
        }
    }
}