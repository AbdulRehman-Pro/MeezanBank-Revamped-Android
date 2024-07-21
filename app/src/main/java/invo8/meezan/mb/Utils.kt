package invo8.meezan.mb

import android.Manifest
import android.app.Activity
import android.view.View
import android.view.Window
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import invo8.meezan.mb.beforeAuth.fragments.qiblaFragment.SwipeButtonInterface
import invo8.meezan.mb.common.SwipeButton


object Utils {

    val LOCATION_PERMISSION_REQUEST_CODE = 123

    fun setStatusBarColor(activity: Activity, newStatusBarColor: Int, isLightMode: Boolean) {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val currentStatusBarColor = window.statusBarColor

        // Check if we should apply light mode
        val decorView: View = window.decorView
        if (isLightMode) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = 0
        }

        // Create a ValueAnimator for the color transition
        val colorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            currentStatusBarColor,
            ContextCompat.getColor(activity, newStatusBarColor)
        )
        colorAnimator.duration = 300 // Adjust the duration as needed

        colorAnimator.addUpdateListener { animator ->
            val animatedColor = animator.animatedValue as Int
            window.statusBarColor = animatedColor
        }

        // Add a listener to reset the flag when the animation finishes
        colorAnimator.addListener(
            onEnd = {
                if (isLightMode) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility = 0
                }
            }
        )

        colorAnimator.start()
    }


    class NoUnderlineClickableSpan(
        private val onClickListener: () -> Unit,
        private val textColor: Int,
        private val underline: Boolean
    ) : ClickableSpan() {
        override fun onClick(widget: View) {
            onClickListener.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = textColor // Set the desired text color
            ds.isUnderlineText = underline // Remove the underline
        }
    }


    fun TextView.setClickableColoredTextNoUnderline(
        fullText: String, targetWord: String, color:
        Int, underline: Boolean, clickListener: () -> Unit
    ) {
        val spannable = SpannableString(fullText)
        val startIndex = fullText.indexOf(targetWord)

        if (startIndex != -1) {
            val endIndex = startIndex + targetWord.length

            // Apply the colored and clickable span without underline
            val clickableSpan = NoUnderlineClickableSpan(clickListener, color, underline)
            spannable.setSpan(
                clickableSpan,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT // Remove the highlight color when clicked
        } else {
            text = fullText
        }
    }

    fun maskPhoneNumber(phoneNumber: String): String {
        if (phoneNumber.length < 7) {
            // Not enough digits to mask, return the original number
            return phoneNumber
        }

        val firstFourDigits = phoneNumber.substring(0, 4)
        val lastTwoDigits = phoneNumber.substring(phoneNumber.length - 2)
        val maskedDigits = "▒▒▒▒▒"
//        val maskedDigits = "●●●●●"

        return "$firstFourDigits$maskedDigits$lastTwoDigits"
    }

    fun hasLocationPermission(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestLocationPermission(
        context: Context,
        activity: Activity,
        listener: SwipeButtonInterface
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {

            showPermissionRationaleDialog(context, listener)
        } else {

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    fun showPermissionRationaleDialog(context: Context, listener: SwipeButtonInterface) {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_permission)
        dialog.setCancelable(true)

        val swipeButton = dialog.findViewById<SwipeButton>(R.id.swipe_btn)
        swipeButton.setSwipeButtonListener(listener)



        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // dialog.getWindow().getAttributes().dimAmount = 0.0f;
//        dialog.window!!.attributes.windowAnimations = R.style.DialogAnim
        dialog.window!!.setGravity(Gravity.CENTER)
    }


}