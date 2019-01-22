package com.george.name

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val PERMISSION_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAlertWindowPermission()
        openFloat.setOnClickListener {
            Log.d(TAG, "打开")
            if (checkAlertWindowPermission())
                openAlertWindow()

        }
        closeFloat.setOnClickListener {
            Log.d(TAG, "关闭")
            val intent = Intent(this,TrackerService::class.java)
            intent.putExtra(TrackerService.TYPE_KEY,TrackerService.Type.CLOSE.ordinal)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_CODE){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if (!Settings.canDrawOverlays(this)){
                    Toast.makeText(this,"权限授予失败，无法开启悬浮窗",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"权限授予成功",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 悬浮窗权限
     */
    private fun checkAlertWindowPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {// 是否在其它app上draw
                Toast.makeText(this, "当前无悬浮窗权限,请授权", Toast.LENGTH_SHORT).show()
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, PERMISSION_CODE)
                return false
            }
        }
        return true
    }

    private fun openAlertWindow() {
        if (AccessibilitUtil.checkAccessibilityEnable(this)) {
            val intent = Intent(this, TrackerService::class.java)
            intent.putExtra(TrackerService.TYPE_KEY, TrackerService.Type.OPEN.ordinal)
            startService(intent)
            finish()
        }
    }
}
