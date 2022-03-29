package au.com.carsales.basemodule.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun androidx.fragment.app.FragmentManager.findModuleFragmentByTag(tagFragment: String): androidx.fragment.app.Fragment? {
    return this.findFragmentByTag(tagFragment)
}

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
        beginTransaction().func().commit()

inline fun androidx.fragment.app.FragmentManager.inTransactionCommitNow(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
        beginTransaction().func().commitNow()