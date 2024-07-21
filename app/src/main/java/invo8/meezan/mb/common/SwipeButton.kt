package invo8.meezan.mb.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView
import invo8.meezan.mb.R
import invo8.meezan.mb.beforeAuth.fragments.qiblaFragment.SwipeButtonInterface


class SwipeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var slidingButton: ImageView
    private var initialX: Float = 0f
    private var active: Boolean = false
    private var initialButtonWidth: Int = 0

    private lateinit var centerText: ShimmerTextView

    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null

    private var swipeButtonInterface: SwipeButtonInterface? = null

    init {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    fun setSwipeButtonListener(listener: SwipeButtonInterface) {
        swipeButtonInterface = listener
    }



    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val background = RelativeLayout(context)

        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)

        background.background =
            ContextCompat.getDrawable(context, invo8.meezan.mb.R.drawable.shape_rounded)

        addView(background, layoutParamsView)


        val centerText = ShimmerTextView(context)

        this.centerText = centerText

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.addRule(CENTER_IN_PARENT, TRUE)
        centerText.text = "SWIPE" //add any text you need
        centerText.textSize = 20F
        centerText.setTextColor(Color.WHITE)
        centerText.reflectionColor = ContextCompat.getColor(context, R.color.purple)
        centerText.setPadding(35, 30, 35, 30)

        val shimmer = Shimmer()
        shimmer.start(centerText)
        shimmer
            .setDuration(1500)
            .setStartDelay(1000)
            .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)

        background.addView(centerText, layoutParams)

        //

        val swipeButton = ImageView(context)
        slidingButton = swipeButton

        disabledDrawable =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_right_double)
        enabledDrawable =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_done)

        slidingButton.setImageDrawable(disabledDrawable)
        slidingButton.setPadding(40, 40, 40, 40)

        val layoutParamsButton = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
        swipeButton.background =
            ContextCompat.getDrawable(context, R.drawable.shape_button)
        swipeButton.setImageDrawable(disabledDrawable)
        addView(swipeButton, layoutParamsButton)

        setOnTouchListener(getButtonTouchListener())


    }


    @SuppressLint("ClickableViewAccessibility")
    private fun getButtonTouchListener(): OnTouchListener? {
        return OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> return@OnTouchListener true
                MotionEvent.ACTION_MOVE -> {

                    //Movement logic here

                    if (initialX.toInt() == 0) {
                        initialX = slidingButton.x
                    }
                    if (event.x > initialX + slidingButton.width / 2 && event.x + slidingButton.width / 2 < width) {
                        slidingButton.x = event.x - slidingButton.width / 2
                        centerText.alpha =
                            1 - 1.3f * (slidingButton.x + slidingButton.width) / width
                    }

                    if (event.x + slidingButton.width / 2 > width && slidingButton.x + slidingButton.width / 2 < width) {
                        slidingButton.x = (width - slidingButton.width).toFloat()
                    }

                    if (event.getX() < slidingButton.width / 2 && slidingButton.x > 0) {
                        slidingButton.x = 0F
                    }

                    return@OnTouchListener true
                }


                MotionEvent.ACTION_UP -> {
                    //Release logic here

                    if (active) {
//                        collapseButton()
                        swipeButtonInterface!!.onReleaseButton(true)
                    } else {
                        initialButtonWidth = slidingButton.width;

                        if (slidingButton.x + slidingButton.width > width * 0.85) {
                            expandButton()
                            swipeButtonInterface!!.onSwipeEndButton(true)

                        } else {
                            moveButtonBack();

                        }
                    }

                    return@OnTouchListener true

                }
            }
            false
        }
    }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton.x, 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton.x = x
        }
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton.width, width
        )
        widthAnimator.addUpdateListener {
            val params = slidingButton.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            slidingButton.layoutParams = params
        }
        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = true
                slidingButton.setImageDrawable(enabledDrawable)
            }
        })
        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun collapseButton() {
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton.width, initialButtonWidth
        )
        widthAnimator.addUpdateListener {
            val params = slidingButton.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            slidingButton.layoutParams = params
        }
        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = false
                slidingButton.setImageDrawable(disabledDrawable)
            }
        })
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton.x, 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton.x = x
        }
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        positionAnimator.duration = 200
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }
}






