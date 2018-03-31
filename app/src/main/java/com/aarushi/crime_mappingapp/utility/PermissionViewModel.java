package com.aarushi.crime_mappingapp.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * @author Ajay Bhatt (ajaybhatt17@gmail.com)
 */
public class PermissionViewModel {


    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @IntDef({PERMISSION_CAMERA, PERMISSION_VIDEO, PERMISSION_LOCATION,
            PERMISSION_READ_CONTACTS, PERMISSION_READ_STORAGE, PERMISSION_WRITE_STORAGE,
            PERMISSION_READ_SMS, PERMISSION_NONE})
    // Create an interface for validating String types
    public @interface PermissionType {
    }

    public static final int PERMISSION_LOCATION = 1;
    public static final int PERMISSION_CAMERA = 2;
    public static final int PERMISSION_VIDEO = 7;
    public static final int PERMISSION_READ_CONTACTS = 3;
    public static final int PERMISSION_READ_STORAGE = 4;
    public static final int PERMISSION_WRITE_STORAGE = 5;
    public static final int PERMISSION_READ_SMS = 6;
    public static final int PERMISSION_NONE = -1;

    private static final String TAG = "PermissionViewModel";


    public static final int LOCATION_REQUEST_CODE = 1000;
    public static final int CAMERA_REQUEST_CODE = 1001;
    public static final int READ_EXTERNAL_STORAGE_CODE = 1002;
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 1003;
    public static final int READ_CONTACTS_CODE = 1004;
    public static final int READ_SMS_CODE = 1005;

    @PermissionType
    private int[] permissionType;

    private int[] grantResults;

    private String tag;

    private PermissionListener permissionListener;

    public PermissionViewModel(@PermissionType int... permissionType) {
        this.permissionType = new int[permissionType.length];
        for (int i = 0; i < permissionType.length; i++) {
            this.permissionType[i] = permissionType[i];
        }
    }

    public int[] getPermissionType() {
        return permissionType;
    }

    @PermissionType
    public static int getPermissionType(String manifestPermission) {
        switch (manifestPermission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return PERMISSION_LOCATION;
            case Manifest.permission.CAMERA:
                return PERMISSION_CAMERA;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return PERMISSION_READ_STORAGE;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return PERMISSION_WRITE_STORAGE;
            case Manifest.permission.READ_CONTACTS:
                return PERMISSION_READ_CONTACTS;
            case Manifest.permission.READ_SMS:
                return PERMISSION_READ_SMS;
        }
        return PERMISSION_NONE;
    }

    public String getManifestPermission(@PermissionType int type) {
        switch (type) {
            case PERMISSION_LOCATION:
                return Manifest.permission.ACCESS_FINE_LOCATION;
            case PERMISSION_CAMERA:
            case PERMISSION_VIDEO:
                return Manifest.permission.CAMERA;
            case PERMISSION_READ_STORAGE:
                return Manifest.permission.READ_EXTERNAL_STORAGE;
            case PERMISSION_WRITE_STORAGE:
                return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case PERMISSION_READ_CONTACTS:
                return Manifest.permission.READ_CONTACTS;
            case PERMISSION_READ_SMS:
                return Manifest.permission.READ_SMS;
        }
        return null;
    }

    public String[] getManifestPermissions(@PermissionType int... type) {
        String[] str = new String[type.length];
        for (int i = 0; i < type.length; i++) {
            str[i] = getManifestPermission(type[i]);
        }
        return str;
    }

    public int getRequestCode(@PermissionType int type) {
        switch (type) {
            case PERMISSION_LOCATION:
                return LOCATION_REQUEST_CODE;
            case PERMISSION_CAMERA:
                return CAMERA_REQUEST_CODE;
            case PERMISSION_READ_STORAGE:
                return READ_EXTERNAL_STORAGE_CODE;
            case PERMISSION_WRITE_STORAGE:
                return WRITE_EXTERNAL_STORAGE_CODE;
            case PERMISSION_READ_CONTACTS:
                return READ_CONTACTS_CODE;
            case PERMISSION_READ_SMS:
                return READ_SMS_CODE;
        }
        return 0;
    }

    public int getRequestCode(@PermissionType int... type) {
        int requestCode = 0;
        for (int chk : type) {
            requestCode += getRequestCode(chk);
        }
        return requestCode;
    }

    public static PermissionViewModel getFromManifestPermission(String[] permissions) {
        @PermissionType int[] permissionTypes = new int[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            permissionTypes[i] = PermissionViewModel.getPermissionType(permissions[i]);
        }
        if (permissionTypes.length == 0) return null;
        return new PermissionViewModel(permissionTypes);
    }

    public static boolean isPermissionRight(String[] permissions, @PermissionType int permissionType) {
        PermissionViewModel permissionViewModel = PermissionViewModel.getFromManifestPermission(permissions);
        if (permissionViewModel != null) {
            return Utils.contains(permissionViewModel.getPermissionType(), permissionType);
        }
        return false;
    }

    public boolean isPermissionApproved(Context context) {
        boolean output = true;
        for (int type : permissionType) {
            output = (output && isPermissionApproved(context, type));
        }
        return output;
    }

    public boolean isPermissionApproved(Context context, @PermissionType int type) {
        String manifestPermission = getManifestPermission(type);
        if (manifestPermission == null) return false;
        int permissionState = ActivityCompat.checkSelfPermission(context, manifestPermission);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        for (int type : permissionType) {
            requestPermission(activity, type);
        }
    }

    public void requestPermission(Fragment fragment) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        for (int type : permissionType) {
            requestPermission(fragment, type);
        }
    }

    public void requestPermission(Activity activity, int type) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        if (isPermissionApproved(activity.getApplicationContext(), type)) return;
        String manifestPermission = getManifestPermission(type);
        if (manifestPermission == null) return;
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(activity, manifestPermission);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            if (permissionListener != null) {
                permissionListener.showEnableDialog(type);
            }
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            requestPermissionDirectly(activity);
        }
    }

    public void requestPermission(Fragment fragment, int type) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        if (isPermissionApproved(fragment.getContext(), type)) return;
        String manifestPermission = getManifestPermission(type);
        if (manifestPermission == null) return;
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), manifestPermission);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            if (permissionListener != null) {
                permissionListener.showEnableDialog(type);
            }
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            requestPermissionDirectly(fragment);
        }
    }

    public void requestPermissionDirectly(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        requestPermissionDirectly(activity, permissionType);
    }

    public void requestPermissionDirectly(Fragment fragment) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        requestPermissionDirectly(fragment, permissionType);
    }

    public void requestPermissionDirectly(Activity activity, @PermissionType int... type) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        int requestCode = getRequestCode(type);
        activity.requestPermissions(getManifestPermissions(type), requestCode);
        if (permissionListener != null) {
            permissionListener.permissionRequested(requestCode);
        }
    }

    public void requestPermissionDirectly(Fragment fragment, @PermissionType int... type) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        int requestCode = getRequestCode(type);
        fragment.requestPermissions(getManifestPermissions(type), requestCode);
        if (permissionListener != null) {
            permissionListener.permissionRequested(requestCode);
        }
    }

    public static void onPermissionResult(Activity activity, int requestCode,
                                          PermissionListener permissionListener,
                                          @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        PermissionViewModel permissionViewModel = PermissionViewModel.getFromManifestPermission(permissions);
        if (permissionViewModel == null) return;
        permissionViewModel.setGrantResults(grantResults);
        if (permissionViewModel.allPermissionGranted()) {
            if (permissionListener != null) {
                permissionListener.onPermissionAccepted(permissionViewModel.grantedPermissionTypes());
            }
        } else {
            int[] notGrantedPermissions = permissionViewModel.notGrantedPermissionTypes();
            if (!permissionViewModel.showRequestPermissionRationale(activity)) {
                permissionListener.showEnableDialog(notGrantedPermissions);
            } else {
                permissionListener.onPermissionDenied(notGrantedPermissions);
            }
        }
    }

    public PermissionListener getPermissionListener() {
        return permissionListener;
    }

    public void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int[] getGrantResults() {
        return grantResults;
    }

    public void setGrantResults(int[] grantResults) {
        this.grantResults = grantResults;
    }

    public boolean allPermissionGranted() {
        boolean output = true;
        for (int grantResult : grantResults) {
            if (grantResults.length > 0 && grantResult != PackageManager.PERMISSION_GRANTED) {
                output = false;
                break;
            }
        }
        return output;
    }

    public int[] grantedPermissionTypes() {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < permissionType.length; i++) {
            int grantResult = grantResults[i];
            if (grantResults.length > 0 && grantResult == PackageManager.PERMISSION_GRANTED) {
                data.add(permissionType[i]);
            }
        }
        return Utils.arrayListToArray(data);
    }

    public int[] notGrantedPermissionTypes() {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < permissionType.length; i++) {
            int grantResult = grantResults[i];
            if (grantResults.length > 0 && grantResult != PackageManager.PERMISSION_GRANTED) {
                data.add(permissionType[i]);
            }
        }
        return Utils.arrayListToArray(data);
    }

    public boolean showRequestPermissionRationale(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        boolean output = false;
        for (int permission : permissionType) {
            output = output || activity.shouldShowRequestPermissionRationale(getManifestPermission(permission));
        }
        return output;
    }

    public interface PermissionListener {

        void showEnableDialog(int... permissionType);

        void permissionRequested(int requestCode);

        void onPermissionAccepted(int... permissionType);

        void onPermissionDenied(int... permissionType);

    }

}