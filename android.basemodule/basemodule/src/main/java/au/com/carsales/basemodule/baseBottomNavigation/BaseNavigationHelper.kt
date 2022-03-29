package au.com.carsales.basemodule.baseBottomNavigation

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.tagName
import au.com.carsales.basemodule.util.checkSafeCommit
import java.util.*
import java.util.logging.Handler

class BaseNavigationHelper(
        private val fragmentManager: FragmentManager,
        var enterAnim: Int = R.anim.fade_in_fragment,
        var exitAnim: Int = R.anim.fade_out_fragment,
        var popEnterAnim: Int = R.anim.fade_in_fragment,
        var popExitAnim: Int = R.anim.fade_out_fragment
) {

    private val durationAnimationFragment = 200

    fun replaceRootFragment(
            fragment: Fragment, containerId: Int, nameBackStack: String?, isAnimation: Boolean = true,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            val transition = fragmentManager.beginTransaction()

            if (isAnimation) {
                transition.setCustomAnimations(enter, exit, popEnter, popExit)
            }

            try {
                transition.replace(containerId, fragment, fragment::class.java.simpleName)
                transition.addToBackStack(nameBackStack)
                transition.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                /*
                Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()
/*
                Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun changeRootFragment(
            fragment: Fragment, containerId: Int, nameBackStack: String, isAnimation: Boolean = true,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            val transition = fragmentManager.beginTransaction()

            if (isAnimation) {
                transition.setCustomAnimations(enter, exit, popEnter, popExit)
            }

            try {
                transition.replace(containerId, fragment, nameBackStack)
                transition.addToBackStack(nameBackStack)
                transition.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
/*
                Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()
/*
                Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun replaceFragment(
            fragment: Fragment, containerId: Int, nameTag: String?, isAnimation: Boolean = true,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            if (fragmentManager.findFragmentByTag(nameTag) == null) {
                val transition = fragmentManager.beginTransaction()
                if (isAnimation) {
                    transition.setCustomAnimations(enter, exit, popEnter, popExit)
                }

                try {
                    transition.replace(containerId, fragment, nameTag)
                    transition.addToBackStack(nameTag)
                    transition.commit()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                    /*
                    Crashlytics.log(
                            Log.ERROR, "fragment: " + fragment.tag
                            , "BaseNavigationHelper - replaceFragment "
                    )*/
                } catch (e: NullPointerException) {
                    e.printStackTrace()
/*
                    Crashlytics.log(
                            Log.ERROR, "fragment: " + fragment.tag
                            , "BaseNavigationHelper - replaceRootFragment "
                    )*/
                }
            }
        }
    }

    fun replaceRootDetailFragment(
            fragment: Fragment, containerId: Int, nameTag: String, isAnimation: Boolean = true,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            if (fragmentManager.findFragmentByTag(fragment.tagName()) == null) {
                val transition = fragmentManager.beginTransaction()

                if (isAnimation) {
                    transition.setCustomAnimations(enter, exit, popEnter, popExit)
                }

                try {
                    transition.replace(containerId, fragment, nameTag)
                    transition.addToBackStack(nameTag)
                    transition.commit()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                    /*Crashlytics.log(
                            Log.ERROR, "fragment: " + fragment.tag
                            , "BaseNavigationHelper - replaceRootDetailFragment "
                    )*/
                } catch (e: NullPointerException) {
                    e.printStackTrace()

                    /*Crashlytics.log(
                            Log.ERROR, "fragment: " + fragment.tag
                            , "BaseNavigationHelper - replaceRootFragment "
                    )*/
                }
            }
        }
    }

    fun replaceMakeRootFragment(
            fragment: Fragment, containerId: Int, nameBackStack: String,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            try {
                val transition = fragmentManager.beginTransaction()
                transition.setCustomAnimations(enter, exit, popEnter, popExit)
                transition.replace(containerId, fragment, fragment.tagName())
                transition.addToBackStack(nameBackStack)
                transition.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceMakeRootFragment "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()

                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun changeTabFragmentWithTagFragment(
            fragment: Fragment?, containerId: Int, nameBackStack: String?, isAnimation: Boolean = true,
            enter: Int = enterAnim, exit: Int = exitAnim, popEnter: Int = popEnterAnim, popExit: Int = popExitAnim
    ) {
        fragmentManager.checkSafeCommit {
            try {
                val transitionManager = fragmentManager.beginTransaction()

                if (isAnimation) {
                    transitionManager.setCustomAnimations(enter, exit, popEnter, popExit)
                }
                transitionManager.replace(containerId, fragment!!)
                transitionManager.addToBackStack(nameBackStack)
                transitionManager.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment?.tag + "nameBackStack: " + nameBackStack
                        , "BaseNavigationHelper - changeTabFragmentWithTagFragment "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()

                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment?.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun removeFragment(
            fragment: Fragment,
            enter: Int = enterAnim,
            exit: Int = exitAnim,
            popEnter: Int = popEnterAnim,
            popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            try {
                val transitionManager = fragmentManager.beginTransaction()
                transitionManager.setCustomAnimations(enter, exit, popEnter, popExit)
                transitionManager.remove(fragment)
                transitionManager.addToBackStack(null)
                transitionManager.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - removeFragment "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()

                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun replaceFragmentWithoutTag(fragment: Fragment,
                                  containerId: Int,
                                  nameBackStack: String,
                                  isAnimation: Boolean = true,
                                  enter: Int = enterAnim,
                                  exit: Int = exitAnim,
                                  popEnter: Int = popEnterAnim,
                                  popExit: Int = popExitAnim
    ) {

        fragmentManager.checkSafeCommit {
            val transition = fragmentManager.beginTransaction()
            if (isAnimation) {
                transition.setCustomAnimations(enter, exit, popEnter, popExit)
            }

            try {
                transition.replace(containerId, fragment)
                transition.addToBackStack(nameBackStack)
                transition.commit()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceFragmentWithoutTag "
                )*/
            } catch (e: NullPointerException) {
                e.printStackTrace()

                /*Crashlytics.log(
                        Log.ERROR, "fragment: " + fragment.tag
                        , "BaseNavigationHelper - replaceRootFragment "
                )*/
            }
        }
    }

    fun isFragmentShowing(fragment: Fragment): Boolean {

        val fragmentsList = fragmentManager.fragments
        fragmentsList.forEach {
            if (it == fragment) {
                return true
            }
        }
        return false
    }

    fun getBaseFragments(): List<BaseModuleFragment> {
        val fragmentsList = fragmentManager.fragments
        //Filter the classes that extends of BaseFragment
        val fragmentBaseList = mutableListOf<BaseModuleFragment>()

        fragmentsList.forEach {
            if (it is BaseModuleFragment) {
                fragmentBaseList.add(it)
            }
        }

        return fragmentBaseList.toList()
    }


    fun showContainerList(containerListFrame: FrameLayout, containerDetailFrame: FrameLayout) {

        val paramsContainerList = containerListFrame.layoutParams as LinearLayout.LayoutParams
        val paramsContainer = if (containerDetailFrame.layoutParams is FrameLayout.LayoutParams) {
            containerDetailFrame.layoutParams as FrameLayout.LayoutParams
        } else {
            containerDetailFrame.layoutParams as LinearLayout.LayoutParams
        }

        val slideAnimator = ValueAnimator
                .ofFloat(paramsContainerList.weight, 40f)
                .setDuration(durationAnimationFragment.toLong())

        slideAnimator.addUpdateListener { animation ->
            // get the value the interpolator is at
            val value = animation.animatedValue as Float
            // I'm going to set the layout's height 1:1 to the tick
            paramsContainerList.weight = value
            containerListFrame.layoutParams = paramsContainerList

            // force all layouts to see which ones are affected by
            // this layouts height change
            containerListFrame.requestLayout()
        }

        // create a new animationset
        val set = AnimatorSet()

        // since this is the only animation we are going to run we just use
        // play
        set.play(slideAnimator)

        // this is how you set the parabola which controls acceleration
        set.interpolator = AccelerateDecelerateInterpolator()

        set.start()

//        slideAnimator.start()

        try {
            val slideAnimator2 = ValueAnimator
                    .ofFloat((paramsContainer as LinearLayout.LayoutParams).weight, 60f)
                    .setDuration(durationAnimationFragment.toLong())

            slideAnimator2.addUpdateListener { animation ->
                // get the value the interpolator is at
                val value = animation.animatedValue as Float
                // I'm going to set the layout's height 1:1 to the tick
                paramsContainer.weight = value
                containerDetailFrame.layoutParams = paramsContainer

                // force all layouts to see which ones are affected by
                // this layouts height change
                containerDetailFrame.requestLayout()
            }

            val set2 = AnimatorSet()

            // since this is the only animation we are going to run we just use
            // play
            set2.play(slideAnimator2)

            // this is how you set the parabola which controls acceleration
            set2.interpolator = AccelerateDecelerateInterpolator()

            // start the animation
            set2.start()
        } catch (e: Exception) {

        }
    }

    fun hideContainerDetails(containerListFrame: FrameLayout, containerDetailFrame: FrameLayout) {

        val paramsContainerList = containerListFrame.layoutParams as LinearLayout.LayoutParams
        val paramsContainer = if (containerDetailFrame.layoutParams is FrameLayout.LayoutParams) {
            containerDetailFrame.layoutParams as FrameLayout.LayoutParams
        } else {
            containerDetailFrame.layoutParams as LinearLayout.LayoutParams
        }


        val slideAnimator = ValueAnimator
                .ofFloat(paramsContainerList.weight, 100f)
                .setDuration(durationAnimationFragment.toLong())

        slideAnimator.addUpdateListener { animation ->
            // get the value the interpolator is at
            val value = animation.animatedValue as Float
            // I'm going to set the layout's height 1:1 to the tick
            paramsContainerList.weight = value
            containerListFrame.layoutParams = paramsContainerList

            // force all layouts to see which ones are affected by
            // this layouts height change
            containerListFrame.requestLayout()
        }

        // create a new animationset
        val set = AnimatorSet()
        // since this is the only animation we are going to run we just use
        // play
        set.play(slideAnimator)
        // this is how you set the parabola which controls acceleration
        set.interpolator = AccelerateDecelerateInterpolator()

        set.start();
//        slideAnimator.start()

        try {
            val slideAnimator2 = ValueAnimator
                    .ofFloat((paramsContainer as LinearLayout.LayoutParams).weight, 0f)
                    .setDuration(durationAnimationFragment.toLong())

            slideAnimator2.addUpdateListener { animation ->
                // get the value the interpolator is at
                val value = animation.animatedValue as Float
                // I'm going to set the layout's height 1:1 to the tick
                paramsContainer.weight = value
                containerDetailFrame.layoutParams = paramsContainer

                // force all layouts to see which ones are affected by
                // this layouts height change
                containerDetailFrame.requestLayout()
            }

            val set2 = AnimatorSet()
// since this is the only animation we are going to run we just use
// play
            set2.play(slideAnimator2)
// this is how you set the parabola which controls acceleration
            set2.interpolator = AccelerateDecelerateInterpolator()

            // start the animation
            set2.start()
        } catch (e: Exception) {

        }
    }

    fun addFragment(fragment: BaseModuleFragment, containerId: Int, tag: String) {
        fragmentManager.checkSafeCommit {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(containerId, fragment, tag)
            transaction.commit()
            hideAndShowFragments(tag)
        }
    }

    fun hideAndShowFragments(tag: String) {

        val transaction = fragmentManager.beginTransaction()

        val fragList = fragmentManager.fragments
        for (frag in fragList) {
            if (Objects.requireNonNull(frag.tag).equals(tag
                            ?: "") || Objects.requireNonNull(
                            frag.tag
                    ).equals(
                            tag ?: ""
                    )
            ) {
                transaction.show(frag)
            } else {
                if (!Objects.requireNonNull(
                                frag.tag
                        ).equals(
                                tag ?: ""
                        )
                ) {
                    transaction.hide(frag)
                }
            }
        }
        transaction.commit()
    }
}