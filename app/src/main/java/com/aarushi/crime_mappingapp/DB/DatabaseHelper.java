package com.aarushi.crime_mappingapp.DB;

/**
 * Created by arushiarora on 3/31/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by arushiarora on 3/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Crime.db";
    public static final String COMPLAINT_TABLE = "complaint_table";
    public static final String COL_ID = "ID";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_LOCATION = "LOCATION";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_CRIME_DATE = "CRIMEDATE";
    public static final String COL_CRIME_TIME = "CRIMETIME";
    public static final String COL_COMPLAINT_TIME = "COMPLAINTTIME";
    public static final String COL_COMPLAINT_DATE = "COMPLAINTDATE";
    public static final String COL_IS_VERIFIED = "ISVERIFIED";
    public static final String COL_IS_UPLOADED = "ISUPLOADED";

    public static final String COL_LATITUDE = "LATITUDE";
    public static final String COL_LONGITUDE = "LONGITUDE";

    public static final String TABLE_IMAGES = "IMAGES";
    public static final String COL_COMPLAINT_ID = "COMPLAINT_ID";
    public static final String COL_IMAGE_PATH_NAME = "IMAGE_PATH_NAME";
    public static final String COL_TIMESTAMP_TIME = "TIMESTAMP_TIME";
    public static final String COL_TIMESTAMP_DATE = "TIMESTAMP_DATE";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + COMPLAINT_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERNAME TEXT," +
                "TYPE TEXT," +
                "LATITUDE TEXT," +
                "LONGITUDE TEXT," +
                "LOCATION TEXT," +
                "DESCRIPTION TEXT," +
                "CRIMEDATE TEXT," +
                "CRIMETIME TEXT," +
                "COMPLAINTTIME TEXT," +
                "COMPLAINTDATE TEXT," +
                "ISVERIFIED TEXT," +
                "ISUPLOADED TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_IMAGES +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_COMPLAINT_ID + " INTEGER," +
                COL_IMAGE_PATH_NAME + " VARCHAR(1000)," +
                COL_TIMESTAMP_DATE + " VARCHAR(15)," +              // format yyyy-MM-dd
                COL_TIMESTAMP_TIME + " VARCHAR(15)," +              // format HH:mm:ss
                COL_LATITUDE + " VARCHAR(50)," +
                COL_LONGITUDE + " VARCHAR(50)," +
                COL_IS_UPLOADED + " VARCHAR(5))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ COMPLAINT_TABLE);
        onCreate(db);
    }

    public long insertData(String username,
                              String type,
                              String latitude,
                              String longitude,
                              String location,
                              String description,
                              String crimedate,
                              String crimetime,
                              String complainttime,
                              String complaintdate,
                              String isverified) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_LOCATION, location);
        contentValues.put(COL_DESCRIPTION, description);
        contentValues.put(COL_CRIME_DATE, crimedate);
        contentValues.put(COL_CRIME_TIME, crimetime);
        contentValues.put(COL_COMPLAINT_TIME, complainttime);
        contentValues.put(COL_COMPLAINT_DATE, complaintdate);
        contentValues.put(COL_IS_VERIFIED, isverified);
        contentValues.put(COL_IS_UPLOADED, "false");

        return db.insert(COMPLAINT_TABLE,null ,contentValues);
    }

    public boolean insertImageEntry(int complaint_id,
                                    String imagePath,
                                    String date,
                                    String time,
                                    String latitude,
                                    String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_COMPLAINT_ID, complaint_id);
        contentValues.put(COL_IMAGE_PATH_NAME, imagePath);
        contentValues.put(COL_TIMESTAMP_DATE, date);
        contentValues.put(COL_TIMESTAMP_TIME, time);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_IS_UPLOADED, "false");

        long result = db.insert(TABLE_IMAGES,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ COMPLAINT_TABLE,null);
        return res;
    }

    public boolean updateData(String id,String username,String type,String latitude,String longitude,String location,String description,String crimedate,String crimetime,String complainttime,String complaintdate,String isverified,String isuploaded) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_TYPE,type);
        contentValues.put(COL_LATITUDE,latitude);
        contentValues.put(COL_LONGITUDE,longitude);
        contentValues.put(COL_LOCATION,location);
        contentValues.put(COL_DESCRIPTION,description);
        contentValues.put(COL_CRIME_DATE,crimedate);
        contentValues.put(COL_CRIME_TIME,crimetime);
        contentValues.put(COL_COMPLAINT_TIME,complainttime);
        contentValues.put(COL_COMPLAINT_DATE,complaintdate);
        contentValues.put(COL_IS_VERIFIED,isverified);
        contentValues.put(COL_IS_UPLOADED,isuploaded);
        db.update(COMPLAINT_TABLE, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public boolean updateComplaintToUploaded(int complaint_local_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_UPLOADED, "true");
        db.update(COMPLAINT_TABLE, contentValues, "ID = ?",new String[] { complaint_local_id+"" });
        return true;
    }

    public boolean updateImageToUploaded(int image_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_UPLOADED, "true");
        db.update(TABLE_IMAGES, contentValues, "ID = ?",new String[] { image_id+"" });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COMPLAINT_TABLE, "ID = ?",new String[] {id});
    }
}




