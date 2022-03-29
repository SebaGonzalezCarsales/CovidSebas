package au.com.carsales.basemodule.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.visible
import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.util.dialog.error.ErrorDialogFragment
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import au.com.carsales.basemodule.viewcomponent.errorView.GlobalErrorClickHandler
import com.android.volley.toolbox.ImageLoader
import kotlinx.android.synthetic.main.notification_badge.view.*

fun getImageLoader(): ImageLoader? {
    return getAppContext().volleyService.imageLoader
}


fun BottomNavigationItemView.showBadge(count: Int) {

    if (this.childCount <= 2) {
        val badge = LayoutInflater.from(context)
                .inflate(R.layout.notification_badge, this, true)
        badge.notificationsBadge?.run {
            text = "$count"
            if (count > 0) {
                visibleWithBounceAnimation()
            } else {
                removeBadge()
            }
        }
    } else {
        if (count > 0) {
            val textView = this.findViewById<TextView>(R.id.notificationsBadge)
            if (textView != null) {
                textView.text = "$count"
                textView.updateWithBounceAnimation()
            } else {
                removeBadge()
            }
        } else {
            removeBadge()
        }
    }

}

fun View.visibleWithBounceAnimation() {

    val animBounce = AnimationUtils.loadAnimation(
            this.context,
            R.anim.bounce_in
    )

    animBounce.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {}
        override fun onAnimationStart(p0: Animation?) {
            visible()
        }

    })

    startAnimation(animBounce)
}

fun View.updateWithBounceAnimation() {

    val animBounce = AnimationUtils.loadAnimation(
            this.context,
            R.anim.bounce_update
    )

    animBounce.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {}
        override fun onAnimationStart(p0: Animation?) {
            visible()
        }

    })

    startAnimation(animBounce)
}


fun View.goneWithBounceAnimation() {

    val animBounce = AnimationUtils.loadAnimation(
            this.context,
            R.anim.bounce_out
    )

    animBounce.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {

        }

        override fun onAnimationStart(p0: Animation?) {}

    })

    startAnimation(animBounce)
}


fun BottomNavigationItemView.removeBadge() {
    if (this.childCount > 2) {
        this.removeViewAt(2)
    }

}

fun Activity.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri

    startActivity(intent)
}


fun FragmentManager.showErrorDialog(message: String?) {
    checkSafeCommit {
        val errorDialogFragment = ErrorDialogFragment()
        errorDialogFragment.show(this, ErrorDialogFragment::class.java.simpleName)
    }
}

fun FragmentManager.showErrorDialog(apiErrorViewData: APIErrorViewData?, errorDialogClickHandler: GlobalErrorClickHandler? = null,
                                    canCancel: Boolean? = true) {
    checkSafeCommit {
        val errorDialogFragment = ErrorDialogFragment()
        val arg = Bundle()
        arg.putSerializable(ErrorDialogFragment.ARG_KEY_DATA, apiErrorViewData)
        arg.putSerializable(ErrorDialogFragment.ARG_KEY_CANCELABLE, canCancel)
        errorDialogFragment.arguments = arg
        errorDialogFragment.errorDialogClickHandler = errorDialogClickHandler
        errorDialogFragment.show(this, ErrorDialogFragment::class.java.simpleName)
    }
}


fun <T> notNull(vararg elements: T): Boolean {
    elements.forEach {
        if (it == null) {
            return false
        }
    }
    return true
}