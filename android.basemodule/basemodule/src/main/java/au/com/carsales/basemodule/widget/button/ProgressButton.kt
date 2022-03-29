package au.com.carsales.basemodule.widget.button

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.Button
import au.com.carsales.basemodule.util.dpToPx

class ProgressButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.buttonStyle
) : Button(context, attrs, defStyleAttr) {
    private var currentText: String? = null
    private var isLoading: Boolean = false

    /**
     * Animation tools
     */

    private var animatedRadius: Float = 0.toFloat()
    private var isFirstLaunch = true
    private var startValueAnimator: ValueAnimator? = null
    private var endValueAnimator: ValueAnimator? = null
    /**
     * Dots amount
     */
    private var dotAmount: Int = 3
    /**
     * Circle coordinates
     */
    private var xCoordinate: Float = 0.toFloat()
    private var dotPosition: Int = 0
    /*
     * Circle size
     */
    private var dotRadius: Float = 0.toFloat()
    private var bounceDotRadius: Float = 0.toFloat()
    private var spacing: Float = 0.toFloat()
    private var circlesWidth: Int = 0

    /**
     * Drawing tools
     */
    private var primaryPaint: Paint? = null
    private var startPaint: Paint? = null
    private var endPaint: Paint? = null


    /**
     * Colors
     * Default is White
     */
    private var startColor: Int = Color.WHITE
    private var endColor: Int = Color.WHITE

    companion object {
        const val ANIMATION_TIME: Long = 300L
        //Unit dp
        const val DOT_RADIUS = 6
        const val SPACING = 7
    }


    init {
        dotRadius = dpToPx(DOT_RADIUS).toFloat()
        spacing = dpToPx(SPACING).toFloat()
        bounceDotRadius = dotRadius + dotRadius / 3
        circlesWidth = (dotAmount * (dotRadius * 2) + spacing * (dotAmount - 1) + (bounceDotRadius - dotRadius) *
                2).toInt()

        primaryPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        primaryPaint!!.color = startColor
        primaryPaint!!.strokeJoin = Paint.Join.ROUND
        primaryPaint!!.strokeCap = Paint.Cap.ROUND
        primaryPaint!!.strokeWidth = 5f

        startPaint = Paint(primaryPaint)
        endPaint = Paint(primaryPaint)

        startValueAnimator = ValueAnimator.ofInt(startColor, endColor)
        startValueAnimator!!.duration = ANIMATION_TIME
        startValueAnimator!!.setEvaluator(ArgbEvaluator())
        startValueAnimator!!.addUpdateListener { animation -> startPaint!!.color = animation.animatedValue as Int }

        endValueAnimator = ValueAnimator.ofInt(endColor, startColor)
        endValueAnimator!!.duration = ANIMATION_TIME
        endValueAnimator!!.setEvaluator(ArgbEvaluator())
        endValueAnimator!!.addUpdateListener { animation -> endPaint!!.color = animation.animatedValue as Int }

    }

    override fun onDetachedFromWindow() {
        hideIndicator()
        super.onDetachedFromWindow()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        if (canvas != null && isLoading) {
            drawCirclesLeftToRight(canvas, animatedRadius)
        }

    }

    fun indicatorColor(color: Int): ProgressButton {
        startColor = color
        endColor = color

        primaryPaint?.color = startColor
        startPaint = Paint(primaryPaint)
        endPaint = Paint(primaryPaint)

        startValueAnimator?.setIntValues(startColor, endColor)
        endValueAnimator?.setIntValues(startColor, endColor)

        return this
    }

    fun indicatorColorRes(@ColorRes colorRes: Int): ProgressButton {
        val color = ResourcesCompat.getColor(resources, colorRes, null)
        indicatorColor(color)
        return this
    }

    fun showIndicator() {
        isLoading = true
        currentText = text.toString()
        //clear text on button
        text = ""

        xCoordinate = ((measuredWidth - circlesWidth) / 2).toFloat() + bounceDotRadius

        startAnimation()

    }

    fun hideIndicator() {
        //revert button text
        if (currentText != null) {
            text = currentText
        }
        stopAnimation()
        isLoading = false

    }

    fun isLoading(): Boolean = isLoading

    private fun startAnimation() {
        val bounceAnimation = BounceAnimation()
        bounceAnimation.duration = ANIMATION_TIME
        bounceAnimation.repeatCount = Animation.INFINITE
        bounceAnimation.interpolator = LinearInterpolator()
        bounceAnimation.setAnimationListener(object : AnimationListener() {
            override fun onAnimationRepeat(animation: Animation) {
                dotPosition++
                if (dotPosition == dotAmount) {
                    dotPosition = 0
                }

                startValueAnimator!!.start()

                if (!isFirstLaunch) {
                    endValueAnimator!!.start()
                }

                isFirstLaunch = false
            }
        })
        startAnimation(bounceAnimation)
    }

    private fun drawCirclesLeftToRight(canvas: Canvas, radius: Float) {
        var step = 0f
        for (i in 0 until dotAmount) {
            drawCircles(canvas, i, step, radius)
            step += dotRadius * 2 + spacing
        }
    }

    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            animatedRadius = (bounceDotRadius - dotRadius) * interpolatedTime
            invalidate()
        }
    }

    private fun drawCircles(canvas: Canvas, i: Int, step: Float, radius: Float) {
        if (dotPosition == i) {
            drawCircleUp(canvas, step, radius)
        } else {
            if (i == dotAmount - 1 && dotPosition == 0 && !isFirstLaunch || dotPosition - 1 == i) {
                drawCircleDown(canvas, step, radius)
            } else {
                drawCircle(canvas, step)
            }
        }
    }

    /**
     * Circle radius is grow
     */
    private fun drawCircleUp(canvas: Canvas, step: Float, radius: Float) {
        canvas.drawCircle(
                xCoordinate + step,
                (measuredHeight / 2).toFloat(),
                dotRadius + radius,
                startPaint!!
        )
    }

    private fun drawCircle(canvas: Canvas, step: Float) {
        canvas.drawCircle(
                xCoordinate + step,
                (measuredHeight / 2).toFloat(),
                dotRadius,
                primaryPaint!!
        )
    }

    /**
     * Circle radius is decrease
     */
    private fun drawCircleDown(canvas: Canvas, step: Float, radius: Float) {
        canvas.drawCircle(
                xCoordinate + step,
                (measuredHeight / 2).toFloat(),
                bounceDotRadius - radius,
                endPaint!!
        )
    }


    private fun stopAnimation() {
        this.clearAnimation()
        postInvalidate()
    }

    internal abstract inner class AnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            // stub
        }

        override fun onAnimationEnd(animation: Animation) {
            // stub
        }
    }
}