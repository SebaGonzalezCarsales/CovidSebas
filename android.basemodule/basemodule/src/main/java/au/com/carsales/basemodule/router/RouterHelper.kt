package au.com.carsales.basemodule.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class RouterHelper {
  val EXTRA_KEY_ROUTER_RESULT = "router_result"
  var targetFragment: androidx.fragment.app.Fragment? = null
  var requestCode: Int
  var backstackName: String?


  constructor(backStackName: String, targetFragment: androidx.fragment.app.Fragment?, requestCode: Int) {
    this.backstackName = backStackName
    this.requestCode = requestCode
    this.targetFragment = targetFragment
  }

  fun getBackStackName() = backstackName

  fun finish(fragment: androidx.fragment.app.Fragment) {
    fragment.fragmentManager?.popBackStack(getBackStackName(), androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
  }


  fun finish(fragment: androidx.fragment.app.Fragment, result: Bundle) {
    val intent = Intent()
    intent.putExtra(EXTRA_KEY_ROUTER_RESULT, result)
    targetFragment?.onActivityResult(requestCode, Activity.RESULT_OK, intent)
    fragment.fragmentManager?.popBackStack(getBackStackName(), androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
  }

  fun finish(activity: Activity) {
    activity.finish()
  }

  fun finish(activity: Activity, result: Bundle) {
    val intent = Intent()
    intent.putExtra(EXTRA_KEY_ROUTER_RESULT, result)
    activity.setResult(Activity.RESULT_OK, intent)
    activity.finish()
  }
}