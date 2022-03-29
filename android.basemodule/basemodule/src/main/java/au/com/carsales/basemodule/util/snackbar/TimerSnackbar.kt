package au.com.carsales.basemodule.util.snackbar

import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.widget.circleProgressBar.CircleProgressBar
import com.google.android.material.snackbar.BaseTransientBottomBar


@Suppress("unused, SetTextI18n")
class TimerSnackbar private constructor(
        parent: ViewGroup,
        content: View,
        contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<TimerSnackbar?>(parent, content, contentViewCallback) {

    enum class DismissEventType(val eventType: String) {
        EVENT_ACTION("EventAction"),
        EVENT_CONSECUTIVE("EventConsecutive"),
        EVENT_MANUAL("EventManual"),
        EVENT_SWIPE("EventSwipe"),
        EVENT_TIMEOUT("EventTimeout")
    }

    companion object {
        private lateinit var timerSnackbar: TimerSnackbar
        private lateinit var timerSnackbarView: View
        private var contentMessage: String? = null
        private var timerSuffix: String? = null
        private var countDownTimer: CountDownTimer? = null
        private var progressTimer: Int = -1

        fun make(parent: ViewGroup, message: String? = null): TimerSnackbar {
            val inflater = LayoutInflater.from(parent.context)
            timerSnackbarView = inflater.inflate(R.layout.timer_snackbar_layout, parent, false)
            val callback = ContentViewCallback(timerSnackbarView)
            val tvMessage = timerSnackbarView.findViewById<TextView>(R.id.textViewMessage)

            timerSnackbar = TimerSnackbar(parent, timerSnackbarView, callback)

            timerSnackbar.duration = LENGTH_INDEFINITE
            timerSnackbar.view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.primaryColor))

            message?.let {
                contentMessage = it
                tvMessage.text = it
            }

            return timerSnackbar
        }
    }

    class ContentViewCallback(private val view: View) : BaseTransientBottomBar.ContentViewCallback {
        override fun animateContentIn(delay: Int, duration: Int) {
            ViewCompat.setScaleY(timerSnackbarView, 0f)
            ViewCompat.animate(timerSnackbarView)
                    .scaleY(1f)
                    .setDuration(duration.toLong()).startDelay = delay.toLong()
        }

        override fun animateContentOut(delay: Int, duration: Int) {
            ViewCompat.setScaleY(timerSnackbarView, 1f)
            ViewCompat.animate(timerSnackbarView)
                    .scaleY(0f)
                    .setDuration(duration.toLong()).startDelay = delay.toLong()
        }
    }

    fun setMessage(message: String): TimerSnackbar {
        val tvMessage = timerSnackbarView.findViewById<TextView>(R.id.textViewMessage)

        tvMessage.apply {
            contentMessage = message
            text = message
        }

        return timerSnackbar
    }

    fun setTimerSuffix(suffix: String): TimerSnackbar {
        timerSuffix = suffix

        return timerSnackbar
    }

    fun setTimer(timerInSeconds: Int): TimerSnackbar {
        progressTimer = timerInSeconds
        val pbTimer = timerSnackbarView.findViewById<CircleProgressBar>(R.id.circleProgressBar)
        val tvProgressText = timerSnackbarView.findViewById<TextView>(R.id.textViewTimer)

        pbTimer.setMax(timerInSeconds)
        pbTimer.setProgress(timerInSeconds.toFloat())

        countDownTimer = object : CountDownTimer(timerInSeconds * 1000L, 1000) {
            override fun onFinish() {
                pbTimer.setProgressWithAnimation(0.toFloat())
                tvProgressText.text = "0"

                if(!timerSuffix.isNullOrEmpty()) {
                    tvProgressText.text = tvProgressText.text.toString().plus(timerSuffix)
                }

                Handler().postDelayed({
                    timerSnackbar.dismiss()
                }, 500)
            }

            override fun onTick(millisUntilFinished: Long) {
                pbTimer.setProgressWithAnimation(progressTimer.toFloat())
                tvProgressText.text = progressTimer.toString()

                if(!timerSuffix.isNullOrEmpty()) {
                    tvProgressText.text = tvProgressText.text.toString().plus(timerSuffix)
                }

                progressTimer--
            }
        }

        countDownTimer?.start()

        return timerSnackbar
    }

    fun setAction(actionText: String, actionListener: ((TimerSnackbar) -> Unit)?, dismissAfterAction: Boolean = true): TimerSnackbar {
        val btnAction = timerSnackbarView.findViewById<Button>(R.id.buttonAction)

        btnAction.text = actionText
        btnAction.visibility = View.VISIBLE

        btnAction.setOnClickListener {
            countDownTimer?.cancel()
            actionListener?.invoke(timerSnackbar)

            if(dismissAfterAction) {
                timerSnackbar.dismiss()
            }
        }

        return timerSnackbar
    }

    fun setDismissListener(dismissListener: ((DismissEventType) -> Unit)?): TimerSnackbar {
        timerSnackbar.addCallback(object : BaseCallback<TimerSnackbar?>() {
            override fun onDismissed(transientBottomBar: TimerSnackbar?, event: Int) {
                countDownTimer?.cancel()

                when(event) {
                    DISMISS_EVENT_ACTION -> { dismissListener?.invoke(DismissEventType.EVENT_ACTION) }
                    DISMISS_EVENT_CONSECUTIVE -> { dismissListener?.invoke(DismissEventType.EVENT_CONSECUTIVE) }
                    DISMISS_EVENT_MANUAL -> { dismissListener?.invoke(DismissEventType.EVENT_MANUAL) }
                    DISMISS_EVENT_SWIPE -> { dismissListener?.invoke(DismissEventType.EVENT_SWIPE) }
                    DISMISS_EVENT_TIMEOUT -> { dismissListener?.invoke(DismissEventType.EVENT_TIMEOUT) }
                }

                super.onDismissed(timerSnackbar, event)
            }
        })

        return timerSnackbar
    }

    override fun show() {
        checkIfTrue(contentMessage == null) { "A message is required to show the Timer Snackbar" }
        checkIfTrue(progressTimer < 0) { "A timer value must be set" }
        checkIfTrue(progressTimer == 0) { "Timer value must be greater than 0 seconds" }
        checkIfTrue(progressTimer > 99) { "Timer value must be less than 99 seconds" }

        super.show()
    }
}

/**
 * Throws an [IllegalArgumentException] with the result of calling [lazyMessage] if the [condition] is true.
 *
 */
private fun checkIfTrue(condition: Boolean, lazyMessage: () -> Any) {
    if (condition) {
        val message = lazyMessage()
        throw IllegalArgumentException(message.toString())
    }
}