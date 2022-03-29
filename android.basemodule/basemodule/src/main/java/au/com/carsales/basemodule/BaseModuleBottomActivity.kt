package au.com.carsales.basemodule

import android.os.Bundle
import android.text.TextUtils.TruncateAt.MARQUEE
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import au.com.carsales.basemodule.baseBottomNavigation.NavigationWithType
import au.com.carsales.basemodule.extension.*
import au.com.carsales.basemodule.util.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.set


abstract class BaseModuleBottomActivity : BaseModuleActivity() {

    // Name root FRAGMENT inside in first element of TAB
    abstract fun navigationActivityType(): NavigationWithType

    abstract fun listRootDetailsFragment(): List<androidx.fragment.app.Fragment>
    abstract fun listMenuIDBottomNavigation(): List<Int>
    abstract fun positionRootFragment(): Int

    abstract fun layoutBottomId(): Int

    abstract fun rootFrameContainer(): FrameLayout
    abstract fun detailFrameContainer(): FrameLayout
    abstract fun containerFrameViewGroup(): ViewGroup
    abstract fun bottomNavigationView(): BottomNavigationView?
    abstract fun emptyFragment(): androidx.fragment.app.Fragment

    private var currentPositionTab = 0
    private var menuView: BottomNavigationMenuView? = null

    override fun layoutId(): Int = layoutBottomId()

    var navigationList: ArrayList<String>? = arrayListOf()

    companion object {
        const val PREFIX_NEW_ROOT = "Root/"
    }

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                var order = item.order

                currentPositionTab = order

                if (order !in listRootDetailsFragment().indices) {
                    order = listRootDetailsFragment().indices.first
                }

                currentTab = listRootDetailsFragment()[order].tagName()
                val backStack = mStacksFragment[currentTab]

                if (backStack?.isNullOrEmpty().isTrue()) {
                    mStacksFragment[currentTab]?.push(currentTab)
                }

                val nameRootFragment = getRootNameFragment()

                if (nameRootFragment != null) {
                    changeNewRootForNavigation(nameRootFragment)
                    currentChangeTab(nameRootFragment, order)
                } else {
                    changeRootTabFragment(mStacksFragment[currentTab]?.lastElement(), order)
                    currentChangeTab(mStacksFragment[currentTab]?.lastElement().orEmpty(), order)
                }

                for (i in 0 until menuView?.childCount!!) {
                    val menuItem = menuView?.getChildAt(i) as BottomNavigationItemView
                    val activeLabel = menuItem.findViewById<View>(R.id.largeLabel)

                    if (activeLabel is TextView) {
                        activeLabel.let { label ->
                            label.isSingleLine = true
                            label.isSelected = true
                            label.ellipsize = MARQUEE
                            label.marqueeRepeatLimit = -1 // Value: -1 = Marquee Repeat Forever
                        }
                    }
                }

                return@OnNavigationItemSelectedListener true
            }

    override fun initView(savedInstanceState: Bundle?) {

        menuView = bottomNavigationView()?.getChildAt(0) as? BottomNavigationMenuView

        menuView?.let { _ ->
            bottomNavigationView()?.let { bottomNavigationView ->
                bottomNavigationView.setOnNavigationItemSelectedListener(
                        mOnNavigationItemSelectedListener
                )
                bottomNavigationView.disableShiftMode()

                if (savedInstanceState != null) {
                    currentPositionTab = savedInstanceState.getInt("currentPositionTab")
                    navigationList = savedInstanceState.getStringArrayList("navigationList")
                    checkOrientationScreen()

                    bottomNavigationView.menu.getItem(currentPositionTab)?.isChecked = true

                } else {
                    configurationRootFragment(listRootDetailsFragment(), positionRootFragment())
                    bottomNavigationView()?.selectedItemId =
                            bottomNavigationView.menu.getItem(positionRootFragment()).itemId
                }
            }
        }
    }

    override fun savedInstanceOfData(outState: Bundle) {
        outState.putSerializable("currentPositionTab", currentPositionTab)
        outState.putStringArrayList("navigationList", navigationList)
    }

    private fun configurationRootFragment(listFragment: List<Fragment>, rootPositionFragment: Int) {
        listFragment.forEachIndexed { index, fragment ->
            mStacksFragment[fragment.tagName()] = mutableListOf()
            if (index == rootPositionFragment) {
                mStacksFragment[fragment.tagName()]!!.push(fragment.tagName())
            }
            navigationList?.add(fragment.tagName())
        }

        currentTab = if (rootPositionFragment >= listFragment.size) {
            listFragment[0].tagName()
        } else {
            listFragment[rootPositionFragment].tagName()
        }
    }

    private fun checkOrientationScreen() {

        if (getRootNameFragment() != null) {
            if (supportFragmentManager.findFragmentByTag(getRootNameFragment()) != null) {
                val fragmentWithTag =
                        supportFragmentManager.findModuleFragmentByTag(getRootNameFragment()!!)
                if (fragmentWithTag is BaseModuleFragment?) {
                    configurationScreenWithOrientationDevice(fragmentWithTag)
                }
            }
        } else {
            if (supportFragmentManager.findFragmentByTag(currentTab) != null) {
                val fragmentWithTag = supportFragmentManager.findModuleFragmentByTag(currentTab)
                if (fragmentWithTag is BaseModuleFragment?) {
                    configurationScreenWithOrientationDevice(fragmentWithTag)
                }
            }
        }

    }

    private fun configurationScreenWithOrientationDevice(fragment: BaseModuleFragment?) {

        if (containerFrameViewGroup() is LinearLayout && fragment != null) {
            if (fragment.isFullScreen()) {
                baseNavigationHelper?.hideContainerDetails(
                        rootFrameContainer(),
                        detailFrameContainer()
                )
            } else {
                baseNavigationHelper?.showContainerList(
                        rootFrameContainer(),
                        detailFrameContainer()
                )
            }
        }
    }

    private fun changeRootTabFragment(nameFragment: String?, positionTab: Int) {

        if (supportFragmentManager.findModuleFragmentByTag(nameFragment!!) != null) {
            changeFragmentTab(nameFragment)
        } else {
            changeRootTab(listRootDetailsFragment()[positionTab] as BaseModuleFragment)
        }
    }

    /**
     * change new root in same navigation
     */
    private fun changeNewRootForNavigation(nameRoot: String) {

        if (supportFragmentManager.findModuleFragmentByTag(nameRoot) != null) {

            when (navigationActivityType()) {

                NavigationWithType.NAVIGATION_TABLET_MULTIPANEL_WITH_BOTTOM -> {

                    val rootFragmentWithTag =
                            supportFragmentManager.findModuleFragmentByTag(nameRoot) as BaseModuleFragment?
                    configurationScreenWithOrientationDevice(rootFragmentWithTag)
                    rootFragmentWithTag?.idNavigation = currentTab
                    rootFragmentWithTag?.idPositionTab = currentPositionTab

                    val indexValue = navigationList?.indexOf(currentTab)
                    navigationList?.removeAt(indexValue ?: 0)
                    navigationList?.add(0, currentTab)

                    var fragmentForDetailsWithTag: BaseModuleFragment? = null

                    if (mStacksFragment[currentTab]?.lastElement() != nameRoot) {
                        fragmentForDetailsWithTag =
                                supportFragmentManager.findModuleFragmentByTag(mStacksFragment[currentTab]?.last()!!) as BaseModuleFragment?
                        fragmentForDetailsWithTag?.idNavigation = currentTab
                        fragmentForDetailsWithTag?.idPositionTab = currentPositionTab
                    }

                    loadFragment(
                            fragment = fragmentForDetailsWithTag,
                            rootFragment = rootFragmentWithTag
                    )

                }
                NavigationWithType.NAVIGATION_PHONE_WITH_BOTTOM -> {

                    val fragmentWithTag =
                            supportFragmentManager.findModuleFragmentByTag(nameRoot) as BaseModuleFragment?
                    fragmentWithTag?.idNavigation = currentTab
                    fragmentWithTag?.idPositionTab = currentPositionTab
                    loadFragment(fragmentWithTag)
                }
            }
        }

    }

    /**
     * change tab but root it exists in navigation
     */
    private fun changeFragmentTab(nameFragment: String) {

        when (navigationActivityType()) {

            NavigationWithType.NAVIGATION_TABLET_MULTIPANEL_WITH_BOTTOM -> {

                val fragmentWithTag =
                        supportFragmentManager.findModuleFragmentByTag(currentTab) as BaseModuleFragment?
                configurationScreenWithOrientationDevice(fragmentWithTag)
                fragmentWithTag?.idNavigation = currentTab
                fragmentWithTag?.idPositionTab = currentPositionTab

                val indexValue = navigationList?.indexOf(currentTab)
                navigationList?.removeAt(indexValue ?: 0)
                navigationList?.add(0, currentTab)

                var fragmentForDetailsWithTag: BaseModuleFragment? = null

                if (currentTab != nameFragment) {
                    fragmentForDetailsWithTag =
                            supportFragmentManager.findModuleFragmentByTag(nameFragment) as BaseModuleFragment?
                    fragmentForDetailsWithTag?.idNavigation = currentTab
                    fragmentForDetailsWithTag?.idPositionTab = currentPositionTab
                }

                loadFragment(fragment = fragmentForDetailsWithTag, rootFragment = fragmentWithTag)


            }
            else -> {
                val fragmentWithTag =
                        supportFragmentManager.findModuleFragmentByTag(nameFragment) as BaseModuleFragment?
                fragmentWithTag?.idNavigation = currentTab
                fragmentWithTag?.idPositionTab = currentPositionTab

                val indexValue = navigationList?.indexOf(currentTab)
                navigationList?.removeAt(indexValue ?: 0)
                navigationList?.add(0, currentTab)

                loadFragment(fragmentWithTag)
            }
        }

    }

    /**
     * change tab but root fragment don't exists in navigation
     */
    private fun changeRootTab(fragment: BaseModuleFragment) {

        fragment.idNavigation = currentTab
        fragment.idPositionTab = currentPositionTab
        fragment.tagIntoNavigation = currentTab

        val indexValue = navigationList?.indexOf(currentTab)
        navigationList?.removeAt(indexValue ?: 0)
        navigationList?.add(0, currentTab)

        when (navigationActivityType()) {

            NavigationWithType.NAVIGATION_TABLET_MULTIPANEL_WITH_BOTTOM -> {

                if (fragment.tagIntoNavigation == "") {
                    fragment.tagIntoNavigation = currentTab
                }

                configurationScreenWithOrientationDevice(fragment)
                loadFragment(fragment = null, rootFragment = fragment)

            }
            else -> {
                loadFragment(fragment)
            }
        }
    }


    /**
     *  Select navigation item
     */
    private fun setNavigationSelectedItem() {

        if (supportFragmentManager.findModuleFragmentByTag(mStacksFragment[currentTab]?.lastElement()!!) != null) {

            val fragmentToNavigate = supportFragmentManager.findModuleFragmentByTag(
                    mStacksFragment[currentTab]?.lastElement()!!
            ) as BaseModuleFragment

            currentChangeTab(
                    mStacksFragment[currentTab]?.lastElement().orEmpty(),
                    fragmentToNavigate.idPositionTab
            )

            bottomNavigationView()?.menu?.getItem(fragmentToNavigate.idPositionTab)?.isChecked = true
        }
    }

    /**
     * return the next current tab
     */
    private fun nextBackStack(): String? {

        if (mStacksFragment[navigationList!![0]]?.size!! <= 1) {
            var newCurrent: String? = null
            run loop@{
                navigationList?.forEachIndexed { index, data ->
                    if (mStacksFragment[data]?.size!! > 0 && index != 0) {
                        newCurrent = data
                        Collections.rotate(navigationList, -1)
                        return@loop
                    }
                }
            }
            return newCurrent
        } else {
            return navigationList!![0]
        }
    }

    /**
     * set the new fragment in stack
     */
    private fun setNewFragment(tagFragment: String) {
        val newFragmentCurrent =
                supportFragmentManager.findFragmentByTag(mStacksFragment[tagFragment]?.lastElement()) as? BaseModuleFragment


        loadFragment(newFragmentCurrent)
        setNavigationSelectedItem()
        configurationScreenWithOrientationDevice(newFragmentCurrent)
    }

    override fun onBackPressed() {
        val newCurrentTab = nextBackStack()

        if (newCurrentTab != null) {

            // When user is in the same tab
            if (currentTab == listRootDetailsFragment()[positionRootFragment()].tagName() && mStacksFragment[currentTab]?.size == 1) {

                navigationList?.removeAt(navigationList?.indexOf(newCurrentTab) ?: 0)
                navigationList?.add(0, newCurrentTab)

                if (navigationList!![0] == currentTab) {
                    finish()
                } else {
                    supportFragmentManager.executePendingTransactions()

                    val newCurrentFragment =
                            (supportFragmentManager.findFragmentByTag(mStacksFragment[navigationList!![0]]?.lastElement()) as? BaseModuleFragment)

                    if (newCurrentFragment != null) {
                        currentTab = newCurrentTab
                        loadFragment(newCurrentFragment)
                        configurationScreenWithOrientationDevice(newCurrentFragment)
                        setNavigationSelectedItem()
                    }

                }

                // When user has change to a new tab
            } else if (currentTab == newCurrentTab) {

                if (mStacksFragment[currentTab]?.size!! > 1) {
                    supportFragmentManager.checkSafeCommit {

                        // Try to find the fragment
                        val fragmentToRemove =
                                (supportFragmentManager.findFragmentByTag(mStacksFragment[currentTab]?.lastElement()))

                        // Let Crashlytics know that fragment wasn't found. This
                        // has to be fixed soon with new jetpack Navigation component
//                        if (fragmentToRemove == null) {
//                            Crashlytics.logException(Exception("Fragment to remove is null"))
//                        }

                        fragmentToRemove?.let {

                            supportFragmentManager.beginTransaction()
//                                .setCustomAnimations(0, R.anim.slide_out_right)
                                    .remove(fragmentToRemove as BaseModuleFragment)
                                    .commitNow()

                            if (mStacksFragment[currentTab]?.size!! > 0) {
                                mStacksFragment[currentTab]?.pop()
                                setNewFragment(newCurrentTab)
                            } else {
                                currentTab = newCurrentTab
                                setNewFragment(newCurrentTab)
                            }
                        } ?: kotlin.run {

                            if (mStacksFragment[currentTab]?.size!! > 0) {
                                mStacksFragment[currentTab]?.pop()
                                setNewFragment(newCurrentTab)
                            } else {
                                currentTab = newCurrentTab
                                setNewFragment(newCurrentTab)
                            }
                        }

                    }
                } else {
                    currentTab = newCurrentTab
                    setNewFragment(newCurrentTab)
                }
            } else {

                if (mStacksFragment[currentTab]?.size!! > 0) {
                    mStacksFragment[currentTab]?.pop()
                }

                currentTab = newCurrentTab
                setNewFragment(newCurrentTab)
            }
        } else {
            finish()
        }
    }

    private fun getBackStackLastEntry(): BaseModuleFragment? = baseNavigationHelper?.getBaseFragments()?.last()

    /**
     * add a new root fragment into navigation
     */
    override fun addFragmentRootToNavigation(fragment: BaseModuleFragment) {

        if (currentTab != String.empty()) {

            val newCurrentTab = PREFIX_NEW_ROOT + currentTab + "/" + fragment.tagName()

            if (!mStacksFragment[currentTab]!!.contains(newCurrentTab)) {
                mStacksFragment[currentTab]!!.push(newCurrentTab)
            }

            if (fragment is BaseModuleFragment) {
                fragment.idNavigation = currentTab
                fragment.idPositionTab = currentPositionTab
                fragment.tagIntoNavigation = newCurrentTab

                configurationScreenWithOrientationDevice(fragment)
            }

            loadFragment(null, fragment)
        }
    }

    /**
     * add fragment into navigation
     */
    override fun pushFragment(fragment: BaseModuleFragment, forCurrentTab: String?) {

        val currentForPushFragment = forCurrentTab ?: currentTab

        if (currentForPushFragment != String.empty()) {
            val newTagFragment = currentForPushFragment + "/" + fragment.tagName()
            if (!mStacksFragment[currentForPushFragment]!!.contains(newTagFragment)) {
                mStacksFragment[currentForPushFragment]!!.push(newTagFragment)
            }

            fragment.idNavigation = currentForPushFragment
            fragment.idPositionTab = currentPositionTab
            fragment.tagIntoNavigation = newTagFragment

            loadFragment(fragment)
        }
    }

    /**
     * add root fragment into navigation
     */
    override fun pushFragmentRootDetail(fragment: BaseModuleFragment) {

        if (!mStacksFragment[currentTab]!!.contains(fragment.tagName())) {
            mStacksFragment[currentTab]!!.push(fragment.tagName())
        }

        fragment.idNavigation = currentTab
        fragment.idPositionTab = currentPositionTab

        loadFragment(fragment)
    }


    /**
     * remove all current stack until to root fragment in navigation
     */
    override fun removeAllCurrentStack(currentForRemoveAll: String?) {
        try {
            supportFragmentManager.checkSafeRemoveFragment {
                val removeCurrentTab = currentForRemoveAll ?: currentTab

                val listFragment = mStacksFragment[removeCurrentTab]?.map { it }

                listFragment?.forEachIndexed { index, fragmentTag ->
                    if (index != 0) {
                        val fragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
                        if (fragmentByTag != null && (fragmentByTag is BaseModuleFragment)) {
                            removeFragment(fragmentByTag, removeCurrentTab)
                        }
                    }
                }

                if (removeCurrentTab == currentTab) {
                    supportFragmentManager.executePendingTransactions()
                    configurationScreenWithOrientationDevice(
                            supportFragmentManager.findFragmentByTag(
                                    mStacksFragment[currentTab]?.lastElement()
                            ) as BaseModuleFragment?
                    )
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            /*
            Crashlytics.log(
                    Log.ERROR,
                    currentForRemoveAll,
                    "removeAllCurrentStack: " + e.printStackTrace().toString()
            )
            */
        }
    }

    /**
     * remove all current stack until to fragment selected in navigation
     */
    override fun removeAllStackFragmentsUpTo(
            fragment: BaseModuleFragment?,
            currentForRemoveUpTo: String?
    ) {
        try {
            supportFragmentManager.checkSafeRemoveFragment {

                val removeCurrentTab = currentForRemoveUpTo ?: currentTab

                if (fragment != null) {

                    val listFragment = mStacksFragment[removeCurrentTab]?.map { it }

                    listFragment?.reversed()?.forEach {
                        val fragmentTag = it
                        val fragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
                        if (fragmentByTag != null && fragmentByTag is BaseModuleFragment) {
                            if (fragmentByTag.tagIntoNavigation != fragment.tagIntoNavigation) {
                                removeFragment(fragmentByTag, removeCurrentTab)
                            }
                        }
                    }


                }
                if (removeCurrentTab == currentTab) {
                    supportFragmentManager.executePendingTransactions()
                    configurationScreenWithOrientationDevice(
                            supportFragmentManager.findFragmentByTag(
                                    mStacksFragment[currentTab]?.lastElement()
                            ) as BaseModuleFragment?
                    )
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            /*
            Crashlytics.log(
                    Log.ERROR,
                    fragment?.tagName(),
                    "removeAllStackFragmentsUpTo: " + e.printStackTrace().toString()
            )*/
        }
    }

    override fun addFragment(fragment: BaseModuleFragment, onBackTag: String?) {
    }

    override fun isShowingFragment(fragment: BaseModuleFragment): Boolean {

        val listFragmentInStack = mStacksFragment[currentTab]
        var isFragmentInStack = false

        listFragmentInStack?.forEach {
            val nameFragment = it.split("/").last()
            if (fragment.tagName() == nameFragment) {
                isFragmentInStack = true
            }
        }

        return isFragmentInStack
    }

    override fun isShowingFragment(fragmentTagName: String): Boolean {
        val listFragmentInStack = mStacksFragment[currentTab]
        var isFragmentInStack = false

        listFragmentInStack?.forEach {
            val nameFragment = it.split("/").last()
            if (fragmentTagName == nameFragment) {
                isFragmentInStack = true
            }
        }

        return isFragmentInStack
    }

    override fun removeFragment(fragment: BaseModuleFragment, currentForRemoveAll: String?) {
        try {
            supportFragmentManager.checkSafeRemoveFragment {
                val currentToRemove = currentForRemoveAll ?: currentTab
                val tagFragment = fragment.tagIntoNavigation

                mStacksFragment[currentToRemove]?.remove(tagFragment)

                supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                supportFragmentManager.executePendingTransactions()
            }
        } catch (e: IllegalStateException) {
            // Set more tags to get a further information about the exception
          //  Crashlytics.setString(BaseModuleBottomActivity::class.java.simpleName, "removeFragment")
           // Crashlytics.setString("EXCEPTION_CAUSE", e.cause.toString())
            //Crashlytics.setString("EXCEPTION_MESSAGE", e.message.toString())
            //Crashlytics.logException(e)
        }

    }

    override fun changeCurrentPosition(position: Int) {
        bottomNavigationView()?.let {
            it.selectedItemId = it.menu.getItem(position).itemId
        }

    }

    override fun getLastFragmentAdded(): Fragment? {
        val stack = mStacksFragment[currentTab]
        stack?.lastOrNull {
            if (it != currentTab) {
                return supportFragmentManager.findFragmentByTag(it)
            }
            return null
        }
        return null
    }

    override fun getCurrentTabForNavigation(): String {
        return currentTab
    }

    /**
     * get name the root fragment
     */
    private fun getRootNameFragment(): String? {
        val listFragment = mStacksFragment[currentTab]
        return listFragment?.lastOrNull { it.startsWith(PREFIX_NEW_ROOT, false) }
    }

    // load fragment
    private fun loadFragment(
            fragment: BaseModuleFragment?,
            rootFragment: BaseModuleFragment? = null
    ) {

        if (rootFragment != null) {
            addRootFragmentInStack(rootFragment)
        }

        if (fragment != null) {
            addDetailFragmentInStack(fragment)
        }
    }

    private fun addRootFragmentInStack(rootFragment: BaseModuleFragment) {
        supportFragmentManager.checkSafeCommit {

            val rootExistingFrag =
                    (supportFragmentManager.findFragmentByTag(rootFragment.tagIntoNavigation) as? BaseModuleFragment)

            if (rootExistingFrag == null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(
                        rootFrameContainer().id,
                        rootFragment,
                        rootFragment.tagIntoNavigation
                )
                transaction.commit()
                showViewInBackStack(rootFragment = rootFragment)

            } else {
                showViewInBackStack(rootFragment = rootFragment)
            }
        }
    }

    private fun addDetailFragmentInStack(fragment: BaseModuleFragment) {

        if (navigationActivityType() == NavigationWithType.NAVIGATION_TABLET_MULTIPANEL_WITH_BOTTOM) {

            val rootNameFragment = getRootNameFragment()
            val currentRootFragment = if (rootNameFragment != null) {
                supportFragmentManager.findFragmentByTag(rootNameFragment) as BaseModuleFragment?
            } else {
                supportFragmentManager.findFragmentByTag(currentTab) as BaseModuleFragment?
            }

            putDetailFragment(fragment, currentRootFragment)
        } else {
            putDetailFragment(fragment)
        }

    }

    private fun putDetailFragment(
            fragment: BaseModuleFragment,
            currentRootFragment: BaseModuleFragment? = null
    ) {
        supportFragmentManager.checkSafeCommit {
            val existingFrag =
                    (supportFragmentManager.findFragmentByTag(fragment.tagIntoNavigation) as? BaseModuleFragment)

            if (existingFrag == null) {
                supportFragmentManager.beginTransaction()
//                    .setCustomAnimations(
//                        if (mStacksFragment[currentTab]?.size == 1) R.anim.fade_in_fragment else R.anim.slide_in_right,
//                        0
//                    )
                        .add(detailFrameContainer().id, fragment, fragment.tagIntoNavigation)
                        .commit()
                if (currentTab == fragment.idNavigation) {
                    showViewInBackStack(
                            fragment = fragment,
                            rootFragment = currentRootFragment
                    )
                } else {
                    supportFragmentManager.beginTransaction()
                            .hide(fragment)
                            .commit()
                }
            } else {
                showViewInBackStack(
                        fragment = fragment,
                        rootFragment = currentRootFragment
                )
            }
        }
    }

    private fun showViewInBackStack(
            fragment: BaseModuleFragment? = null,
            rootFragment: BaseModuleFragment? = null
    ) {

        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
        val fragList = supportFragmentManager.fragments

        for (frag in fragList) {
            if (Objects.requireNonNull(frag.tag).equals(
                            fragment?.tagIntoNavigation
                                    ?: ""
                    ) || Objects.requireNonNull(
                            frag.tag
                    ).equals(
                            rootFragment?.tagIntoNavigation ?: ""
                    )
            ) {
                transaction.show(frag)
            } else {
                if (!Objects.requireNonNull(
                                frag.tag
                        ).equals(
                                rootFragment?.tagIntoNavigation ?: ""
                        )
                ) {
                    transaction.hide(frag)
                }
            }
        }

        transaction.commit()
    }

    override fun showOrUpdateBadgeBottomItem(positionBottomItem: Int, count: Int) {

        val bottomNavigationMenuView =
                bottomNavigationView()?.getChildAt(0) as BottomNavigationMenuView
        val bottomItemView =
                bottomNavigationMenuView.getChildAt(positionBottomItem) as BottomNavigationItemView
        bottomItemView.showBadge(count)

    }

    override fun removeBadgeBottomItem(positionBottomItem: Int) {
        val bottomNavigationMenuView =
                bottomNavigationView()?.getChildAt(0) as BottomNavigationMenuView
        val bottomItemView =
                bottomNavigationMenuView.getChildAt(positionBottomItem) as BottomNavigationItemView
        bottomItemView.removeBadge()

    }

    fun resetNavigationTab() {

        mStacksFragment.forEach { (tagName, _) ->
            if (tagName != currentTab) {
                removeAllCurrentStack(tagName)
            }
        }
    }

    /**
     *This method can be used to know if the fragment it's full screen or not
     */
    override fun checkIfFullScreen(currentFragment: BaseModuleFragment) {
        configurationScreenWithOrientationDevice(currentFragment)
    }

    override fun getBottomNavigationView(): BottomNavigationView? {
        return bottomNavigationView()
    }
}