package invo8.meezan.mb.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    // Override the onTouchEvent and onInterceptTouchEvent methods
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Disable touch events
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // Disable touch events
        return false
    }
}
