package au.com.carsales.basemodule.extension

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build


fun String.nameApp(activity: Context): String {
    val packageManager = activity.packageManager
    var applicationInfo: ApplicationInfo? = null
    try {
        applicationInfo = packageManager.getApplicationInfo(activity.applicationInfo.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
    }

    return if (applicationInfo != null) activity.packageManager.getApplicationLabel(applicationInfo) as String else "Unknown"
}

fun String.versionApp(context: Context): String{
    return context.packageManager.getPackageInfo(context.packageName, 0).versionName
}

fun String.deviceApp(): String{

    val manufature = Build.MANUFACTURER
    val model = Build.MODEL

    return if (model.startsWith(manufature)) {
        capitalize(manufature)
    }else{
        capitalize(manufature) + " "+ model
    }
}

fun String.androidVersionApp():String{

    val release = Build.VERSION.RELEASE
    val sdkVersion = Build.VERSION.SDK_INT
    return "Android : $sdkVersion ($release)"
}


private fun capitalize(s: String?): String {
    if (s == null || s.isEmpty()) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        Character.toUpperCase(first) + s.substring(1)
    }
}