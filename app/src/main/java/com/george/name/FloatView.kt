package com.george.name

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

/**
 * Created By George
 * Description:
 * 自定义悬浮窗
 */
class FloatView(context: Context) : LinearLayout(context) {
    private var mContext: Context = context
    private var mWindowManager: WindowManager
    private lateinit var packageName: TextView
    private lateinit var className: TextView
    private lateinit var downPoint: Point
    private lateinit var movePoint: Point

    init {
        mWindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        initView()
    }

    private fun initView() {
        View.inflate(mContext, R.layout.float_window, this)
        packageName = findViewById(R.id.package_name)
        className = findViewById(R.id.class_name)
        findViewById<View>(R.id.close).setOnClickListener {
            Toast.makeText(mContext, "关闭悬浮窗", Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, TrackerService::class.java)
            intent.putExtra(TrackerService.TYPE_KEY, TrackerService.Type.CLOSE.ordinal)
            mContext.startActivity(intent)
        }
    }

    fun updateDisplay(packageNameStr: String, classNameStr: String) {
        packageName.text = packageNameStr
        className.text = classNameStr
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> downPoint = Point(event.rawX.toInt(), event.rawY.toInt())
            MotionEvent.ACTION_MOVE -> {
                movePoint = Point(event.rawX.toInt(),event.rawY.toInt())
                val dx = movePoint.x - downPoint.x
                val dy = movePoint.y - downPoint.y
                val layoutParams = layoutParams as WindowManager.LayoutParams
                layoutParams.x += dx
                layoutParams.y += dy
                mWindowManager.updateViewLayout(this,layoutParams)
                downPoint = movePoint
            }
        }

        return false
    }
}