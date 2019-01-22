package com.george.name

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

/**
 * Created By George
 * Description:
 * 比如魅族pro5界面：
 * 辅助功能-》无障碍模式
 */
class TrackerService : AccessibilityService() {
    private var mFloatWindowUtils: FloatWindowUtils? = null
    private var type: Int = 0

    companion object {
        const val TYPE_KEY = "type_key"
    }

    enum class Type {
        OPEN, CLOSE;
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (mFloatWindowUtils == null) {
            mFloatWindowUtils = FloatWindowUtils(applicationContext)
        }
        type = intent.getIntExtra(TYPE_KEY, -1)
        if (type != -1) {
            if (type == Type.OPEN.ordinal) {
                mFloatWindowUtils!!.addFloatView()
            } else if (type == Type.CLOSE.ordinal) {
                mFloatWindowUtils!!.removeFloatView()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onInterrupt() {
    }

    /**
     * 监听事件：更新悬浮窗packageName和className
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageNameStr = event.packageName
            var classNameStr = event.className
            if (classNameStr.startsWith(packageNameStr)) {
                classNameStr = classNameStr.substring(packageNameStr.length)
            }
            mFloatWindowUtils?.updateDisplay(packageNameStr as String, classNameStr as String)
        }
    }
}