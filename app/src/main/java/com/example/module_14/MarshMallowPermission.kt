package com.example.module_14

import android.content.Context
import java.nio.file.Files.size
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat



class MarshMallowPermission{
    fun checkPermission(context: Context, permission: String): Int {
        return ContextCompat.checkSelfPermission(context, permission)
    }

    /**
     * method to check if all multiple/single permission is granted or not
     * @param appCompatActivity calling activity
     * @param permissionsToGrant permission list in string array
     * @param PERMISSION_REQUEST_CODE to identify every permission check
     * @return if granted or not
     */
    fun checkMashMallowPermissions(
        appCompatActivity: AppCompatActivity,
        permissionsToGrant: Array<String>,
        PERMISSION_REQUEST_CODE: Int
    ): Boolean {
        val permissions = ArrayList<String>()
        for (permission in permissionsToGrant) {
            if (checkPermission(appCompatActivity, permission) != 0) {
                permissions.add(permission)
            }
        }
        //if all permission is granted
        if (permissions.size === 0) {
            return true
        }
        ActivityCompat.requestPermissions(
            appCompatActivity,
            permissions.toArray(arrayOfNulls<String>(permissions.size)),
            PERMISSION_REQUEST_CODE
        )
        return false
    }
}