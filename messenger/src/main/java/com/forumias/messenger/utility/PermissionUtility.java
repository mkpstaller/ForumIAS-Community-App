package com.forumias.messenger.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtility {
    public int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public   boolean checkCameraPermissions(Context context , Activity activity) {
        if (activity != null) {
            int permissionSendMessage = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

        }
        return true;
    }

    public   boolean checkGalleryPermissions(Context context , Activity activity) {
        if (activity != null) {

            int locationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (locationPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

        }
        return true;
    }


    public  boolean checkReadSMSPermissions(Context context , Activity activity) {
        if (activity != null) {

            int readPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
            int receivePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_SMS);
            }
            if (receivePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

        }
        return true;
    }
}
