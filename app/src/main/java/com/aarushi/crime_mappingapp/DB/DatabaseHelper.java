package com.aarushi.crime_mappingapp.DB;

/**
 * Created by arushiarora on 3/31/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arushiarora on 3/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Crime.db";
    public static final String TABLE_NAME = "complaint_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "TYPE";
    public static final String COL_4 = "LATITUDE";
    public static final String COL_5 = "LONGITUDE";
    public static final String COL_6 = "LOCATION";
    public static final String COL_7 = "DESCRIPTION";
    public static final String COL_8 = "CRIMEDATE";
    public static final String COL_9 = "CRIMETIME";
    public static final String COL_10 = "COMPLAINTTIME";
    public static final String COL_11 = "COMPLAINTDATE";
    public static final String COL_12 = "ISVERIFIED";
    public static final String COL_13 = "ISUPLOADED";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,TYPE TEXT,LATITUDE TEXT,LONGITUDE TEXT,LOCATION TEXT,DESCRIPTION TEXT,CRIMEDATE TEXT,CRIMETIME TEXT,COMPLAINTTIME TEXT,COMPLAINTDATE TEXT,ISVERIFIED TEXT,ISUPLOADED TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username,String type,String latitude,String longitude,String location,String description,String crimedate,String crimetime,String complainttime,String complaintdate,String isverified,String isuploaded) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,type);
        contentValues.put(COL_4,latitude);
        contentValues.put(COL_5,longitude);
        contentValues.put(COL_6,location);
        contentValues.put(COL_7,description);
        contentValues.put(COL_8,crimedate);
        contentValues.put(COL_9,crimetime);
        contentValues.put(COL_10,complainttime);
        contentValues.put(COL_11,complaintdate);
        contentValues.put(COL_12,isverified);
        contentValues.put(COL_13,isuploaded);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String username,String type,String latitude,String longitude,String location,String description,String crimedate,String crimetime,String complainttime,String complaintdate,String isverified,String isuploaded) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,type);
        contentValues.put(COL_4,latitude);
        contentValues.put(COL_5,longitude);
        contentValues.put(COL_6,location);
        contentValues.put(COL_7,description);
        contentValues.put(COL_8,crimedate);
        contentValues.put(COL_9,crimetime);
        contentValues.put(COL_10,complainttime);
        contentValues.put(COL_11,complaintdate);
        contentValues.put(COL_12,isverified);
        contentValues.put(COL_13,isuploaded);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}




