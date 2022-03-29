package au.com.carsales.basemodule

import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.lastElement
import au.com.carsales.basemodule.extension.pop
import au.com.carsales.basemodule.extension.push
import au.com.carsales.basemodule.util.checkSafeCommit
import au.com.carsales.basemodule.util.checkSafeRemoveFragment

abstract class BaseModuleNavigationActivity : BaseModuleActivity() {


    abstract fun layoutContainerId(): Int
    override fun layoutId(): Int = layoutContainerId()

    abstract fun frameContainer(): FrameLayout


    override fun onBackPressed() {

        supportFragmentManager.checkSafeCommit {

            if (supportFragmentManager.fragments.size == 1) {
                (supportFragmentManager.fragments.first() as? BaseModuleFragment)?.onBackPressFragment()
                finish()
                return@checkSafeCommit
            }

            // Try to find the fragment
            val fragmentToRemove =
                (supportFragmentManager.findFragmentByTag(mStacksFragment[currentTab]?.lastElement()))

            // Let Crashlytics know that fragment wasn't found. This
            // has to be fixed soon with new jetpack Navigation component
//                        if (fragmentToRemove == null)
//                            Crashlytics.logException(Exception("Fragment to remove is null"))

            (fragmentToRemove as? BaseModuleFragment)?.let {
                it.onBackPressFragment()
                supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()

                if (mStacksFragment[currentTab]?.size!! > 1) {

                    mStacksFragment[currentTab]?.pop()
                    setNewFragment(mStacksFragment[currentTab]?.lastElement() ?: currentTab)
                } else {
                    finish()
                }
            } ?: kotlin.run {

                finish()
            }

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

        mStacksFragment[currentTab]!!.push(mStacksFragment[currentTab]?.lastElement() + "/" + fragment.tagName())

        fragment.tagIntoNavigation = mStacksFragment[currentTab]!!.lastElement()
        baseNavigationHelper!!.addFragment(
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

        val firstElement = mStacksFragment[currentTab]?.first()
        mStacksFragment[currentTab]?.reversed()?.forEachIndexed { index, fragmentTag ->

            if (firstElement != fragmentTag) {
                val fragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
                if (fragmentByTag != null && (fragmentByTag is BaseModuleFragment)) {
                    removeFragment(fragmentByTag)
                }
            }
        }
        mStacksFragment[currentTab]?.clear()
        mStacksFragment[currentTab]?.push(firstElement!!)
        baseNavigationHelper?.hideAndShowFragments(
            mStacksFragment[currentTab]?.first() ?: String.empty()
        )
    }

    override fun removeAllStackFragmentsUpTo(
        fragment: BaseModuleFragment?,
        currentForRemoveUpTo: String?
    ) {

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
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()
            supportFragmentManager.executePendingTransactions()
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