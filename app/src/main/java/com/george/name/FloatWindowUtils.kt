package com.george.name

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.view.WindowManager.LayoutParams

/**
 * Created By George
 * Description:
 */
@Suppress("DEPRECATION")// TYPE_PHONE
class FloatWindowUtils(context: Context) {
    private var mContext: Context = context
    private var mWindowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutParams1: LayoutParams
    private var mFloatView: FloatView? = null

    init {
        val layoutParams = LayoutParams()
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.width = LayoutParams.WRAP_CONTENT
        layoutParams.height = LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.START or Gravity.TOP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams1 = layoutParams
    }

    fun addFloatView() {
        if (mFloatView == null) {
            mFloatView = FloatView(mContext)
            mWindowManager.addView(mFloatView, layoutParams1)
        }
    }

    fun removeFloatView() {
        if (mFloatView != null) {
            mWindowManager.removeView(mFloatView)
            mFloatView = null
        }
    }

    fun updateDisplay(packageNameStr: String, classNameStr: String) {
        mFloatView?.updateDisplay(packageNameStr, classNameStr)
    }
}