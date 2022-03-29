package au.com.carsales.basemodule.extension

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import androidx.core.content.res.ResourcesCompat
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.util.dpToPx

fun Button.setStyleToolbar(context: Activity, menuItem: MenuItem) {

    val titleFont = ResourcesCompat.getFont(context, R.font.opensans_regular)
    this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f)
    this.setTextColor(resources.getColor(R.color.primaryColor))
    this.setAllCaps(false)
//    this.setBackgroundColor(resources.getColor(R.color.white))
    setSelectableBackgroundView(context, this)
    this.text = menuItem.title.toString() + "  "
    this.typeface = titleFont

}

private fun setSelectableBackgroundView(activity: Activity, view: View) {
    // Create an array of the attributes we want to resolve
    // using values from a theme
    // android.R.attr.selectableItemBackground requires API LEVEL 11
    val attrs = intArrayOf(android.R.attr.selectableItemBackgroundBorderless/* index 0 */)

    // Obtain the styled attributes. 'themedContext' is a context with a
    // theme, typically the current Activity (i.e. 'this')
    val ta = activity.obtainStyledAttributes(attrs)

    // Now get the value of the 'listItemBackground' attribute that was
    // set in the theme used in 'themedContext'. The parameter is the index
    // of the attribute in the 'attrs' array. The returned Drawable
    // is what you are after
    val drawableFromTheme = ta.getDrawable(0 /* index */)

    // Finally free resources used by TypedArray
    ta.recycle()

    // setBackground(Drawable) requires API LEVEL 16,
    // otherwise you have to use deprecated setBackgroundDrawable(Drawable) method.
    view.background = drawableFromTheme
    // imageButton.setBackgroundDrawable(drawableFromTheme);
}

fun Button.setStyle(backgroundColor: Int?, borderColor: Int?, textColor: Int?) {
    setStyle(backgroundColor, borderColor, textColor, null)
}

fun Button.setStyle(backgroundColor: Int?, borderColor: Int?, textColor: Int?, cornerRadius: Float?) {
    var backgroundDrawable: Drawable? = null
    if (background is LayerDrawable) {
        if (cornerRadius != null) {
            val mask =
                    if ((background as LayerDrawable).numberOfLayers > 0)
                        (background as LayerDrawable).getDrawable(0)
                    else null
            if (mask != null && mask is GradientDrawable) {
                mask.cornerRadius = cornerRadius
            }
        }

        backgroundDrawable =
                if ((background as LayerDrawable).numberOfLayers > 1)
                    (background as LayerDrawable).getDrawable(1)
                else null
    }
    if (backgroundDrawable != null && backgroundDrawable is StateListDrawable) {

        val dcs = backgroundDrawable.constantState as DrawableContainer.DrawableContainerState
        val children = dcs.children

        if (children.isNotEmpty() && children[0] is GradientDrawable) {
            val enableDrawable = children[0] as GradientDrawable

            //background color
            enableDrawable.setColor(backgroundColor ?: Color.TRANSPARENT)
            if (cornerRadius != null) {
                enableDrawable.cornerRadius = cornerRadius
            }
            //border color
            val strokeWidth = dpToPx(1)
            enableDrawable.setStroke(strokeWidth, borderColor ?: Color.TRANSPARENT)
        }
    }

    //text color
    if (textColor != null) {
        setTextColor(textColor)

    }
}