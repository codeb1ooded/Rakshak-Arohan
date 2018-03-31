package com.aarushi.crime_mappingapp.upload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.API.ApiClient;
import com.aarushi.crime_mappingapp.ComplaintActivity;
import com.aarushi.crime_mappingapp.Constants;
import com.aarushi.crime_mappingapp.DB.DatabaseHelper;
import com.aarushi.crime_mappingapp.DashboardActivity;
import com.aarushi.crime_mappingapp.Models.EmptyClass;
import com.aarushi.crime_mappingapp.Models.path.Path;
import com.aarushi.crime_mappingapp.Models.path.Route;
import com.aarushi.crime_mappingapp.safest_route.SafestRouteActivity;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by megha on 30/03/18.
 */

public class AutoUploadService extends Service {

    static final String TAG = "AutoUploadServiceTag";
    DatabaseHelper dbHelper;
    SQLiteDatabase mDatabase;

    public AutoUploadService() {
    }

    private static final int NOTIF_ID = 6;

    public static boolean AUTO_UPLOAD_SERVICE = false;

    @Override
    public int onStartCommand(final Intent intent, final int flags, int startId) {

        dbHelper = new DatabaseHelper(AutoUploadService.this);
        String title = "Market Acquire: Uploading..";
        String message = "Uploading Forms";
        startForeground(NOTIF_ID, getNotification(title, message).build());

        getUnuploadedComplaints();
        getUnuploadedImages();
        String imagePath = (new PreferenceManagerUtils(AutoUploadService.this)).getImagePath();
//        Log.i(TAG, imagePath);
        Log.i(TAG, "Upload start");
      //  if(imagePath != null)
        //    uploadFile((new PreferenceManagerUtils(AutoUploadService.this)).getImagePath());

        return START_NOT_STICKY;
    }

    private NotificationCompat.Builder getNotification(String title, String text) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentText(text)
               // .setSmallIcon(R.drawable.)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

    }

    /**
     * Method to create notification with the updated message
     * @param isUploaded true if the upload was successful and false if there was an error
     */
    private void updateNotification(boolean isUploaded) {
        String text;
        if (isUploaded) {
            text = "All the complaints have been uploaded.";
        } else {
            text = "Some of the complaints couldn't be uploaded due to an error.";
        }
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        Notification notification = getNotification("Market Acquire", text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, notification);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void uploadFile(final int image_id, String filepath) {

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(filepath);
        Uri fileUri = Uri.fromFile(file);

        // finally, execute the request
        JsonObject postParam = new JsonObject();
        Bitmap bitmap = null;
        try {
            bitmap = createBitmap(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
        }
        postParam.addProperty("image", getStringFromBitmap(bitmap)) ;

        Call<EmptyClass> call = ApiClient.getInterface().upload(postParam);
        call.enqueue(new Callback<EmptyClass>() {
            @Override
            public void onResponse(Call<EmptyClass> call, Response<EmptyClass> response) {
                dbHelper.updateImageToUploaded(image_id);
                Log.d(TAG, "response of image upload");
            }

            @Override
            public void onFailure(Call<EmptyClass> call, Throwable t) {
                Log.d(TAG, "error in image upload");
            }
        });
    }

    private Bitmap createBitmap(String pathname) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(pathname)));
        return Bitmap.createScaledBitmap(bitmap, 500, 500, true);
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    public void getUnuploadedComplaints(){
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.COMPLAINT_TABLE + " WHERE " + DatabaseHelper.COL_IS_UPLOADED + "=\'false\'";
        mDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Call<ResponseBody> reportComplaint = ApiClient.getInterface().reportComplaint(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CRIME_DATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CRIME_TIME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPLAINT_TIME)),
                    "2018-03-31",   //TODO: currently hardcoded
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_IS_VERIFIED)));
            final int complaint_id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));
            reportComplaint.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        dbHelper.updateComplaintToUploaded(complaint_id);
                    }
                    else{
                        Toast.makeText(AutoUploadService.this, "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(AutoUploadService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        cursor.close();
    }

    public void getUnuploadedImages(){
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_IMAGES + " WHERE " + DatabaseHelper.COL_IS_UPLOADED + "=\'false\'";
        mDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            uploadFile(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_PATH_NAME)));
        }
        cursor.close();
    }

}

