package com.george.name

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

/**
 * Created By George
 * Description:
 */
object AccessibilitUtil {
    fun checkAccessibilityEnable(context: Context) :Boolean{
        if (!isAccessibilitySettingsOn(context)){
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            context.startActivity(intent)
            Toast.makeText(context,"请先开启 此应用 的辅助功能",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    /**
     * 系统属性：判断辅助功能是否开启
     */
    private fun isAccessibilitySettingsOn(context: Context): Boolean {
        val accessibilityEnable = Settings.Secure.getInt(context.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        if (accessibilityEnable == 1){
            val services = Settings.Secure.getString(context.contentResolver,Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (services!=null){
                return services.toLowerCase().contains(context.packageName.toLowerCase())
            }
        }
        return false
    }
}