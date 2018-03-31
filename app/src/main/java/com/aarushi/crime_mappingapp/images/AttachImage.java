package com.aarushi.crime_mappingapp.images;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.GPSTracker;
import com.aarushi.crime_mappingapp.Models.ImageModel;
import com.aarushi.crime_mappingapp.R;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by megha on 30/03/18.
 */

public class AttachImage extends AppCompatActivity {

    public static final int INTENT_ATTACH_IMAGE = 100;

    int TAKE_PHOTO_CODE = 0;
    String file_name;

    Button attachImage, addMoreImage;
    GridView gridView;
    ImageAdapterGridView mAdapter;

    ArrayList<ImageModel> images;
    SimpleDateFormat timeFormat, dateFormat;
    private GPSTracker mGPSTracker;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_screen);

        // Here, we are making a folder named picFolder to store
        // pics taken by the camera using this application.
        images = new ArrayList<>();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mGPSTracker = new GPSTracker(this);

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/RakshakArohan/";
        File newdir = new File(dir);
        newdir.mkdirs();

        mAdapter = new ImageAdapterGridView(this, images);
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(mAdapter);
        images = new ArrayList<>();

        addMoreImage = (Button) findViewById(R.id.add_more);
        addMoreImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                file_name = dir + "IMG_"+ timeStamp +".jpg";
                File newfile = new File(file_name);
                try {
                    newfile.createNewFile();
                }
                catch (IOException e) {}

                Log.e("AbsolutePath", newfile.getAbsolutePath());

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });

        attachImage = (Button) findViewById(R.id.attach);
        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("images", images);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Intent i = new Intent();
            i.putExtra("PICTURE_PATH", file_name);
            Log.i("picture", file_name);

            final Date currentTime = Calendar.getInstance().getTime();
            final Time today = new Time(Time.getCurrentTimezone());

            double lat = 0, lng = 0;

            if(mGPSTracker.canGetLocation()) {
                lat = mGPSTracker.getLatitude();
                lng = mGPSTracker.getLongitude();
            }

            ImageModel image = new ImageModel(file_name,
                    timeFormat.format(currentTime),
                    dateFormat.format(currentTime),
                    lat + "",
                    lng + "");
            images.add(image);
            mAdapter.changeData(image);
            mAdapter.notifyDataSetChanged();

           // setResult(RESULT_OK , i);
           // finish();
        }
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        private ArrayList<ImageModel> mImages;

        public ImageAdapterGridView(Context c, ArrayList<ImageModel> images) {
            mContext = c;
            mImages = images;
        }

        public int getCount() {
            return mImages.size();
        }

        public void changeData(ImageModel image){
            mImages.add(image);
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(250, 250));
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            Bitmap bmp = null;
            try {
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(new FileInputStream(new File(mImages.get(position).getPathName()))), 500, 500, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mImageView.setImageBitmap(bmp);
            return mImageView;
        }
    }
}
