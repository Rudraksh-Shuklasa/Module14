package com.example.module_14

import android.content.Context
import android.widget.Toast
import android.content.pm.PackageManager


object CameraUtils {

    /**
     * method to check if Device support camera or not
     * @param context of calling class
     * @return if camera is supportable or not
     */
    fun isDeviceSupportCamera(context: Context): Boolean {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true
        }
        Toast.makeText(context, "Device doesn't support camera.", Toast.LENGTH_SHORT).show()
        return false
    }
}